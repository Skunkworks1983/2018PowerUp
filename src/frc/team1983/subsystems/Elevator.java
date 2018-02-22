package frc.team1983.subsystems;

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

        right1.config_kP(0, Constants.PidConstants.ElevatorControlPid.P, 0);
        right1.config_kI(0, Constants.PidConstants.ElevatorControlPid.I, 0);
        right1.config_kD(0, Constants.PidConstants.ElevatorControlPid.D, 0);
        right1.config_kF(0, Constants.PidConstants.ElevatorControlPid.F, 0);

        right1.configClosedloopRamp(0.5, 0);
        right1.configPeakOutputForward(0.90, 0);
        //right1.config_IntegralZone(0, )

        //right1.set(ControlMode.Position, Constants.PidConstants.ElevatorControlPid.ELEVATOR_TOP - 100);

        right1.selectProfileSlot(0, 0);

        logger.info("Error: {}", right1.getClosedLoopError(0));
    }

    public void initDefaultCommand()
    {

    }

    //Called only for testing and manual override
    public void set(ControlMode mode, double value)
    {
        right1.set(mode, value);
        right2.set(mode, value);
    }

    public void setProfile(MotionProfile profile)
    {

    }

    public double getEncoderValue()
    {
        return right1.getSelectedSensorPosition(0);
    }

    public double getSetpoint()
    {
        return setpoint;
    }

    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
        right1.set(ControlMode.Position, setpoint);
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
