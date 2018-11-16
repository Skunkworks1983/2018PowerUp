package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
import frc.team1983.utility.control.Motor;

public class Collector extends Subsystem
{
    private Motor intakeLeft, intakeRight;
    private Motor rotateLeft, rotateRight;

    public Collector()
    {
        intakeLeft = new Motor(Constants.MotorMap.Collector.INTAKE_LEFT, Constants.MotorMap.Collector.INTAKE_LEFT_REVERSED);
        intakeRight = new Motor(Constants.MotorMap.Collector.INTAKE_RIGHT, Constants.MotorMap.Collector.INTAKE_RIGHT_REVERSED);

        rotateRight = new Motor(Constants.MotorMap.Collector.ROTATE_RIGHT, Constants.MotorMap.Collector.ROTATE_RIGHT_REVERSED, true);
        rotateLeft = new Motor(Constants.MotorMap.Collector.ROTATE_LEFT, Constants.MotorMap.Collector.ROTATE_LEFT_REVERSED);

        intakeLeft.setNeutralMode(NeutralMode.Brake);
        intakeRight.setNeutralMode(NeutralMode.Brake);

        rotateRight.setNeutralMode(NeutralMode.Brake);

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
    }

    @Override
    public void initDefaultCommand()
    {

    }

    @Override
    public void periodic()
    {

    }

    public void setLeft(ControlMode mode, double value)
    {
        intakeLeft.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        intakeRight.set(mode, value);
    }

    public double getPosition()
    {
        return rotateRight.getSelectedSensorPosition(0);
    }

    public void setRotate(ControlMode mode, double value)
    {
        rotateRight.set(mode, value);
        rotateLeft.set(ControlMode.Follower, Constants.MotorMap.Collector.ROTATE_RIGHT);
    }
}
