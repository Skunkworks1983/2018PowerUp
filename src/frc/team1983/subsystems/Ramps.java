package frc.team1983.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Misc;
import frc.team1983.settings.RobotMap;
import frc.team1983.subsystems.utilities.ServoWrapper;

//The ramps of the robot, managing the servos that release the different parts of the mechanism.
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

    public void drop()
    {
        leftDrop.set(Misc.RAMP_DROP_SERVO_GOAL);
        rightDrop.set(Misc.RAMP_DROP_SERVO_GOAL);
    }

    public void prop()
    {
        leftProp.set(Misc.RAMP_PROP_SERVO_GOAL);
        rightProp.set(Misc.RAMP_PROP_SERVO_GOAL);
    }

    public void initDefaultCommand() {}
}

