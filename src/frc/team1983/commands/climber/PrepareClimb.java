package frc.team1983.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Climber;
import frc.team1983.subsystems.Ramps;


public class PrepareClimb extends Command {
    private Climber climber;
    private Ramps ramp;

    public PrepareClimb(Climber climber, Ramps ramp) {
        requires(climber);
        requires(ramp);
        this.climber = climber;
        this.ramp = ramp;
    }

    @Override
    protected void initialize() {
        climber.stabilize();
    }


    @Override
    protected void execute() {
        ramp.unlock(true);
        climber.deployHooks();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
