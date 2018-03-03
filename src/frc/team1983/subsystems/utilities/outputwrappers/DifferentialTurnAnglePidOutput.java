package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

public class DifferentialTurnAnglePidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;
    private double baseSpeed;
    private Logger logger;
    private double adjustmentSpeed;
    private DifferentialTurnAngle differentialTurnAngle;
    public DifferentialTurnAnglePidOutput(Drivebase drivebase, double baseSpeed)
    {
        this.drivebase = drivebase;
        this.baseSpeed = baseSpeed;
        this.differentialTurnAngle = differentialTurnAngle;
        logger = LoggerFactory.createNewLogger(DifferentialTurnAnglePidOutput.class);
    }
    public void setAdjustmentSpeed(double speed)
    {
        adjustmentSpeed = speed ;
        //logger.info("baseSpeed{}", baseSpeed);
    }


    public void writeHelper(double out)

    {
            drivebase.setLeft(ControlMode.PercentOutput, (baseSpeed + adjustmentSpeed) + out);
            drivebase.setRight(ControlMode.PercentOutput, (-baseSpeed - adjustmentSpeed) + out);

    }
}
