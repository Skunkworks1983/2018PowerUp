package frc.team1983.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

//The elevator subsystem
public class Elevator extends Subsystem
{
    private Motor right1, right2;
    private Motor left1, left2;

    private Logger logger;

    private double setpoint;

    public Elevator()
    {
        logger = LoggerFactory.createNewLogger(Elevator.class);

        right1 = new Motor(Constants.MotorMap.Elevator.RIGHT1, Constants.MotorMap.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Elevator.RIGHT2, Constants.MotorMap.Elevator.RIGHT2_REVERSED);

        left1 = new Motor(Constants.MotorMap.Elevator.LEFT1, Constants.MotorMap.Elevator.LEFT1_REVERSED);
        left2 = new Motor(Constants.MotorMap.Elevator.LEFT2, Constants.MotorMap.Elevator.LEFT2_REVERSED);

        right2.follow(right1);

        left1.follow(right1);
        left2.follow(right1);

        right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        right1.setSensorPhase(true);

        right1.config_kP(Constants.PidConstants.ElevatorControlPid.Up.slot,
                         Constants.PidConstants.ElevatorControlPid.Up.P, 0);
        right1.config_kI(Constants.PidConstants.ElevatorControlPid.Up.slot,
                         Constants.PidConstants.ElevatorControlPid.Up.I, 0);
        right1.config_kD(Constants.PidConstants.ElevatorControlPid.Up.slot,
                         Constants.PidConstants.ElevatorControlPid.Up.D, 0);
        right1.config_kF(Constants.PidConstants.ElevatorControlPid.Up.slot,
                         Constants.PidConstants.ElevatorControlPid.Up.F , 0);

        right1.config_kP(Constants.PidConstants.ElevatorControlPid.Down.slot,
                         Constants.PidConstants.ElevatorControlPid.Down.P, 0);
        right1.config_kI(Constants.PidConstants.ElevatorControlPid.Down.slot,
                         Constants.PidConstants.ElevatorControlPid.Down.I, 0);
        right1.config_kD(Constants.PidConstants.ElevatorControlPid.Down.slot,
                         Constants.PidConstants.ElevatorControlPid.Down.D, 0);
        right1.config_kF(Constants.PidConstants.ElevatorControlPid.Down.slot,
                         Constants.PidConstants.ElevatorControlPid.Down.F, 0);

        right1.configClosedloopRamp(0.5, 0);
        right1.configPeakOutputForward(0.90, 0);
        //right1.config_IntegralZone(0, )

        //right1.set(ControlMode.Position, Constants.PidConstants.ElevatorControlPid.ELEVATOR_TOP - 100);

        right1.selectProfileSlot(Constants.PidConstants.ElevatorControlPid.Up.slot, 0);

        logger.info("Error: {}", right1.getClosedLoopError(0));
    }

    public void initDefaultCommand()
    {

    }

    // calculates ticks given vertical change in position of the carriage
    public double feetToEncoderTicks(double feet)
    {
        double resolution = Constants.MotorMap.Elevator.ENCODER_RESOLUTION;
        double circumference = Constants.MotorMap.Elevator.SPROCKET_CIRCUMFERENCE;
        double ticks = (feet / circumference) * resolution;

        return ticks;
    }

    // calculates vertical change in position of the carriage given ticks
    public double encoderTicksToFeet(double ticks)
    {
        double resolution = Constants.MotorMap.Elevator.ENCODER_RESOLUTION;
        double circumference = Constants.MotorMap.Elevator.SPROCKET_CIRCUMFERENCE;
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
        return left1.isProfileFinished();
    }

    public double getSetpoint()
    {
        return setpoint;
    }

    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
        // code to select correct pid values dependent on direction of elevator travel
        // note: the higher the elevator is, the more negative the value is
        if(this.setpoint < this.getEncoderValue())
        {
            right1.selectProfileSlot(Constants.PidConstants.ElevatorControlPid.Up.slot, 0);
        }
        else
        {
            right1.selectProfileSlot(Constants.PidConstants.ElevatorControlPid.Down.slot, 0);
        }
        right1.set(ControlMode.Position, setpoint);
        logger.info("Value is {}", setpoint);
    }

    @Override
    public void periodic()
    {
        logger.info("Error: {}", right1.getClosedLoopError(0));
    }

    public double getCurrentDraw()
    {
        return (right1.getOutputCurrent() + right2.getOutputCurrent())/2;
    }
}
