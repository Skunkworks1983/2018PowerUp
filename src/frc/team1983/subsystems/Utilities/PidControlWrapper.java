package frc.team1983.subsystems.Utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class PidControlWrapper extends PIDController
{
    public PidControlWrapper(double kP, double kI, double kD, double kF, PIDSource source, PIDOutput output, double period)
    {
        super(kP, kI, kD, kF, source, output, period);
    }

    public PidControlWrapper(double kP, double kI, double kD, PIDSource source, PIDOutput output,
                             double period)
    {
        super(kP, kI, kD, source, output, period);
    }

    public PidControlWrapper(double kP, double kI, double kD, PIDSource source, PIDOutput output)
    {
        super(kP, kI, kD, source, output);
    }

    public PidControlWrapper(double kP, double kI, double kD, double kF, PIDSource source,
                             PIDOutput output)
    {
        super(kP, kI, kD, kF, source, output);
    }
}

