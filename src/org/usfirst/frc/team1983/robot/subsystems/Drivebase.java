package org.usfirst.frc.team1983.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.usfirst.frc.team1983.robot.RobotMap;

public class Drivebase extends Subsystem {
    private Gyro gyro;
    private Motor left, leftSlave0, leftSlave1, right, rightSlave0, rightSlave1;

    public Drivebase()
    {
        left = new Motor(RobotMap.LEFT_MASTER_PORT);
        leftSlave0 = new Motor(RobotMap.LEFT_SLAVE_0_PORT);
        leftSlave1 = new Motor(RobotMap.LEFT_SLAVE_1_PORT);
        right = new Motor(RobotMap.RIGHT_MASTER_PORT);
        rightSlave0 = new Motor(RobotMap.RIGHT_SLAVE_0_PORT);
        rightSlave1 = new Motor(RobotMap.RIGHT_SLAVE_1_PORT);

        leftSlave0.follow(left);
        leftSlave1.follow(left);

        rightSlave0.follow(right);
        rightSlave1.follow(right);
    }


    public void initDefaultCommand() {}
}

