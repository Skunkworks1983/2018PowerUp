package org.usfirst.frc.team1983.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1983.robot.Robot;


public class LowerRamps extends Command
{
    public LowerRamps()
    {
       requires(Robot.getInstance().getRamps());
    }

    @Override
    protected void initialize()
    {
        Robot.getInstance().getRamps().activateLeft();
        Robot.getInstance().getRamps().activateRight();
    }

    @Override
    protected void execute()
    {

    }

    @Override
    protected boolean isFinished()
    {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    protected void end()
    {
        Robot.getInstance().getRamps().disableRamps();
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
        this.end();
    }
}
