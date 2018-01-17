package org.usfirst.frc.team1983.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1983.robot.Robot;
import org.usfirst.frc.team1983.robot.subsystems.Ramps;

//this props the ramps up
public class PropRamps extends Command
{
    private Ramps ramp;
    public PropRamps()
    {
        requires(Robot.getInstance().getRamps());
        ramp = Robot.getInstance().getRamps();
    }

    @Override
    protected void initialize()
    {
        ramp.prop();
    }

    @Override
    protected void execute()
    {

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
        this.end();
    }
}
