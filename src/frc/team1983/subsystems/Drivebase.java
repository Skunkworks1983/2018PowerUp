package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

//The base of the robot. Consists of the drive train motors, slaved to each other.
public class Drivebase extends Subsystem
{
    private Motor left1, left2, left3;
    private Motor right1, right2, right3;
    private Gyro gyro;

    private Logger logger;

    public Drivebase()
    {
        logger = LoggerFactory.createNewLogger(Drivebase.class);

        left1 = new Motor(Constants.MotorMap.Drivebase.LEFT_1, Constants.MotorMap.Drivebase.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Drivebase.LEFT_2, Constants.MotorMap.Drivebase.LEFT2_REVERSED);
        left3 = new Motor(Constants.MotorMap.Drivebase.LEFT_3, Constants.MotorMap.Drivebase.LEFT3_REVERSED);

        right1 = new Motor(Constants.MotorMap.Drivebase.RIGHT_1, Constants.MotorMap.Drivebase.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Drivebase.RIGHT_2, Constants.MotorMap.Drivebase.RIGHT2_REVERSED);
        right3 = new Motor(Constants.MotorMap.Drivebase.RIGHT_3, Constants.MotorMap.Drivebase.RIGHT3_REVERSED);

        gyro = new Gyro(I2C.Port.kOnboard);

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        left1.setSensorPhase(true);
        right1.setSensorPhase(true);

        left1.configPIDF(0, 0.3, 0, 0, 0.2);
        right1.configPIDF(0, 0.3, 0, 0, 0.2);
    }

    public void initDefaultCommand()
    {
        //setDefaultCommand(new TankDrive(this, MotorMap.getInstance().getOI()));
    }

    public double getFeet(double ticks)
    {
        double resolution = Constants.MotorMap.Drivebase.ENCODER_RESOLUTION;
        double circumference = Constants.MotorMap.Drivebase.WHEEL_CIRCUMFERENCE;
        double reduction = Constants.MotorMap.Drivebase.ENCODER_REDUCTION;

        double feet = (ticks / resolution) * (circumference * reduction);
        return feet;
    }

    public double getTicks(double feet)
    {
        double resolution = Constants.MotorMap.Drivebase.ENCODER_RESOLUTION;
        double circumference = Constants.MotorMap.Drivebase.WHEEL_CIRCUMFERENCE;
        double reduction = Constants.MotorMap.Drivebase.ENCODER_REDUCTION;

        double ticks = (feet / (circumference * reduction)) * resolution;
        return ticks;
    }

    public void setLeft(ControlMode mode, double value)
    {
        left1.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
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

    public double getLeftEncoderVelocity()
    {
        return left1.getSelectedSensorVelocity(0);
    }

    public double getRightEncoderVelocity()
    {
        return right1.getSelectedSensorVelocity(0);
    }

    public void setLeftProfile(MotionProfile profile)
    {
        left1.setProfile(profile);
    }

    public void setRightProfile(MotionProfile profile)
    {
        right1.setProfile(profile);
    }

    public void runProfiles()
    {
        left1.runProfile();
        right1.runProfile();
    }

    public void stopProfiles()
    {
        left1.stopProfile();
        right1.stopProfile();
    }

    public boolean leftProfileIsFinished()
    {
        return left1.isProfileFinished();
    }

    public boolean rightProfileIsFinished()
    {
        return right1.isProfileFinished();
    }

    public boolean profilesAreFinished()
    {
        return leftProfileIsFinished() && rightProfileIsFinished();
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

