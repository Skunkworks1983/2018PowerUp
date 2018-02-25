package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.commands.drivebase.DriveStraight;
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
        //commented out because set() MAYBE doesn't care if values go out of bounds
        //TODO: figure out if set() is actually ok with that

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
            drivebase.setRight(ControlMode.PercentOutput, (baseSpeed - out));
        }
        else if(baseSpeed - out > Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setRight(ControlMode.PercentOutput, Constants.AutoValues.MAX_OUTPUT);
        }
        else if(baseSpeed - out < -Constants.AutoValues.MAX_OUTPUT)
        {
            drivebase.setRight(ControlMode.PercentOutput, -  Constants.AutoValues.MAX_OUTPUT);
        }
        */

        drivebase.setLeft(ControlMode.PercentOutput, baseSpeed + out);
        drivebase.setRight(ControlMode.PercentOutput, (baseSpeed - out));
    }
}
