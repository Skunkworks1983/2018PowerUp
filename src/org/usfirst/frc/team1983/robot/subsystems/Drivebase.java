package org.usfirst.frc.team1983.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1983.robot.RobotMap;

public class Drivebase extends Subsystem { //conventions
    private Motor left, leftSlave0, leftSlave1, right, rightSlave0, rightSlave1; //Clearer names, like leftMaster, leftSlave0, etc

    public static NeutralMode DRIVEBASE_NEUTRALMODE = NeutralMode.Coast;

    public Drivebase()
    {
        left = new Motor(RobotMap.LEFT_MASTER_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.LEFT_MASTER_REVERSE);
        leftSlave0 = new Motor(RobotMap.LEFT_SLAVE_0_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.LEFT_SLAVE_0_REVERSE);
        leftSlave1 = new Motor(RobotMap.LEFT_SLAVE_1_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.LEFT_SLAVE_1_REVERSE);
        right = new Motor(RobotMap.RIGHT_MASTER_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.RIGHT_MASTER_REVERSE);
        rightSlave0 = new Motor(RobotMap.RIGHT_SLAVE_0_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.RIGHT_SLAVE_0_REVERSE);
        rightSlave1 = new Motor(RobotMap.RIGHT_SLAVE_1_PORT, DRIVEBASE_NEUTRALMODE, RobotMap.RIGHT_SLAVE_1_REVERSE);

        leftSlave0.follow(left); //You should probably also call set() here to make sure the motors are in teh correct mode
        leftSlave1.follow(left);

        rightSlave0.follow(right);
        rightSlave1.follow(right);
    }


    public void initDefaultCommand() {}
}

