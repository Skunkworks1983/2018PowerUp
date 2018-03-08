package frc.team1983.commands.autonomous.doublecubeautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public class SwitchFarScaleClose extends CommandGroup
{

    public SwitchFarScaleClose(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, GameDataPoller.OwnedSide robotPosition)
    {

    }
}
