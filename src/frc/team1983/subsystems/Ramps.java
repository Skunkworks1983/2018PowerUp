package frc.team1983.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.ServoWrapper;

//The ramps of the robot, managing the servos that release the different parts of the mechanism.
public class Ramps extends Subsystem
{
    private ServoWrapper leftDrop, leftProp, rightDrop, rightProp;

    public Ramps()
    {
        leftDrop = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_LEFT_DROP_PORT);
        leftProp = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_LEFT_PROP_PORT);
        rightDrop = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_RIGHT_DROP_PORT);
        rightProp = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_RIGHT_PROP_PORT);
    }

    public void drop()
    {
        leftDrop.set(Constants.MotorMap.MotorGoals.RAMP_DROP_SERVO_GOAL);
        rightDrop.set(Constants.MotorMap.MotorGoals.RAMP_DROP_SERVO_GOAL);
    }

    public void prop()
    {
        leftProp.set(Constants.MotorMap.MotorGoals.RAMP_PROP_SERVO_GOAL);
        rightProp.set(Constants.MotorMap.MotorGoals.RAMP_PROP_SERVO_GOAL);
    }

    public void initDefaultCommand() {}
}

