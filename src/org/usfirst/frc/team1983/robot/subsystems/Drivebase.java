package org.usfirst.frc.team1983.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1983.robot.RobotMap;

public class Drivebase extends Subsystem
{
    private Motor leftMaster, leftSlave0, leftSlave1, rightMaster, rightSlave0, rightSlave1;

    public static NeutralMode DRIVEBASE_NEUTRALMODE = NeutralMode.Coast;

    public Drivebase()
    {
        leftMaster = new Motor(RobotMap.LEFT_MASTER_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.LEFT_MASTER_REVERSE);
        leftSlave0 = new Motor(RobotMap.LEFT_SLAVE_0_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.LEFT_SLAVE_0_REVERSE);
        leftSlave1 = new Motor(RobotMap.LEFT_SLAVE_1_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.LEFT_SLAVE_1_REVERSE);
        rightMaster = new Motor(RobotMap.RIGHT_MASTER_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.RIGHT_MASTER_REVERSE);
        rightSlave0 = new Motor(RobotMap.RIGHT_SLAVE_0_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.RIGHT_SLAVE_0_REVERSE);
        rightSlave1 = new Motor(RobotMap.RIGHT_SLAVE_1_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.RIGHT_SLAVE_1_REVERSE);

        leftSlave0.follow(leftMaster);
        leftSlave1.follow(leftMaster);

        rightSlave0.follow(rightMaster);
        rightSlave1.follow(rightMaster);
    }


    public void initDefaultCommand() {}
}

