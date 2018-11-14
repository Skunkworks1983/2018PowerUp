package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.services.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

public class DifferentialTurnAnglePidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;
    private double baseSpeed;
    private Logger logger;
    private double adjustmentSpeed;
    public DifferentialTurnAnglePidOutput(Drivebase drivebase, double baseSpeed)
    {
        this.drivebase = drivebase;
        this.baseSpeed = baseSpeed;
        logger = LoggerFactory.createNewLogger(DifferentialTurnAnglePidOutput.class);
    }
    public void setAdjustmentSpeed(double speed)
    {
        adjustmentSpeed = speed;
        //logger.info("adjustmentSpeed{}", speed);
    }


    public void writeHelper(double out)

    {
            drivebase.setLeft(ControlMode.PercentOutput, (-baseSpeed - adjustmentSpeed) + out);
            drivebase.setRight(ControlMode.PercentOutput, (baseSpeed + adjustmentSpeed) + out);
            logger.info("adjustmentSpeed{} \t total speed left{} \t total speed right{}", adjustmentSpeed,(baseSpeed + adjustmentSpeed) + out, (-baseSpeed - adjustmentSpeed) + out);

    }
}
