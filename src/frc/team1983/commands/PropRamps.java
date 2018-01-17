package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.subsystems.Ramps;

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
    protected void initialize() {}

    @Override
    protected void execute()
    {
        ramp.prop();
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end() {}

    @Override
    protected void interrupted()
    {
        super.interrupted();
        this.end();
    }
}
