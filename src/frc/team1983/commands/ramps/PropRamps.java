package frc.team1983.commands.ramps;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.subsystems.Ramps;

//Activates the servos that release the gas tubes, lifting each ramp up.
public class PropRamps extends Command
{
    private Ramps ramp;

    public PropRamps(Ramps ramp)
    {
        requires(ramp);
        this.ramp = ramp;
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
