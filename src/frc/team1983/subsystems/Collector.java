package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
import frc.team1983.commands.collector.SetWristAngle;
import frc.team1983.utility.control.Motor;

public class Collector extends Subsystem
{
    private Motor intakeLeft, intakeRight;
    private Motor wristLeft, wristRight;

    public Collector()
    {
        intakeLeft = new Motor(Constants.MotorMap.Collector.INTAKE_LEFT, Constants.MotorMap.Collector.INTAKE_LEFT_REVERSED);
        intakeRight = new Motor(Constants.MotorMap.Collector.INTAKE_RIGHT, Constants.MotorMap.Collector.INTAKE_RIGHT_REVERSED);

        wristRight = new Motor(Constants.MotorMap.Collector.WRIST_RIGHT, Constants.MotorMap.Collector.WRIST_RIGHT_REVERSED, true);
        wristLeft = new Motor(Constants.MotorMap.Collector.WRIST_LEFT, Constants.MotorMap.Collector.WRIST_LEFT_REVERSED);

        wristRight.setSensorPhase(false);
        wristRight.configClosedloopRamp(0.25);

        wristRight.config_kP(0, Constants.Gains.Wrist.P);
        wristRight.config_kI(0, Constants.Gains.Wrist.I);
        wristRight.config_kD(0, Constants.Gains.Wrist.D);
        wristRight.config_kF(0, 0);

        setIntakeNeutralMode(true);
        setWristNeutralMode(false);

        zero();
    }

    @Override
    public void initDefaultCommand()
    {
        setDefaultCommand(new SetWristAngle(Constants.Setpoints.Wrist.UP));
    }

    @Override
    public void periodic()
    {

    }

    public void zero()
    {
        wristRight.setSelectedSensorPosition(0);
    }

    public static double toDegrees(double ticks)
    {
        return ticks * Constants.WRIST_DEGREES_PER_TICK;
    }

    public static double toTicks(double degrees)
    {
        return degrees / Constants.WRIST_DEGREES_PER_TICK;
    }

    public double getAngle()
    {
        return toDegrees(wristRight.getSelectedSensorPosition());
    }

    public boolean isAtSetpoint()
    {
        return Math.abs(getAngle() - toDegrees(wristRight.getClosedLoopTarget())) < Constants.WRIST_ALLOWABLE_ERROR;
    }

    public void setIntakeLeft(ControlMode mode, double value)
    {
        intakeLeft.set(mode, value);
    }

    public void setIntakeRight(ControlMode mode, double value)
    {
        intakeRight.set(mode, value);
    }

    public void setWrist(ControlMode mode, double value)
    {
        wristRight.set(mode, value);
        wristLeft.set(ControlMode.Follower, Constants.MotorMap.Collector.WRIST_RIGHT);
    }

    public void setIntakeNeutralMode(boolean coast)
    {
        intakeLeft.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
        intakeRight.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
    }

    public void setWristNeutralMode(boolean coast)
    {
        wristLeft.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
        wristRight.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
    }
}
