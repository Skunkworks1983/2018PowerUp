package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.TankDrive;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;
import org.apache.logging.log4j.core.Logger;

//The base of the robot. Consists of the drive train motors, slaved to each other.
public class Drivebase extends Subsystem
{
    private Motor left1, left2, left3;
    private Motor right1, right2, right3;
    private Gyro gyro;

    private static NeutralMode DRIVEBASE_NEUTRAL_MODE = NeutralMode.Coast;

    private Logger logger;

    public Drivebase()
    {
        left1 = new Motor(Constants.MotorMap.Drivebase.LEFT_1, Constants.MotorMap.Drivebase.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Drivebase.LEFT_2, Constants.MotorMap.Drivebase.LEFT2_REVERSED, false);
        left3 = new Motor(Constants.MotorMap.Drivebase.LEFT_3, Constants.MotorMap.Drivebase.LEFT3_REVERSED, false);

        gyro = new Gyro(I2C.Port.kOnboard);

        right1 = new Motor(Constants.MotorMap.Drivebase.RIGHT_1, Constants.MotorMap.Drivebase.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Drivebase.RIGHT_2, Constants.MotorMap.Drivebase.RIGHT2_REVERSED, false);
        right3 = new Motor(Constants.MotorMap.Drivebase.RIGHT_3, Constants.MotorMap.Drivebase.RIGHT3_REVERSED, false);

        left1.setSensorPhase(true);
        right1.setSensorPhase(true);

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        logger = LoggerFactory.createNewLogger(Drivebase.class);
    }

    public void initDefaultCommand()
    {
        //setDefaultCommand(new TankDrive(this, Robot.getInstance().getOI()));
    }

    public void setLeft(ControlMode mode, double value)
    {
        left1.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        //setDefaultCommand(new TankDrive(this, Robot.getInstance().getOI()));
        right1.set(mode, value);
    }

    public double getLeftEncoderValue()
    {
        return left1.getSelectedSensorPosition(0);
    }

    public double getRightEncoderValue()
    {
        return right1.getSelectedSensorPosition(0);
    }

    public Gyro getGyro()
    {
        return this.gyro;
    }

    public double getLeftDist()
    {
        return (getLeftEncoderValue()/Constants.MotorMap.DrivebaseConstants.DRIVEBASE_TICKS_PER_FOOT)*Constants.AutoValues.DRIVEBASE_ENCODER_FUDGE_FACTOR;
    }
    public double getRightDist()
    {
        return (getRightEncoderValue()/Constants.MotorMap.DrivebaseConstants.DRIVEBASE_TICKS_PER_FOOT)*Constants.AutoValues.DRIVEBASE_ENCODER_FUDGE_FACTOR;
    }
    public void setBrakeMode(boolean isBrake)
    {
        if(isBrake)
        {
            left1.setNeutralMode(NeutralMode.Brake);
            left2.setNeutralMode(NeutralMode.Brake);
            left3.setNeutralMode(NeutralMode.Brake);

            right1.setNeutralMode(NeutralMode.Brake);
            right2.setNeutralMode(NeutralMode.Brake);
            right3.setNeutralMode(NeutralMode.Brake);
        }
        else
        {
            left1.setNeutralMode(NeutralMode.Coast);
            left2.setNeutralMode(NeutralMode.Coast);
            left3.setNeutralMode(NeutralMode.Coast);

            right1.setNeutralMode(NeutralMode.Coast);
            right2.setNeutralMode(NeutralMode.Coast);
            right3.setNeutralMode(NeutralMode.Coast);
        }

    }
}

