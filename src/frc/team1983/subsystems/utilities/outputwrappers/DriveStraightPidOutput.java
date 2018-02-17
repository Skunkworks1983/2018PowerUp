package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;

import javax.naming.ldap.Control;

public class DriveStraightPidOutput extends PidOutputWrapper
{
    private Drivebase drivebase;
    private double baseSpeed;

    public DriveStraightPidOutput(Drivebase drivebase, double baseSpeed)
    {
        this.drivebase = drivebase;
        this.baseSpeed = baseSpeed;
    }

    public void writeHelper(double out)
    {
        drivebase.setLeft(ControlMode.PercentOutput, baseSpeed);
        drivebase.setRight(ControlMode.PercentOutput, baseSpeed);
        /*
        if(baseSpeed + out <= Constants.AutoValues.MAX_OUTPUT && baseSpeed + out >= -Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setLeft(ControlMode.PercentOutput, baseSpeed + out);
        }
        else if(baseSpeed + out > Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setLeft(ControlMode.PercentOutput, Constants.AutoValues.MAX_OUTPUT);
        }
        else if(baseSpeed + out <-Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setLeft(ControlMode.PercentOutput, -Constants.AutoValues.MAX_OUTPUT);
        }

        if(baseSpeed - out <= Constants.AutoValues.MAX_OUTPUT && baseSpeed - out >= -Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setRight(ControlMode.PercentOutput, -(baseSpeed - out));
        }
        else if(baseSpeed - out > Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setRight(ControlMode.PercentOutput, -Constants.AutoValues.MAX_OUTPUT);
        }
        else if(baseSpeed - out < -Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setRight(ControlMode.PercentOutput, Constants.AutoValues.MAX_OUTPUT);
        }*/
    }
}
