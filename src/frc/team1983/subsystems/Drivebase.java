package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
import frc.team1983.utility.sensors.Pigeon;
import frc.team1983.utility.control.Motor;
import frc.team1983.utility.control.ProfilerSignal;

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

        left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        left1.configClosedLoopPeakOutput(0, 1, 0);
        right1.configClosedLoopPeakOutput(0, 1, 0);

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        left1.setSensorPhase(true);
        right1.setSensorPhase(true);
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
        left1.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }

    public void setBrakeMode(boolean brake)
    {
        NeutralMode mode = brake ? NeutralMode.Brake : NeutralMode.Coast;

        left1.setNeutralMode(mode);
        left2.setNeutralMode(mode);
        left3.setNeutralMode(mode);

        right1.setNeutralMode(mode);
        right2.setNeutralMode(mode);
        right3.setNeutralMode(mode);
    }
}

