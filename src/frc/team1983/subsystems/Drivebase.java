package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.sensors.Pigeon;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.control.ProfileSignal;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

//The base of the robot. Consists of the drive train motors, slaved to each other.
public class Drivebase extends Subsystem
{
    public Motor left1, left2, left3;
    public Motor right1, right2, right3;
    private ProfileSignal signal;
    private Pigeon gyro;

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

        gyro = new Pigeon(left3);
        signal = new ProfileSignal();

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        left1.setSensorPhase(true);
        right1.setSensorPhase(true);

        left1.setGains(0, Constants.PidConstants.Drivebase.Left.MAIN);
        right1.setGains(0, Constants.PidConstants.Drivebase.Right.MAIN);

        left1.linkSignal(signal);
        right1.linkSignal(signal);
    }

    public void initDefaultCommand()
    {

    }

    public static double getFeet(double ticks)
    {
        double resolution = Constants.MotorMap.Drivebase.ENCODER_RESOLUTION;
        double circumference = Constants.MotorMap.Drivebase.WHEEL_CIRCUMFERENCE;
        double reduction = Constants.MotorMap.Drivebase.ENCODER_REDUCTION;

        double feet = (ticks / resolution) * (circumference * reduction);
        return feet;
    }

    public static double getTicks(double feet)
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

    // u
    public double getLeftEncoderValue()
    {
        return left1.getSelectedSensorPosition(0);
    }

    // u
    public double getRightEncoderValue()
    {
        return right1.getSelectedSensorPosition(0);
    }

    // u
    public double getLeftError()
    {
        return left1.getClosedLoopError(0);
    }

    // u
    public double getRightError()
    {
        return right1.getClosedLoopError(0);
    }

    // u/100ms
    public double getLeftEncoderVelocity()
    {
        return left1.getSelectedSensorVelocity(0);
    }

    // u/100ms
    public double getRightEncoderVelocity()
    {
        return right1.getSelectedSensorVelocity(0);
    }

    // feet
    public double getLeftDistance()
    {
        return getFeet(getLeftEncoderValue());
    }

    // feet
    public double getRightDistance()
    {
        return getFeet(getRightEncoderValue());
    }

    // feet/s
    public double getLeftVelocity()
    {
        return getFeet(getLeftEncoderVelocity() * 10);
    }

    // feet/s
    public double getRightVelocity()
    {
        return getFeet(getRightEncoderVelocity() * 10);
    }

    public void setLeftProfile(MotionProfile profile)
    {
        left1.setProfile(profile);
    }

    public void setRightProfile(MotionProfile profile)
    {
        right1.setProfile(profile);
    }

    public void resetEncoders()
    {
        right1.setSelectedSensorPosition(0, 0, 0);
        left1.setSelectedSensorPosition(0, 0, 0);
    }

    public void runProfiles()
    {
        left1.runProfile();
        right1.runProfile();

        signal.setEnabled(true);
    }

    public void stopProfiles()
    {
        left1.stopProfile();
        right1.stopProfile();

        signal.setEnabled(false);
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

    public synchronized void setLeftAuxiliaryOutput(double auxiliaryOutput)
    {
        left1.setAuxiliaryOutput(auxiliaryOutput);
    }

    public synchronized void setRightAuxiliaryOutput(double auxiliaryOutput)
    {
        right1.setAuxiliaryOutput(auxiliaryOutput);
    }

    public Pigeon getGyro()
    {
        return this.gyro;
    }

    public void setBrakeMode(boolean brake)
    {
        left1.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
        left2.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
        left3.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);

        right1.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
        right2.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
        right3.setNeutralMode(brake ? NeutralMode.Brake : NeutralMode.Coast);
    }
}

