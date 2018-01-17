package org.usfirst.frc.team1983.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1983.robot.Robot;
import org.usfirst.frc.team1983.robot.subsystems.Ramps;


public class LowerRamps extends Command
{
    private Ramps ramp;
    public LowerRamps()
    {
       requires(Robot.getInstance().getRamps());
       ramp = new Ramps();
    }

    @Override
    protected void initialize()
    {
        ramp.drop();
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
