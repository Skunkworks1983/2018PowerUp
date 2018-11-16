package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;
import org.apache.logging.log4j.core.Logger;

//time-sensitive initialization
//Implementation of PidInputWrapper, specifically for DifferentialTurnAngle command.
//gets the difference of distance travelled by each side
public class DifferentialTurnAnglePidInput extends PidInputWrapper
{
    private double leftEncoderStart;
    private double rightEncoderStart;
    private Drivebase drivebase;

    public DifferentialTurnAnglePidInput(Drivebase drivebase)
    {

        this.drivebase = drivebase;
        leftEncoderStart = drivebase.getLeftDistance();
        rightEncoderStart = drivebase.getRightDistance();
    }

    public double pidGet()
    {
        return (((drivebase.getLeftDistance() - leftEncoderStart) -
                (drivebase.getRightDistance() - rightEncoderStart)));
    }
}
