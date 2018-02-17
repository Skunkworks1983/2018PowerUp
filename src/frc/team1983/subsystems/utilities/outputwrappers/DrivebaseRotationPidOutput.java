package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.subsystems.Drivebase;

public class DrivebaseRotationPidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;

    public DrivebaseRotationPidOutput(Drivebase drivebase)
    {
        this.drivebase = drivebase;
    }

    public void writeHelper(double out)
    {
        //TODO: Verify directionality
        drivebase.setLeft(ControlMode.PercentOutput, -out);
        drivebase.setRight(ControlMode.PercentOutput, out);
    }
}
