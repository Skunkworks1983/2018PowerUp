package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;

//time-sensitive initialization
//Implementation of PidInputWrapper, specifically for DifferentialTurnAngle command.
//gets the difference of distance travelled by each side
public class DifferentialTurnAnglePidInput extends PidInputWrapper
{
    private double leftEncoderStart;
    private double rightEncoderStart;
    private Drivebase drivebase;
    private Gyro gyro;

    public DifferentialTurnAnglePidInput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
        leftEncoderStart = drivebase.getLeftDist();
        rightEncoderStart = drivebase.getRightDist();
    }

    public double pidGet()
    {
        return (((drivebase.getLeftDist() - leftEncoderStart) -
                (drivebase.getRightDist() - rightEncoderStart)));

    }


}
