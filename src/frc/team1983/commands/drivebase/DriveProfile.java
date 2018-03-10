package frc.team1983.commands.drivebase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.autonomous.actions.Action;
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

    private PIDController headingController;
    private Command action;

    protected double time, delay;

    protected boolean hasFinished = false;
    protected boolean runHeadingCorrection = true;

    protected double startHeading;
    protected double deltaHeading;

    private double inRangeTime = 0;
    private double lastMilli = 0;

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile,
                        double deltaHeading, ActionsEnum action)
    {
        Logger logger = LoggerFactory.createNewLogger(this.getClass());
        logger.info("Drivebase from robot is null: {}", Robot.getInstance().getDrivebase() == null);
        logger.info("Drivebase from constructor is null {}", drivebase == null);

        requires(drivebase);

        this.action = action.getAction().createAction(Robot.getInstance().getCollector(), Robot.getInstance().getElevator());
        this.drivebase = drivebase;
        this.leftProfile = leftProfile;
        this.rightProfile = rightProfile;

        headingController = new PIDController(Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kP(),
                                              Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kI(),
                                              Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kD(),
                                              Constants.PidConstants.Drivebase.HEADINGCORRECTION.get_kF(),
                                              new GyroPidInput(drivebase.getGyro()), new DrivebaseAuxiliaryPidOutput(drivebase));

        this.deltaHeading = deltaHeading;
    }

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile, ActionsEnum action)
    {
        this(drivebase, leftProfile, rightProfile, 0, action);
    }

    @Override
    public void initialize()
    {
        if(runHeadingCorrection)
        {
            headingController.enable();
        }

        drivebase.setLeftProfile(leftProfile);
        drivebase.setRightProfile(rightProfile);

        drivebase.runProfiles();

        startHeading = drivebase.getGyro().getAngle();
    }

    @Override
    public void execute()
    {
        double desiredHeading = startHeading + (deltaHeading * (Math.min(timeSinceInitialized(), time) / time));
        headingController.setSetpoint(desiredHeading);
    }

    @Override
    public boolean isFinished()
    {
        if(isOnTarget())
        {
            inRangeTime += (System.currentTimeMillis() - lastMilli) * 0.001;
        }
        else
        {
            inRangeTime = 0;
        }

        lastMilli = System.currentTimeMillis();

        return hasFinished || drivebase.profilesAreFinished() && isOnTarget() && inRangeTime >= Constants.Motion.DRIVEBASE_IN_RANGE_END_TIME;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {
        hasFinished = true;

        drivebase.stopProfiles();
        headingController.disable();

        System.out.println("FINISHED left error: " + Drivebase.getFeet(drivebase.getLeftError()) +
                                 " , right error: " + Drivebase.getFeet(drivebase.getRightError()));
    }

    public boolean isOnTarget()
    {
        return Math.abs(drivebase.getLeftEncoderValue() - leftProfile.getDistance()) <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE &&
               Math.abs(drivebase.getRightEncoderValue() - rightProfile.getDistance()) <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE;
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

    public Command getAction()
    {
        return action;
    }
}
