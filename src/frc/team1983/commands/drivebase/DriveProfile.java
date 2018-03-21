package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.services.parser.SmellyDeserializer;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.DrivebaseAuxiliaryPidOutput;
import frc.team1983.util.control.ClosedLoopGains;
import frc.team1983.util.motion.profiles.CruiseProfile;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SmellyDeserializer.class)
public class DriveProfile extends CommandBase
{
    private Logger logger;

    private Drivebase drivebase;
    protected CruiseProfile leftProfile, rightProfile;

    private PIDController headingLoop;
    private boolean runHeadingCorrection = false;
    private double startHeading;
    private double endHeading;
    private double deltaHeading;
    private ArrayList<CommandBase> actions;

    private GyroPidInput pidInput;
    private DrivebaseAuxiliaryPidOutput pidOutput;

    private boolean stitched = false;

    private double onTargetTime = 0;
    private double lastOnTargetTimestamp = 0;

    private double leftDistance, rightDistance;
    private double leftInitDist, rightInitDist;
    private double totalDistance;

    private double duration;

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile, double duration,
                        double deltaHeading, ActionsEnum[] actions)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        requires(drivebase);
        setTimeout(duration + 0.75);

        this.actions = new ArrayList<>();

        for(ActionsEnum action : actions)
        {
            this.actions.add(action.getAction().createAction(Robot.getInstance().getCollector(), Robot.getInstance().getElevator()));
        }

        this.drivebase = drivebase;
        this.leftProfile = leftProfile;
        this.rightProfile = rightProfile;

        leftDistance = Drivebase.getFeet(leftProfile.getDistance());
        rightDistance = Drivebase.getFeet(rightProfile.getDistance());

        this.duration = duration;
        this.deltaHeading = deltaHeading;

        pidInput = new GyroPidInput(drivebase.getGyro());
        pidOutput = new DrivebaseAuxiliaryPidOutput(drivebase);

        if(runHeadingCorrection)
        {
            headingLoop = new PIDController(
                    0, 0, 0, 0,
                    pidInput, pidOutput
            );
        }
    }

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile, double duration, ActionsEnum[] actions)
    {
        this(drivebase, leftProfile, rightProfile, duration, 0, actions);
        this.runHeadingCorrection = false;
    }

    @Override
    public void initialize()
    {
        if(runHeadingCorrection)
        {
            startHeading = drivebase.getGyro().getAngle();
            endHeading = startHeading + deltaHeading;

            leftInitDist = drivebase.getLeftDistance();
            rightInitDist = drivebase.getRightDistance();

            totalDistance = (leftDistance + rightDistance) / 2;

            headingLoop.enable();
            headingLoop.setOutputRange(-0.6, 0.6);
        }

        drivebase.setLeftProfile(leftProfile);
        drivebase.setRightProfile(rightProfile);

        drivebase.runProfiles();
    }

    @Override
    public void execute()
    {
        if(runHeadingCorrection)
        {
            double dL = drivebase.getLeftDistance();
            double dR = drivebase.getRightDistance();

            double percent = ((dL + dR) / 2) / totalDistance;
            percent = Math.min(1, Math.max(percent, 0));
            double desiredHeading = startHeading + (percent * deltaHeading);

            headingLoop.setSetpoint(desiredHeading);
        }
    }

    @Override
    public boolean isFinished()
    {
        boolean finished = drivebase.profilesAreFinished();

        if(!stitched)
        {
            //logger.info(onTargetTime);

            if(onTarget() && lastOnTargetTimestamp != 0)
            {
                onTargetTime += (System.currentTimeMillis() - lastOnTargetTimestamp) * 0.001;
                lastOnTargetTimestamp = System.currentTimeMillis();
            }
            else
            {
                onTargetTime = 0;
            }

            lastOnTargetTimestamp = System.currentTimeMillis();

            finished &= (onTargetTime >= Constants.Motion.DRIVEBASE_IN_RANGE_END_TIME);
        }
        else
        {
            finished &= onTarget();
        }

        finished |= isTimedOut();
        if(isTimedOut())
        {
            logger.info("timed out");
        }
        return finished;
    }

    private boolean onTarget()
    {
        boolean isOnTarget = (drivebase.getLeftError() <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE) &&
                             (drivebase.getRightError() <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE);

        if(runHeadingCorrection)
        {
            isOnTarget &= (endHeading - drivebase.getGyro().getAngle()) <= Constants.Motion.DRIVEBASE_HEADING_END_RANGE;
        }

        return isOnTarget;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {
        logger.info("left error: " + Drivebase.getFeet(drivebase.getLeftError()) +
                                   " , right error: " + Drivebase.getFeet(drivebase.getRightError()) +
                                   " , gyro error: " + (endHeading - drivebase.getGyro().getAngle()));

        drivebase.stopProfiles();

        if(runHeadingCorrection)
        {
            headingLoop.disable();
        }
    }

    // ohhhhh my god please
    public static void stitch(DriveProfile drive1, DriveProfile drive2)
    {
        drive1.stitched = true;

        CruiseProfile.stitch(drive1.leftProfile, drive2.leftProfile);
        CruiseProfile.stitch(drive1.rightProfile, drive2.rightProfile);
    }

    // this is terrible please put me out of my misery
    public static void stitch(List<DriveProfile> drives)
    {
        List<CruiseProfile> leftProfiles = new ArrayList<>();
        List<CruiseProfile> rightProfiles = new ArrayList<>();

        for(int i = 0; i < drives.size(); i++)
        {
            if(i < drives.size() - 1)
            {
                drives.get(i).stitched = true;
            }

            leftProfiles.add(drives.get(i).leftProfile);
            rightProfiles.add(drives.get(i).rightProfile);
        }

        CruiseProfile.stitch(leftProfiles);
        CruiseProfile.stitch(rightProfiles);
    }

    public ArrayList<CommandBase> getActions()
    {
        return actions;
    }

    protected void setHeadingLoopGains(ClosedLoopGains gains)
    {
        if(runHeadingCorrection)
        {
            headingLoop.setP(gains.get_kP());
            headingLoop.setI(gains.get_kI());
            headingLoop.setD(gains.get_kD());
            headingLoop.setF(gains.get_kF());
        }
    }
}
