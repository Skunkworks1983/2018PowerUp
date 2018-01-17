package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.settings.Constants;
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
        double leftSpeed = Robot.getInstance().getOI().getAxis(Constants.OIJoystick.LEFT, Constants.OIJoystickAxis.Y);
        double rightSpeed = Robot.getInstance().getOI().getAxis(Constants.OIJoystick.RIGHT, Constants.OIJoystickAxis.Y);

        Robot.getInstance().getDrivebase().setLeft(leftSpeed);
        Robot.getInstance().getDrivebase().setRight(rightSpeed);
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
