package frc.team1983.commands.ramps;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.subsystems.Ramps;

//Activates the servos that kick down the ramp
public class LowerRamps extends Command
{
    private Ramps ramp;

    public LowerRamps(Ramps ramp)
    {
       requires(ramp);
       this.ramp = ramp;
    }

    @Override
    protected void initialize() {}

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
    protected void end() {}

    @Override
    protected void interrupted()
    {
        super.interrupted();
        this.end();
    }
}
