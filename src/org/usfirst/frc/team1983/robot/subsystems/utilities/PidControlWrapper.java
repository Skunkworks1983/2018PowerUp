package org.usfirst.frc.team1983.robot.subsystems.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class PidControlWrapper extends PIDController
{
    public PidControlWrapper(double Kp, double Ki, double Kd, double Kf, PIDSource source, PIDOutput output, double period)
    {
        super(Kp, Ki, Kd, Kf, source, output, period);
    }

    public PidControlWrapper(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output,
                             double period)
    {
        super(Kp, Ki, Kd, source, output, period);
    }

    public PidControlWrapper(double Kp, double Ki, double Kd, PIDSource source, PIDOutput output)
    {
        super(Kp, Ki, Kd, source, output);
    }

    public PidControlWrapper(double Kp, double Ki, double Kd, double Kf, PIDSource source,
                             PIDOutput output)
    {
        super(Kp, Ki, Kd, Kf, source, output);
    }
}

