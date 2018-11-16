package frc.team1983.commands.drivebase;

import frc.team1983.Robot;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;

public class TurnDegree extends DriveArc
{
    public TurnDegree(Drivebase drivebase, double angle, double time, ActionsEnum[] actions)
    {
        super(drivebase, 0, angle, time, actions);
        setHeadingLoopGains(Robot.getInstance().getTurnGains());
    }

    public TurnDegree(Drivebase drivebase, double angle, double time)
    {
        this(drivebase, angle, time, new ActionsEnum[]{ActionsEnum.NONE});
    }
}
