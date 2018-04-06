package frc.team1983.commands.drivebase;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.subsystems.Drivebase;

public class TurnTo extends TurnDegree
{
    public TurnTo(Drivebase drivebase, double heading, double time, ActionsEnum[] actions)
    {
        super(drivebase, heading, time, actions);

        useAbsoluteOrientation = true;
        startHeading = heading;
        endHeading = heading;
    }
}
