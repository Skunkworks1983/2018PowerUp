package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
import frc.team1983.utility.control.Motor;

public class Elevator extends Subsystem
{
    private Motor right1, right2;
    private Motor left1, left2;

    public Elevator()
    {
        left1 = new Motor(Constants.MotorMap.Elevator.LEFT1, Constants.MotorMap.Elevator.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Elevator.LEFT2, Constants.MotorMap.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.MotorMap.Elevator.RIGHT1, Constants.MotorMap.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Elevator.RIGHT2, Constants.MotorMap.Elevator.RIGHT2_REVERSED);

        right1.config_kP(0, Constants.Gains.Elevator.P);
        right1.config_kI(0, Constants.Gains.Elevator.I);
        right1.config_kD(0, Constants.Gains.Elevator.D);
        right1.config_kF(0, 0);
        right1.config_IntegralZone(0, (int) Elevator.toTicks(Constants.Gains.Elevator.I_ZONE));

        right1.configMotionCruiseVelocity((int) Elevator.toTicks(Constants.Motion.ELEVATOR_MAX_VELOCITY));
        right1.configMotionAcceleration((int) Elevator.toTicks(Constants.Motion.ELEVATOR_MAX_ACCELERATION));

        right1.setSensorPhase(true);

        right2.follow(right1);
        left1.follow(right1);
        left2.follow(right1);

        zero();
    }

    @Override
    public void initDefaultCommand()
    {

    }

    @Override
    public void periodic()
    {

    }

    public void zero()
    {
        right1.setSelectedSensorPosition(0);
    }

    public static double toInches(double ticks)
    {
        return ticks * Constants.ELEVATOR_INCHES_PER_TICK;
    }

    public static double toTicks(double inches)
    {
        return inches / Constants.ELEVATOR_INCHES_PER_TICK;
    }

    public double getHeight()
    {
        return toInches(right1.getSelectedSensorPosition());
    }

    public boolean isAtSetpoint()
    {
        return Math.abs(getHeight() - toInches(right1.getClosedLoopTarget())) < Constants.ELEVATOR_ALLOWABLE_ERROR;
    }

    public void set(ControlMode mode, double value)
    {
        right1.set(mode, value, DemandType.ArbitraryFeedForward, Constants.Gains.Elevator.F);
    }

    public void setNeutralMode(boolean coast)
    {
        Motor[] motors = {left1, left2, right1, right2};
        for(Motor motor : motors)
            motor.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
    }
}
