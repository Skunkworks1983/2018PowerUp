package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
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
        wristRight.configClosedloopRamp(0.25, 0);
        wristRight.setSelectedSensorPosition(0, 0, 0);

        wristRight.config_kP(0, Constants.Gains.Wrist.P, 0);
        wristRight.config_kI(0, Constants.Gains.Wrist.I, 0);
        wristRight.config_kD(0, Constants.Gains.Wrist.D, 0);
        wristRight.config_kF(0, Constants.Gains.Wrist.F, 0);

        setIntakeNeutralMode(true);
        setWristNeutralMode(true);
    }

    @Override
    public void initDefaultCommand()
    {

    }

    @Override
    public void periodic()
    {

    }

    public static double toDegrees(double ticks)
    {
        return 0;
    }

    public static double toTicks(double degrees)
    {
        return 0;
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
        wristLeft.set(ControlMode.Follower, Constants.MotorMap.Collector.WRIST_LEFT);
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
