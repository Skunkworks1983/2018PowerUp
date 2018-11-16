package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.sensors.Pigeon;
import org.apache.logging.log4j.core.Logger;

// Implementation of PidInputWrapper, specifically for the gyro sensor. Simply gets the yaw value of the NavX.
public class GyroPidInput extends PidInputWrapper
{
    Pigeon gyro;
    private Logger logger;

    public GyroPidInput(Pigeon gyro)
    {
        this.gyro = gyro;
        logger = LoggerFactory.createNewLogger(GyroPidInput.class);
    }

    public double pidGet()
    {
        return gyro.getAngle();
    }
}
