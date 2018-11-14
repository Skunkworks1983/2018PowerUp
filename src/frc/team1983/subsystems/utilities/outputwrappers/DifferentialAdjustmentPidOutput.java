package frc.team1983.subsystems.utilities.outputwrappers;

import frc.team1983.services.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public class DifferentialAdjustmentPidOutput extends PidOutputWrapper
{
    private Logger logger;
    private DifferentialTurnAnglePidOutput differentialTurnAnglePidOutput;
    public DifferentialAdjustmentPidOutput(DifferentialTurnAnglePidOutput differentialTurnAnglePidOutput)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.differentialTurnAnglePidOutput = differentialTurnAnglePidOutput;
    }

    public void writeHelper(double out)
    {
        differentialTurnAnglePidOutput.setAdjustmentSpeed(out);
    }

}
