package org.usfirst.frc.team1983.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1983.robot.Robot;


public class TankDrive extends Command
{
    public TankDrive()
    {
        requires(Robot.getInstance().getDrivebase());
    }

    @Override
    protected void initialize()
    {
    //this year doesn't seem to have any kind of setEnabled for talons? Worth looking into
    }

    @Override
    protected void execute()
    {
        double leftSpeed = Robot.getInstance().getOI().leftStick.getY();
        double rightSpeed = Robot.getInstance().getOI().rightStick.getY();
        //set tolerances
        if (leftSpeed < 0.05 && leftSpeed > -0.05) {
            Robot.getInstance().getDrivebase().setLeft(0);
        }
        else {
            Robot.getInstance().getDrivebase().setLeft(leftSpeed);
        }
        //set tolerances
        if (rightSpeed < 0.05 && rightSpeed > -0.05) {
            Robot.getInstance().getDrivebase().setRight(0);
        }
        else {
            Robot.getInstance().getDrivebase().setRight(rightSpeed);
        }
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
