package frc.team1983.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

//The elevator subsystem
public class Elevator extends Subsystem
{
    // left1 and right1 could probably be grouped together by the follow() function but we'll investigate
    private Motor left1, left2;
    private Motor right1, right2;

    private Logger logger;

    public Elevator()
    {
        left1 = new Motor(Constants.Robot.Elevator.LEFT1, Constants.Robot.Elevator.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.Robot.Elevator.LEFT2, Constants.Robot.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.Robot.Elevator.RIGHT1, Constants.Robot.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.Robot.Elevator.RIGHT2, Constants.Robot.Elevator.RIGHT2_REVERSED);

        left2.follow(left1);
        right2.follow(right1);

        right1.follow(left1);

        logger = LoggerFactory.createNewLogger(Elevator.class);
    }

    public void initDefaultCommand()
    {

    }

    // calculates ticks given vertical change in position of the carriage
    public double feetToEncoderTicks(double feet)
    {
        double resolution = Constants.Robot.Elevator.ENCODER_RESOLUTION;
        double sprocketCircumference = Math.PI * Constants.Robot.Elevator.SPROCKET_DIAMETER;

        return (feet * resolution) / (2 * sprocketCircumference);
    }

    // calculates vertical change in position of the carriage given ticks
    public double encoderTicksToFeet(double ticks)
    {
        // someone who is smart please verify this
        double resolution = Constants.Robot.Elevator.ENCODER_RESOLUTION;
        double sprocketCircumference = Math.PI * Constants.Robot.Elevator.SPROCKET_DIAMETER;

        return 2 * ((ticks * sprocketCircumference) / resolution);
    }

    public void set(ControlMode mode, double value)
    {
        left1.set(mode, value);
    }

    public double getEncoderValue()
    {
        return left1.getSelectedSensorPosition(0);
    }

    public void setProfile(MotionProfile profile)
    {
        left1.setProfile(profile);
    }

    public void runProfile()
    {
        left1.runProfile();
    }

    public void stopProfile()
    {
        left1.stopProfile();
    }

    public boolean isProfileFinished()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        left1.getMotionProfileStatus(status);
        return status.isUnderrun;
    }
}
