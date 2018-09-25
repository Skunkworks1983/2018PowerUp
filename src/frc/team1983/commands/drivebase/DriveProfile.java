package frc.team1983.commands.drivebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.wpi.first.wpilibj.PIDController;
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

public class DriveProfile extends CommandBase
{
    protected Logger logger;

    protected Drivebase drivebase;
    public CruiseProfile leftProfile, rightProfile;
    public double duration;

    private PIDController headingLoop;
    private GyroPidInput pidInput;
    private DrivebaseAuxiliaryPidOutput pidOutput;

    public boolean useAbsoluteOrientation = false;
    public boolean runHeadingCorrection = true;
    public double startHeading;
    public double endHeading;
    public double deltaHeading;

    protected ArrayList<CommandBase> actions;

    protected boolean stitched = false;

    private double onTargetTime = 0;
    private double lastTimeMillis = 0;

    private double totalDistance;
    public double actionDelay = 0;

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile, double duration,
                        double deltaHeading, ActionsEnum[] actions, double actionDelay)
    {
        requires(drivebase);
        setTimeout(duration + 1.5);

        this.actionDelay = actionDelay;
        this.actions = new ArrayList<>();

        for(ActionsEnum action : actions)
        {
            this.actions.add(action.getAction().createAction(Robot.getInstance().getCollector(), Robot.getInstance().getElevator()));
            this.actions.get(this.actions.size() - 1).configTimeout(duration);
        }

        this.drivebase = drivebase;
        this.leftProfile = leftProfile;
        this.rightProfile = rightProfile;

        totalDistance = (Drivebase.getFeet(leftProfile.getDistance()) + Drivebase.getFeet(rightProfile.getDistance())) / 2;

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

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile, double duration,
                        double deltaHeading, ActionsEnum[] actions)
    {
        this(drivebase, leftProfile, rightProfile, duration, deltaHeading, actions, 0);
    }

    @Override
    public void initialize()
    {
        if(runHeadingCorrection)
        {
            if(!useAbsoluteOrientation)
            {
                startHeading = drivebase.getGyro().getAngle();
                endHeading = startHeading + deltaHeading;
            }

            headingLoop.enable();
        }

        if(leftProfile.getCruiseVelocity() != 0 && rightProfile.getCruiseVelocity() != 0)
        {
            drivebase.setLeftProfile(leftProfile);
            drivebase.setRightProfile(rightProfile);

            drivebase.runProfiles();
        }
    }

    @Override
    public void execute()
    {
        if(runHeadingCorrection)
        {
            double desiredHeading = startHeading;

            if(!useAbsoluteOrientation)
            {
                double dL = Math.abs(drivebase.getLeftDistance());
                double dR = Math.abs(drivebase.getRightDistance());

                double percentDist = ((dL + dR) / 2) / Math.abs(totalDistance);
                percentDist = Math.min(1, Math.max(percentDist, 0));
                double percentTime = Math.min(timeSinceInitialized(), duration) / duration;

                double percent = percentTime;

                desiredHeading = startHeading + (percent * deltaHeading);
            }
            else
            {
                desiredHeading = endHeading;
            }

            headingLoop.setSetpoint(desiredHeading);
        }
    }

    @Override
    public boolean isFinished()
    {
        boolean finished = drivebase.profilesAreFinished();

        if(onTarget() && lastTimeMillis != 0)
        {
            onTargetTime += (System.currentTimeMillis() - lastTimeMillis) * 0.001;
        }
        else
        {
            onTargetTime = 0;
        }

        lastTimeMillis = System.currentTimeMillis();

        finished &= (onTargetTime >= Constants.Motion.DRIVEBASE_IN_RANGE_END_TIME);

        finished |= isTimedOut();

        return finished;
    }

    private boolean onTarget()
    {
        boolean isOnTarget = true;

        if(!stitched)
        {
            isOnTarget = (Math.abs(drivebase.getLeftError()) <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE) &&
                    (Math.abs(drivebase.getRightError()) <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE);
        }

        if(runHeadingCorrection)
        {
            isOnTarget &= (Math.abs(endHeading - drivebase.getGyro().getAngle())) <= Constants.Motion.DRIVEBASE_HEADING_END_RANGE;
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

        drivebase.setRightAuxiliaryOutput(0);
        drivebase.setLeftAuxiliaryOutput(0);
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

    public DriveProfile setProfileTimeout(double timeout)
    {
        setTimeout(timeout);
        return this;
    }
}
