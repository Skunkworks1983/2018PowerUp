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

        left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        left1.setSensorPhase(true);
        right1.setSensorPhase(true);

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);
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

    public void setNeutralMode(boolean coast)
    {
        Motor[] motors = {left1, left2, left3, right1, right2, right3};

        for(Motor motor : motors)
            motor.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
    }
}

