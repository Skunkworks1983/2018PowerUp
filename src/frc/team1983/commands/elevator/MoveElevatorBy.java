package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Elevator;
import frc.team1983.util.motion.MotionProfile;

public class MoveElevatorBy extends Command
{
    private Elevator elevator;

    private double feet;
    private double time;

    public MoveElevatorBy(Elevator elevator, double feet, double time)
    {
        requires(elevator);

        this.elevator = elevator;
        this.feet = feet;
        this.time = time;
    }

    @Override
    public void initialize()
    {
        elevator.setProfile(new MotionProfile(elevator.feetToEncoderTicks(feet), time));
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

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {

    }
}
