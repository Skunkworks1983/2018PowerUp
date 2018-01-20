package frc.team1983.commands.ramps;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Ramps;

//Activates the servos that kick down the ramp
public class LowerRamps extends CommandBase
{
    private Ramps ramp;

    public LowerRamps(Ramps ramp)
    {
       requires(ramp);
       this.ramp = ramp;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute()
    {
        ramp.drop();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end() {}

    @Override
    public void interrupted()
    {
        this.end();
    }
}
