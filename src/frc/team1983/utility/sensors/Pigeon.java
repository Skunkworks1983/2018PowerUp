package frc.team1983.utility.sensors;

import com.ctre.phoenix.sensors.PigeonIMU;
import frc.team1983.services.LoggerFactory;
import frc.team1983.utility.control.Motor;
import org.apache.logging.log4j.core.Logger;

public class Pigeon extends PigeonIMU
{
    Logger logger = LoggerFactory.createNewLogger(this.getClass());
    private double offset = 0;

    public Pigeon(Motor motor)
    {
        super(motor);
        offset = getFusedHeading();
    }

    public Pigeon(int id)
    {
        super(id);
    }

    public boolean isDead()
    {
        return false; //TODO Write logic
    }

    public void reset()
    {
        offset = getFusedHeading();
    }

    public static int reversed = 1;
    public double getAngle()
    {
        /*
        double[] xyz = new double[3];
        getAccumGyro(xyz);

        ErrorCode code = getAccumGyro(xyz);

        PigeonState state = getState();

        logger.info("pigeon state: {}", state);

        if(code != ErrorCode.OK)
        {
            logger.error("getAngle error: {}", code);
        }
        return xyz[2];
        */
        return (getFusedHeading() - offset) * reversed;
    }
}
