package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;
import org.apache.logging.log4j.core.Logger;

//The base of the robot. Consists of the drive train motors, slaved to each other.
public class Drivebase extends Subsystem
{
    private MotorGroup left, right;
    private Motor leftMaster, leftSlave0, leftSlave1, rightMaster, rightSlave0, rightSlave1;

    private static NeutralMode DRIVEBASE_NEUTRAL_MODE = NeutralMode.Coast;

    private Logger drivebaseLogger;

    public Drivebase()
    {
        leftMaster = new Motor(Constants.MotorMap.DrivebasePorts.LEFT_MASTER_PORT, DRIVEBASE_NEUTRAL_MODE, Constants.MotorMap.DrivebaseReversed.LEFT_MASTER_REVERSE, true);
        leftSlave0 = new Motor(Constants.MotorMap.DrivebasePorts.LEFT_SLAVE_0_PORT, DRIVEBASE_NEUTRAL_MODE, Constants.MotorMap.DrivebaseReversed.LEFT_SLAVE_0_REVERSE);
        leftSlave1 = new Motor(Constants.MotorMap.DrivebasePorts.LEFT_SLAVE_1_PORT, DRIVEBASE_NEUTRAL_MODE, Constants.MotorMap.DrivebaseReversed.LEFT_SLAVE_1_REVERSE);
        rightMaster = new Motor(Constants.MotorMap.DrivebasePorts.RIGHT_MASTER_PORT, DRIVEBASE_NEUTRAL_MODE, Constants.MotorMap.DrivebaseReversed.RIGHT_MASTER_REVERSE, true);
        rightSlave0 = new Motor(Constants.MotorMap.DrivebasePorts.RIGHT_SLAVE_0_PORT, DRIVEBASE_NEUTRAL_MODE, Constants.MotorMap.DrivebaseReversed.RIGHT_SLAVE_0_REVERSE);
        rightSlave1 = new Motor(Constants.MotorMap.DrivebasePorts.RIGHT_SLAVE_1_PORT, DRIVEBASE_NEUTRAL_MODE, Constants.MotorMap.DrivebaseReversed.RIGHT_SLAVE_1_REVERSE);

        left = new MotorGroup(leftMaster, false);
        left.addMotor(leftSlave0);
        left.addMotor(leftSlave1);

        //TODO: Make sure this directionality is right.
        right = new MotorGroup(rightMaster, true);
        right.addMotor(rightSlave0);
        right.addMotor(rightSlave1);

        drivebaseLogger = LoggerFactory.createNewLogger(Drivebase.class);

    }

    //Set the output of the left motorgroup
    public void setLeft(double value)
    {
        left.set(value);
    }

    //Set the output of the right motorgroup
    public void setRight(double value)
    {
        right.set(value);
    }

    protected void initDefaultCommand()
    {
    }

    public double getLeftDist()
    {
        return leftMaster.getPosition();
    }

    public double getRightDist()
    {
        return rightMaster.getPosition();
    }
}

