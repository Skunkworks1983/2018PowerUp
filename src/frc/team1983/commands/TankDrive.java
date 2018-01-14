package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.OIMap;

//Default command for Drivebase during Teleop
public class TankDrive extends Command
{
    public TankDrive()
    {
        requires(Robot.getInstance().getDrivebase());
    }

    @Override
    protected void initialize()
    {
        //TODO: look into talon setEnabled()
    }

    @Override
    protected void execute()
    {
        double leftSpeed = Robot.getInstance().getOI().leftStick.getY();
        double rightSpeed = Robot.getInstance().getOI().rightStick.getY();
        //set tolerances
        if (leftSpeed < Misc.JOYSTICK_TOLERANCE && leftSpeed > -Misc.JOYSTICK_TOLERANCE)
        {
            Robot.getInstance().getDrivebase().setLeft(0);
        }
        else
        {
            Robot.getInstance().getDrivebase().setLeft(leftSpeed);
        }
        //set tolerances
        if (rightSpeed < Misc.JOYSTICK_TOLERANCE && rightSpeed > -Misc.JOYSTICK_TOLERANCE)
        {
            Robot.getInstance().getDrivebase().setRight(0);
        }
        else
        {
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
        Robot.getInstance().getDrivebase().setLeft(0);
        Robot.getInstance().getDrivebase().setRight(0);
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        this.end();
    }
}
