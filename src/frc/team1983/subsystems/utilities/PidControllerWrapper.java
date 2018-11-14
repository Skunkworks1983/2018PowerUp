package frc.team1983.subsystems.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.services.LoggerFactory;

/*Class that wraps the PIDController class provided by WpiLib. Not used for any extra functionality yet, but should be
  the default implementation in our codebase.
*/
public class PidControllerWrapper extends PIDController
{
    private double p, i, d, f;
    private PIDSource source;
    private PIDOutput output;
    private org.apache.logging.log4j.core.Logger logger;

    public PidControllerWrapper(double p, double i, double d, double f, PIDSource source, PIDOutput output)
    {
        super(p, i, d, f, source, output);
        logger = LoggerFactory.createNewLogger(PidControllerWrapper.class);
        logger.info("PidControllerWrapper initialized");
    }

    public PidControllerWrapper(double p, double i, double d, PIDSource source, PIDOutput output)
    {
        super(p, i, d, source, output);
    }
}
