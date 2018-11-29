package frc.team1983.autonomous.routines;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.autonomous.AutoRoutine;
import frc.team1983.commands.collector.SetWristAngle;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.elevator.SetElevatorPosition;

public class TestRoutine extends AutoRoutine
{
    public TestRoutine()
    {
        CommandGroup action = new CommandGroup();
        action.addParallel(new SetElevatorPosition(15));
        action.addParallel(new SetWristAngle(45));

        addParallel(action);
        addParallel(new DriveFeet(6));
    }
}
