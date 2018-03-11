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

        left1 = new Motor(Constants.MotorMap.Elevator.LEFT1, Constants.MotorMap.Elevator.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Elevator.LEFT2, Constants.MotorMap.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.MotorMap.Elevator.RIGHT1, Constants.MotorMap.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Elevator.RIGHT2, Constants.MotorMap.Elevator.RIGHT2_REVERSED);

        right1.config_kP(0, Constants.PidConstants.ElevatorControlPid.P, 0);
        right1.config_kI(0, Constants.PidConstants.ElevatorControlPid.I, 0);
        right1.config_kI(0, Constants.PidConstants.ElevatorControlPid.D, 0);
        right1.config_kI(0, Constants.PidConstants.ElevatorControlPid.F, 0);

        right1.config_IntegralZone(0, Constants.PidConstants.ElevatorControlPid.I_ZONE, 0);

        right2.follow(right1);
        left1.follow(right1);
        left2.follow(right1);
    }

    public void initDefaultCommand()
    {

    }

    public static double getFeet(double ticks)
    {
        return 0;
    }

    public static double getTicks(double feet)
    {
        return 0;
    }

    public void set(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }

    public double getError()
    {
        return right1.getClosedLoopError(0);
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
        right1.setSensorPhase(true);
        this.setpoint = setpoint;
        right1.set(ControlMode.Position, setpoint);
    }

    public double getCurrentDraw()
    {
        return (right1.getOutputCurrent() + right2.getOutputCurrent())/2;
    }
}
