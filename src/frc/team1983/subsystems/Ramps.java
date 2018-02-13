package frc.team1983.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.ServoWrapper;
import org.apache.logging.log4j.core.Logger;

//The ramps of the robot, managing the servos that release the different parts of the mechanism.
public class Ramps extends Subsystem
{
    private ServoWrapper leftDrop, leftProp, rightDrop, rightProp;
    private Logger logger;

    public Ramps()
    {
        leftDrop = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_LEFT_DROP_PORT);
        leftProp = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_LEFT_PROP_PORT);
        rightDrop = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_RIGHT_DROP_PORT);
        rightProp = new ServoWrapper(Constants.MotorMap.RampPorts.RAMP_RIGHT_PROP_PORT);
        logger = LoggerFactory.createNewLogger(Ramps.class);

    }

    public void drop()
    {
        leftDrop.set(Constants.PidConstants.MotorSetpoints.RAMP_DROP_SERVO_GOAL);
        rightDrop.set(Constants.PidConstants.MotorSetpoints.RAMP_DROP_SERVO_GOAL);
    }

    public void prop()
    {
        leftProp.set(Constants.PidConstants.MotorSetpoints.RAMP_PROP_SERVO_GOAL);
        rightProp.set(Constants.PidConstants.MotorSetpoints.RAMP_PROP_SERVO_GOAL);
    }

    public void initDefaultCommand()
    {
    }
}

