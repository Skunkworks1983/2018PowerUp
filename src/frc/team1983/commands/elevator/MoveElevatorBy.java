package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Elevator;
import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.apache.logging.log4j.core.Logger;

public class MoveElevatorBy extends Command
{
    private Elevator elevator;
    private Logger logger;

    private double feet;
    private double time;

    private MotionProfile profile;

    public MoveElevatorBy(Elevator elevator, double feet, double time)
    {
        requires(elevator);

        this.elevator = elevator;
        this.feet = feet;
        this.time = time;

        profile = new TrapezoidalProfile(Elevator.getTicks(feet), time);
    }

    @Override
    public void initialize()
    {
        elevator.setProfile(profile);
        elevator.runProfile();
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return elevator.isProfileFinished();
    }

    public boolean isOnTarget()
    {
        return false;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {
        elevator.stopProfile();

        logger.info("error: " + elevator.getError());
    }
}
