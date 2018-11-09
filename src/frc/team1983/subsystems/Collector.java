package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.DigitalInputWrapper;
import frc.team1983.subsystems.utilities.Motor;
import org.apache.logging.log4j.core.Logger;

import static frc.team1983.settings.Constants.OIMap.ALLOWABLE_FOLDOVER_DROP;
import static frc.team1983.settings.Constants.PidConstants.CollectorRotate.UP_TICKS;

//Subsystem that will acquire and expel the Power Cubes.
public class Collector extends Subsystem
{
    private Motor left, right;
    private Motor rotateLeft, rotateRight;
    private double setpoint;
    private DigitalInputWrapper leftSwitch, rightSwitch;
    //The collector subsystem
    private Logger logger;

    public double desiredRotateSetpoint = 0;

    public Collector()
    {
        left = new Motor(Constants.MotorMap.Collector.LEFT, Constants.MotorMap.Collector.LEFT_REVERSED);
        right = new Motor(Constants.MotorMap.Collector.RIGHT, Constants.MotorMap.Collector.RIGHT_REVERSED);

        rotateRight = new Motor(Constants.MotorMap.Collector.ROTATE_RIGHT, Constants.MotorMap.Collector.ROTATE_RIGHT_REVERSED, true);
        rotateLeft = new Motor(Constants.MotorMap.Collector.ROTATE_LEFT, Constants.MotorMap.Collector.ROTATE_LEFT_REVERSED);

        left.setNeutralMode(NeutralMode.Brake);
        right.setNeutralMode(NeutralMode.Brake);

        rotateRight.setNeutralMode(NeutralMode.Brake);

        leftSwitch = new DigitalInputWrapper(Constants.MotorMap.Collector.LEFT_SWITCH, Constants.MotorMap.Collector.LEFT_SWITCH_REVERSED);
        rightSwitch = new DigitalInputWrapper(Constants.MotorMap.Collector.RIGHT_SWITCH, Constants.MotorMap.Collector.RIGHT_SWITCH_REVERSED);

        rotateRight.config_kP(0, Constants.PidConstants.CollectorRotate.P, 0);
        rotateRight.config_kI(0, Constants.PidConstants.CollectorRotate.I, 0);
        rotateRight.config_kD(0, Constants.PidConstants.CollectorRotate.D, 0);
        rotateRight.config_kF(0, Constants.PidConstants.CollectorRotate.F, 0);

        rotateRight.configClosedloopRamp(0.25, 0);
        rotateRight.configPeakOutputForward(0.3, 0);

        rotateRight.setSelectedSensorPosition(0, 0, 0);
        rotateRight.configPeakOutputReverse(-.6, 0);
        rotateRight.selectProfileSlot(0, 0);
        rotateRight.setSensorPhase(false);
        logger = LoggerFactory.createNewLogger(Collector.class);


    }

    public void initDefaultCommand()
    {
    }

    public void setLeft(ControlMode mode, double value)
    {
        left.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        right.set(mode, value);
    }

    public void setRotate(ControlMode mode, double value)
    {
        if(mode == ControlMode.Position)
        {
            desiredRotateSetpoint = value;
            rotateRight.set(ControlMode.Position, getPosition());
        }
        else
        {
            rotateRight.set(mode, value);
        }

        rotateLeft.set(ControlMode.Follower, Constants.MotorMap.Collector.ROTATE_RIGHT);
    }

    public boolean isLeftSwitchDown()
    {
        return leftSwitch.get();
    }

    public boolean isRightSwitchDown()
    {
        return rightSwitch.get();
    }

    public double getPosition()
    {
        return rotateRight.getSelectedSensorPosition(0);
    }

    @Override
    public void periodic()
    {
        if(rotateRight.getControlMode() == ControlMode.Position)
        {
            // only set the setpoint behind the elevator if we're at the top of the elevator
            double minCarriageHeight = Constants.OIMap.Setpoint.TOP.getEncoderTicks() - Constants.OIMap.ALLOWABLE_ERROR_FOLDOVER;
            double setpointFixed = Math.max(desiredRotateSetpoint, Robot.getInstance().getElevator().getEncoderValue() >= minCarriageHeight ? desiredRotateSetpoint : UP_TICKS);

            if(Robot.getInstance().getElevator().setpoint < minCarriageHeight && desiredRotateSetpoint <= ALLOWABLE_FOLDOVER_DROP)
                desiredRotateSetpoint = UP_TICKS;

            rotateRight.set(ControlMode.Position, setpointFixed);
        }
        //logger.trace("Collector error: {}", rotate.getClosedLoopError(0));
    }
}
