package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

public class EncoderTurnAnglePidInput extends PidInputWrapper
{
    private double distance;
    private Logger logger;
    public EncoderTurnAnglePidInput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
        logger = LoggerFactory.createNewLogger(EncoderTurnAnglePidInput.class);
    }

    private Drivebase drivebase;

    public double pidGet()
    {
        distance = (drivebase.getLeftDistance()-drivebase.getRightDistance()) / 2;
        distance = distance / (Constants.AutoValues.WHEELBASE_RADIUS);
        distance = distance * (360/(2*Math.PI));

        return (distance);
    }
}
