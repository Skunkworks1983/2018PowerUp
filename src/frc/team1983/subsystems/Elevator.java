package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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

    private double setpoint;

    public Elevator()
    {
        left1 = new Motor(Constants.MotorMap.Elevator.LEFT1, Constants.MotorMap.Elevator.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Elevator.LEFT2, Constants.MotorMap.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.MotorMap.Elevator.RIGHT1, Constants.MotorMap.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Elevator.RIGHT2, Constants.MotorMap.Elevator.RIGHT2_REVERSED);

        left2.follow(left1);
        right2.follow(right1);

        left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        //The encoders on the rail
        logger = LoggerFactory.createNewLogger(Elevator.class);
    }

    public void initDefaultCommand()
    {

    }

    public void set(ControlMode mode, double value)
    {
        left1.set(mode, value);
        right1.set(mode, value);
    }

    public void setProfile(MotionProfile profile)
    {

    }

    public double getEncoderValue()
    {
        return left1.getSelectedSensorPosition(0);
    }

    public double getSetpoint()
    {
        return setpoint;
    }

    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
    }
}
