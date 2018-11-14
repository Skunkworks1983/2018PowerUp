package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.services.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

public class DrivebaseRotationPidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;
    private Logger logger;

    public DrivebaseRotationPidOutput(Drivebase drivebase)
    {
        logger = LoggerFactory.createNewLogger(DrivebaseRotationPidOutput.class);
        this.drivebase = drivebase;
    }

    public void writeHelper(double out)
    {
        //TODO: Verify directionality
        drivebase.setLeft(ControlMode.PercentOutput, out);
        drivebase.setRight(ControlMode.PercentOutput, -out);
        logger.info("SimpleTurnAngle output{}", out);
    }
}
