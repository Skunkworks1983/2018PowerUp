package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

        gyro = new Gyro(SPI.Port.kMXP);
        gyro.initGyro();

        right1 = new Motor(Constants.MotorMap.Drivebase.RIGHT_1, Constants.MotorMap.Drivebase.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Drivebase.RIGHT_2, Constants.MotorMap.Drivebase.RIGHT2_REVERSED, false);
        right3 = new Motor(Constants.MotorMap.Drivebase.RIGHT_3, Constants.MotorMap.Drivebase.RIGHT3_REVERSED, false);

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
        System.out.println("left set to: " + value);
    }

    public void setRight(ControlMode mode, double value)
    {
        //setDefaultCommand(new TankDrive(this, Robot.getInstance().getOI()));
        right2.set(mode, value);
        System.out.println("right set to: " + value);
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
}

