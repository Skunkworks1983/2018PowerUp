package frc.team1983.commands.drivebase;

import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

public class TurnDegree extends DriveArc
{
    public TurnDegree(Drivebase drivebase, double angle, double time)
    {
        super(drivebase, 0, angle, time);
    }
}
