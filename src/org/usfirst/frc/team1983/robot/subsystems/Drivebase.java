package org.usfirst.frc.team1983.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1983.robot.RobotMap;

public class Drivebase extends Subsystem
{
    public MotorGroup left, right;

    private static NeutralMode DRIVEBASE_NEUTRAL_MODE = NeutralMode.Coast;

    public Drivebase()
    {
        Motor leftMaster = new Motor(RobotMap.LEFT_MASTER_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.LEFT_MASTER_REVERSE);
        Motor leftSlave0 = new Motor(RobotMap.LEFT_SLAVE_0_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.LEFT_SLAVE_0_REVERSE);
        Motor leftSlave1 = new Motor(RobotMap.LEFT_SLAVE_1_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.LEFT_SLAVE_1_REVERSE);
        Motor rightMaster = new Motor(RobotMap.RIGHT_MASTER_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.RIGHT_MASTER_REVERSE);
        Motor rightSlave0 = new Motor(RobotMap.RIGHT_SLAVE_0_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.RIGHT_SLAVE_0_REVERSE);
        Motor rightSlave1 = new Motor(RobotMap.RIGHT_SLAVE_1_PORT, DRIVEBASE_NEUTRAL_MODE, RobotMap.RIGHT_SLAVE_1_REVERSE);

        left = new MotorGroup(leftMaster, leftSlave0, leftSlave1);
        right = new MotorGroup(rightMaster, rightSlave0, rightSlave1);
    }


    public void initDefaultCommand() {}
}

