package frc.team1983.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.ServoWrapper;
import org.apache.logging.log4j.core.Logger;

//The ramps of the robot, managing the servos that release the different parts of the mechanism.
public class Ramps extends Subsystem
{
    private ServoWrapper leftLock, rightLock; // locks for the keys attached to the elevator
    private ServoWrapper leftLeg, rightLeg; // locks for the keys attached to the gas shocks

    private Logger logger;

    public Ramps()
    {
        leftLock = new ServoWrapper(Constants.MotorMap.Ramps.LEFT_RAMPLOCK);
        rightLock = new ServoWrapper(Constants.MotorMap.Ramps.RIGHT_RAMPLOCK);

        leftLeg = new ServoWrapper(Constants.MotorMap.Ramps.LEFT_LEG);
        rightLeg = new ServoWrapper(Constants.MotorMap.Ramps.RIGHT_LEG);

        logger = LoggerFactory.createNewLogger(Ramps.class);
    }

    public void lock(boolean left)
    {
        if(left)
        {
            leftLock.set(0.2);
        }
        else
        {
            rightLock.set(0.2);
        }
    }

    public void unlock(boolean left)
    {
        if(left)
        {
            leftLock.set(1);
        }
        else
        {
            rightLock.set(1);
        }
    }

    public void unprop(boolean left)
    {
        if(left)
        {
            leftLeg.set(1);
        }
        else
        {
            rightLeg.set(1);
        }
    }

    public void prop(boolean left)
    {
        if(left)
        {
            leftLeg.set(0);
        }
        else
        {
            rightLeg.set(0);
        }
    }

    public void initDefaultCommand()
    {

    }
}

