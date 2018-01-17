package org.usfirst.frc.team1983.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1983.robot.RobotMap;
import org.usfirst.frc.team1983.robot.subsystems.utilities.ServoWrapper;
// this subsystem contains methods and servos used to drop or raise the ramps.
public class Ramps extends Subsystem
{

    private ServoWrapper leftDrop, leftProp, rightDrop, rightProp;

    public Ramps()
    {
        leftDrop = new ServoWrapper(RobotMap.RAMP_LEFT_DROP_PORT);
        leftProp = new ServoWrapper(RobotMap.RAMP_LEFT_PROP_PORT);
        rightDrop = new ServoWrapper(RobotMap.RAMP_RIGHT_DROP_PORT);
        rightProp = new ServoWrapper(RobotMap.RAMP_RIGHT_PROP_PORT);
    }

    public void drop() {
        leftDrop.set(RobotMap.RAMP_DROP_SERVO_GOAL);
        rightDrop.set(RobotMap.RAMP_DROP_SERVO_GOAL);
    }

    public void prop() {
        leftProp.set(RobotMap.RAMP_PROP_SERVO_GOAL);
        rightProp.set(RobotMap.RAMP_PROP_SERVO_GOAL);
    }

    public void initDefaultCommand()
    {
    }
}

