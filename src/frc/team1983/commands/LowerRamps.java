package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.subsystems.Ramps;

//this activates the servos that kick down the ramp
public class LowerRamps extends Command
{
    private Ramps ramp;
    public LowerRamps()
    {
       requires(Robot.getInstance().getRamps());
       ramp = Robot.getInstance().getRamps();
    }

    @Override
    protected void initialize()
    {
    }

    @Override
    protected void execute()
    {
        ramp.drop();
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
