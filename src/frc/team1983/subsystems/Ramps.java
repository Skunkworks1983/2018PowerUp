package frc.team1983.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.ServoWrapper;

//The ramps of the robot, managing the servos that release the different parts of the mechanism.
public class Ramps extends Subsystem
{
    private ServoWrapper leftLock, rightLock; // locks for the keys attached to the elevator
    private ServoWrapper leftLeg, rightLeg; // locks for the keys attached to the gas shocks

    public Ramps()
    {
        leftLock = new ServoWrapper(Constants.MotorMap.Ramps.LEFT_RAMPLOCK);
        rightLock = new ServoWrapper(Constants.MotorMap.Ramps.RIGHT_RAMPLOCK);

        leftLeg = new ServoWrapper(Constants.MotorMap.Ramps.LEFT_LEG);
        rightLeg = new ServoWrapper(Constants.MotorMap.Ramps.RIGHT_LEG);
    }

    public void initDefaultCommand()
    {

    }
}

