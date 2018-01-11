package org.usfirst.frc.team1983.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1983.robot.RobotMap;

public class Drivebase extends Subsystem
{
    private MotorGroup left, right;
    private Motor leftMaster, leftSlave0, leftSlave1, rightMaster, rightSlave0, rightSlave1;

    private static NeutralMode DRIVEBASE_NEUTRAL_MODE = NeutralMode.Coast;

    public Drivebase()
    {
        leftMaster = new Motor(RobotMap.LEFT_MASTER_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.LEFT_MASTER_REVERSE);
        leftSlave0 = new Motor(RobotMap.LEFT_SLAVE_0_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.LEFT_SLAVE_0_REVERSE);
        leftSlave1 = new Motor(RobotMap.LEFT_SLAVE_1_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.LEFT_SLAVE_1_REVERSE);
        rightMaster = new Motor(RobotMap.RIGHT_MASTER_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.RIGHT_MASTER_REVERSE);
        rightSlave0 = new Motor(RobotMap.RIGHT_SLAVE_0_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.RIGHT_SLAVE_0_REVERSE);
        rightSlave1 = new Motor(RobotMap.RIGHT_SLAVE_1_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.RIGHT_SLAVE_1_REVERSE);

        left = new MotorGroup(leftMaster, false);
        left.addMotor(leftSlave0);
        left.addMotor(leftSlave1);
        right = new MotorGroup(rightMaster, false);
        right.addMotor(rightSlave0);
        right.addMotor(rightSlave1);
    }

    //Set the output of the left motorgroup
    public void setLeft(double value) {
        left.set(value);
    }

    //Set the output of the right motorgroup
    public void setRight(double value) {
        right.set(value);
    }

    protected void initDefaultCommand() {}
}

