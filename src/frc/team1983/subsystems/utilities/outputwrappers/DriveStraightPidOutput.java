package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.subsystems.Drivebase;

import javax.naming.ldap.Control;

public class DriveStraightPidOutput extends PidOutputWrapper
{
    Drivebase drivebase;
    double baseSpeed;

    public DriveStraightPidOutput(Drivebase drivebase, double baseSpeed)
    {
        this.drivebase = drivebase;
        this.baseSpeed = baseSpeed;
    }

    public void writeHelper(double out)
    {
        if(baseSpeed + out <= 1 && baseSpeed + out >= -1)
        {
            drivebase.setLeft(ControlMode.PercentOutput, baseSpeed + out);
        }
        else if(baseSpeed + out > 1)
        {
            drivebase.setLeft(ControlMode.PercentOutput, 1);
        }
        else if(baseSpeed + out <-1)
        {
            drivebase.setLeft(ControlMode.PercentOutput, -1);
        }

        if(baseSpeed - out <=1 && baseSpeed - out >= -1)
        {
            drivebase.setRight(ControlMode.PercentOutput, baseSpeed - out);
        }
        else if(baseSpeed - out > 1)
        {
            drivebase.setRight(ControlMode.PercentOutput, 1);
        }
        else if(baseSpeed - out < -1)
        {
            drivebase.setRight(ControlMode.PercentOutput, -1);
        }
    }
}
