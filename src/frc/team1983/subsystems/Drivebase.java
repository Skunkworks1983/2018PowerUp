package frc.team1983.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

//The base of the robot. Consists of the drive train motors, slaved to each other.
public class Drivebase extends Subsystem
{
    public Motor left1, left2, left3;
    public Motor right1, right2, right3;

    private Logger logger;

    public Drivebase()
    {
        left1 = new Motor(Constants.Robot.Drivebase.LEFT_1, Constants.Robot.Drivebase.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.Robot.Drivebase.LEFT_2, Constants.Robot.Drivebase.LEFT2_REVERSED);
        left3 = new Motor(Constants.Robot.Drivebase.LEFT_3, Constants.Robot.Drivebase.LEFT3_REVERSED);

        right1 = new Motor(Constants.Robot.Drivebase.RIGHT_1, Constants.Robot.Drivebase.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.Robot.Drivebase.RIGHT_2, Constants.Robot.Drivebase.RIGHT2_REVERSED);
        right3 = new Motor(Constants.Robot.Drivebase.RIGHT_3, Constants.Robot.Drivebase.RIGHT3_REVERSED);

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        left1.setSensorPhase(true);
        right1.setSensorPhase(true);

        logger = LoggerFactory.createNewLogger(Drivebase.class);

        left1.configPIDF(0, 0.2, 0, 0, 0.2);
        right1.configPIDF(0, 0.2, 0, 0, 0.2);
    }

    public void initDefaultCommand()
    {

    }

    public double encoderTicksToFeet(double ticks)
    {
        double resolution = Constants.Robot.Drivebase.ENCODER_RESOLUTION;
        double circumference = Constants.Robot.Drivebase.WHEEL_CIRCUMFERENCE;
        double reduction = Constants.Robot.Drivebase.ENCODER_REDUCTION;

        double feet = (ticks / resolution) * (circumference * reduction);
        return feet;
    }

    public double feetToEncoderTicks(double feet)
    {
        double resolution = Constants.Robot.Drivebase.ENCODER_RESOLUTION;
        double circumference = Constants.Robot.Drivebase.WHEEL_CIRCUMFERENCE;
        double reduction = Constants.Robot.Drivebase.ENCODER_REDUCTION;

        double ticks = (feet / (circumference * reduction)) * resolution;
        return ticks;
    }

    public void setLeft(ControlMode mode, double value)
    {
        left1.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }

    public double getLeftEncoderValue()
    {
        return left1.getSelectedSensorPosition(0);
    }

    public double getRightEncoderValue()
    {
        return right1.getSelectedSensorPosition(0);
    }

    public double getLeftEncoderVelocity()
    {
        return left1.getSelectedSensorVelocity(0);
    }

    public double getRightEncoderVelocity()
    {
        return right1.getSelectedSensorVelocity(0);
    }

    public void setLeftProfile(MotionProfile profile)
    {
        logger.info("Setting left profile");
        left1.setProfile(profile);
        logger.info("Have set left profile");
    }

    public void setRightProfile(MotionProfile profile)
    {
        right1.setProfile(profile);
    }

    public void runProfiles()
    {
        left1.runProfile();
        //right1.runProfile();
    }

    public void stopProfiles()
    {
        left1.stopProfile();
        //right1.stopProfile();
    }

    public boolean leftProfileIsFinished()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        left1.getMotionProfileStatus(status);
        return status.isUnderrun || (status.isLast && status.activePointValid);
    }

    public boolean rightProfileIsFinished()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        right1.getMotionProfileStatus(status);
        return status.isUnderrun || (status.activePointValid && status.isLast);
    }

    public boolean profilesAreFinished()
    {
        return leftProfileIsFinished() && rightProfileIsFinished();
    }
}

