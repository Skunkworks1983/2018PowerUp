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

    public void lock()
    {
        leftLock.set(0);
        rightLock.set(0);
    }

    public void unlock()
    {
        leftLock.set(1);
        rightLock.set(1);
    }

    public void unprop()
    {
        leftLeg.set(1);
        rightLeg.set(1);
    }

    public void prop()
    {
        leftLeg.set(0);
        rightLeg.set(0);
    }

    public void initDefaultCommand()
    {

    }
}

