package frc.team1983.commands.autonomous.actions;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Elevator;

public interface Action
{
    public Command createAction(Collector collector, Elevator elevator);
}
