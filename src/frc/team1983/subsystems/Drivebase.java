package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

//The base of the robot. Consists of the drive train motors, slaved to each other.
public class Drivebase extends Subsystem
{
    private Motor left1, left2, left3;
    private Motor right1, right2, right3;

    private Logger logger;

    public Drivebase()
    {
        left1 = new Motor(Constants.Robot.Drivebase.LEFT_1, Constants.Robot.Drivebase.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.Robot.Drivebase.LEFT_2, Constants.Robot.Drivebase.LEFT2_REVERSED);
        left3 = new Motor(Constants.Robot.Drivebase.LEFT_3, Constants.Robot.Drivebase.LEFT3_REVERSED);

        right1 = new Motor(Constants.Robot.Drivebase.RIGHT_1, Constants.Robot.Drivebase.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.Robot.Drivebase.RIGHT_2, Constants.Robot.Drivebase.RIGHT2_REVERSED);
        right3 = new Motor(Constants.Robot.Drivebase.RIGHT_3, Constants.Robot.Drivebase.RIGHT3_REVERSED);

        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);

        logger = LoggerFactory.createNewLogger(Drivebase.class);
    }

    public void initDefaultCommand()
    {

    }

    public void setLeft(ControlMode mode, double value)
    {
        left1.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        right2.set(mode, value);
    }

    public double getLeftEncoderValue()
    {
        return left1.getSelectedSensorPosition(0);
    }

    public double getRightEncoderValue()
    {
        return right1.getSelectedSensorPosition(0);
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
}

