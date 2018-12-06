package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
import frc.team1983.utility.control.Motor;

public class Drivebase extends Subsystem
{
    private Motor left1, left2, left3;
    private Motor right1, right2, right3;

    public Drivebase()
    {
        left1 = new Motor(Constants.MotorMap.Drivebase.LEFT_1, Constants.MotorMap.Drivebase.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Drivebase.LEFT_2, Constants.MotorMap.Drivebase.LEFT2_REVERSED);
        left3 = new Motor(Constants.MotorMap.Drivebase.LEFT_3, Constants.MotorMap.Drivebase.LEFT3_REVERSED);

        right1 = new Motor(Constants.MotorMap.Drivebase.RIGHT_1, Constants.MotorMap.Drivebase.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Drivebase.RIGHT_2, Constants.MotorMap.Drivebase.RIGHT2_REVERSED);
        right3 = new Motor(Constants.MotorMap.Drivebase.RIGHT_3, Constants.MotorMap.Drivebase.RIGHT3_REVERSED);

        left1.config_kP(0, Constants.Gains.Drivebase.Left.P);
        left1.config_kI(0, Constants.Gains.Drivebase.Left.I);
        left1.config_kD(0, Constants.Gains.Drivebase.Left.D);
        left1.config_kF(0, 0);
        left1.config_IntegralZone(0, (int) toTicks(Constants.Gains.Drivebase.Left.I_ZONE));

        right1.config_kP(0, Constants.Gains.Drivebase.Right.P);
        right1.config_kI(0, Constants.Gains.Drivebase.Right.I);
        right1.config_kD(0, Constants.Gains.Drivebase.Right.D);
        right1.config_kF(0, 0);
        right1.config_IntegralZone(0, (int) toTicks(Constants.Gains.Drivebase.Right.I_ZONE));

        left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        left1.setSensorPhase(true);
        right1.setSensorPhase(true);

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

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
        left1.setSelectedSensorPosition(0);
    }

    public static double toInches(double ticks)
    {
        return ticks * Constants.DRIVEBASE_INCHES_PER_TICK;
    }

    public static double toTicks(double inches)
    {
        return inches / Constants.DRIVEBASE_INCHES_PER_TICK;
    }

    public double getLeftPosition()
    {
        return toInches(left1.getSelectedSensorPosition());
    }

    public double getRightPosition()
    {
        return toInches(right1.getSelectedSensorPosition());
    }

    public double getLeftVelocity()
    {
        return toInches(left1.getSelectedSensorVelocity() * 10);
    }

    public double getRightVelocity()
    {
        return toInches(right1.getSelectedSensorVelocity() * 10);
    }

    public void setLeft(ControlMode mode, double value)
    {
        left1.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }

    public void setNeutralMode(boolean coast)
    {
        Motor[] motors = {left1, left2, left3, right1, right2, right3};
        for(Motor motor : motors)
            motor.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
    }
}

