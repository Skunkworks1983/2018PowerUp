package frc.team1983.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
        //left1 = new Motor(Constants.Robot.Elevator.LEFT1, Constants.Robot.Elevator.LEFT1_REVERSED, true);
        //left2 = new Motor(Constants.Robot.Elevator.LEFT2, Constants.Robot.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.Robot.Elevator.RIGHT1, Constants.Robot.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.Robot.Elevator.RIGHT2, Constants.Robot.Elevator.RIGHT2_REVERSED);

        right2.follow(right1);

        logger = LoggerFactory.createNewLogger(Elevator.class);
    }

    public void initDefaultCommand()
    {

    }

    // calculates ticks given vertical change in position of the carriage
    public double feetToEncoderTicks(double feet)
    {
        double resolution = Constants.Robot.Elevator.ENCODER_RESOLUTION;
        double circumference = Constants.Robot.Elevator.SPROCKET_CIRCUMFERENCE;
        double ticks = (feet / circumference) * resolution;

        return ticks;
    }

    // calculates vertical change in position of the carriage given ticks
    public double encoderTicksToFeet(double ticks)
    {
        double resolution = Constants.Robot.Elevator.ENCODER_RESOLUTION;
        double circumference = Constants.Robot.Elevator.SPROCKET_CIRCUMFERENCE;
        double feet = (ticks / resolution) * circumference;

        return feet;
    }

    public void set(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }

    public double getEncoderValue()
    {
        return right1.getSelectedSensorPosition(0);
    }

    public void setProfile(MotionProfile profile)
    {
        right1.setProfile(profile);
    }

    public void runProfile()
    {
        right1.runProfile();
    }

    public void stopProfile()
    {
        right1.stopProfile();
    }

    public boolean isProfileFinished()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        right1.getMotionProfileStatus(status);
        return status.isUnderrun;
    }
}
