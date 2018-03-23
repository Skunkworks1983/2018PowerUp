package frc.team1983.subsystems.sensors;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.PigeonIMU;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.utilities.Motor;
import org.apache.logging.log4j.core.Logger;

public class Pigeon extends PigeonIMU
{
    Logger logger = LoggerFactory.createNewLogger(this.getClass());

    public Pigeon(Motor motor)
    {
        super(motor);
    }

    public Pigeon(int id)
    {
        super(id);
    }

    public boolean isDead()
    {
        return false; //TODO Write logic
    }

    public double getAngle()
    {
        double[] xyz = new double[3];
        getAccumGyro(xyz);

        ErrorCode code = getAccumGyro(xyz);

        if(code != ErrorCode.OK)
        {
            logger.error("getAngle error: {}", code);
        }
        return xyz[2];
    }
}
