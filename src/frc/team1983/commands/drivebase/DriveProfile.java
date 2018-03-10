package frc.team1983.commands.drivebase;

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
import frc.team1983.util.motion.profiles.CruiseProfile;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = SmellyDeserializer.class)
public class DriveProfile extends CommandBase
{
    private Drivebase drivebase;
    protected CruiseProfile leftProfile, rightProfile;

    private PIDController headingLoop;
    private boolean runHeadingCorrection = true;
    private double startHeading;
    private double endHeading;
    private double deltaHeading;
    private ArrayList<Command> actions;

    private double onTargetTime = 0;
    private double lastOnTargetTimestamp = 0;

    private double leftDistance, rightDistance;
    private double leftInitDist, rightInitDist;

    private double duration;

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile, double duration,
                        double deltaHeading, ActionsEnum[] actions)
    {
        Logger logger = LoggerFactory.createNewLogger(this.getClass());

        requires(drivebase);

        this.actions = new ArrayList<>();

        for(ActionsEnum action : actions)
        {
            this.actions.add(action.getAction().createAction(Robot.getInstance().getCollector(), Robot.getInstance().getElevator()));
        }

        this.drivebase = drivebase;
        this.leftProfile = leftProfile;
        this.rightProfile = rightProfile;

        this.leftDistance = leftProfile.getDistance();
        this.rightDistance = rightProfile.getDistance();

        this.duration = duration;
        this.deltaHeading = deltaHeading;

        if(runHeadingCorrection)
        {
            headingLoop = new PIDController(
                    Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kP(),
                    Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kI(),
                    Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kD(),
                    Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kF(),
                    new GyroPidInput(drivebase.getGyro()),
                    new DrivebaseAuxiliaryPidOutput(drivebase)
            );

            headingLoop.setOutputRange(-1, 1);
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
            endHeading = drivebase.getGyro().getAngle() + deltaHeading;

            leftInitDist = drivebase.getLeftDistance();
            rightInitDist = drivebase.getRightDistance();

            headingLoop.enable();
        }

        drivebase.setLeftProfile(leftProfile);
        drivebase.setRightProfile(rightProfile);

        drivebase.runProfiles();
    }

    @Override
    public void execute()
    {
        System.out.println("left error: " + Drivebase.getFeet(drivebase.getLeftError()) +
                                   " , right error: " + Drivebase.getFeet(drivebase.getRightError()) +
                                   " , gyro error: " + (endHeading - drivebase.getGyro().getAngle()));

        if(runHeadingCorrection)
        {
            double avgDist = ((drivebase.getLeftDistance() - leftInitDist) + (drivebase.getRightDistance() - rightInitDist)) / 2;
            double currentDistPercent = avgDist / ((leftDistance + rightDistance) / 2);
            currentDistPercent = Math.min(currentDistPercent, 1.0);
            double desiredHeading = startHeading + ((endHeading - startHeading) * currentDistPercent);

            //double desiredHeading = startHeading + ((endHeading - startHeading) * Math.min(timeSinceInitialized(), duration));

            headingLoop.setSetpoint(desiredHeading);
        }
    }

    @Override
    public boolean isFinished()
    {
        boolean finished = drivebase.profilesAreFinished();

        if(onTarget())
        {
            onTargetTime += (System.currentTimeMillis() - lastOnTargetTimestamp) * 0.001;
            lastOnTargetTimestamp = System.currentTimeMillis();
        }
        else
        {
            onTargetTime = 0;
        }

        finished &= (onTargetTime >= Constants.Motion.DRIVEBASE_IN_RANGE_END_TIME);
        finished |= isTimedOut();
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
        drivebase.stopProfiles();

        if(runHeadingCorrection)
        {
            headingLoop.disable();
        }
    }

    // ohhhhh my god please
    public static void stitch(DriveProfile drive1, DriveProfile drive2)
    {
        CruiseProfile.stitch(drive1.leftProfile, drive2.leftProfile);
        CruiseProfile.stitch(drive1.rightProfile, drive2.rightProfile);
    }

    // this is terrible please put me out of my misery
    public static void stitch(List<DriveProfile> drives)
    {
        List<CruiseProfile> leftProfiles = new ArrayList<>();
        List<CruiseProfile> rightProfiles = new ArrayList<>();

        for(DriveProfile drive : drives)
        {
            leftProfiles.add(drive.leftProfile);
            rightProfiles.add(drive.rightProfile);
        }

        CruiseProfile.stitch(leftProfiles);
        CruiseProfile.stitch(rightProfiles);
    }

    public ArrayList<Command> getActions()
    {
        return actions;
    }
}
