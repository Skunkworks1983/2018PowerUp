package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Elevator;

public class MoveElevatorTo extends MoveElevatorBy
{
    public MoveElevatorTo(Elevator elevator, double feet, double time)
    {
        super(elevator, feet - elevator.encoderTicksToFeet(elevator.getEncoderValue()), time);
    }
}
