package frc.team1983.commands.drivebase;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

public class DriveFeet extends DriveProfile
{
    public DriveFeet(Drivebase drivebase, double distance, double time)
    {
        super(drivebase, generateProfile(distance, time), generateProfile(distance, time), time, 0);
    }

    private static CruiseProfile generateProfile(double distance, double time)
    {
        return new TrapezoidalProfile(Drivebase.getTicks(distance), time);
    }
}
