package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.subsystems.Drivebase;

public class DifferentialTurnAnglePidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;
    private double baseSpeed;
    public DifferentialTurnAnglePidOutput(Drivebase drivebase, double baseSpeed)
    {
        this.drivebase = drivebase;
        this.baseSpeed = baseSpeed;
    }

    public void writeHelper(double out)
    {
        drivebase.setLeft(ControlMode.PercentOutput, baseSpeed + out);
        drivebase.setRight(ControlMode.PercentOutput, -baseSpeed + out);
    }
}
