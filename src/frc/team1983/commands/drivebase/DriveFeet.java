package frc.team1983.commands.drivebase;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

public class DriveFeet extends DriveProfile
{
    public DriveFeet(Drivebase drivebase, double feet, double time)
    {
        super(drivebase, generateProfile(feet, time), generateProfile(feet, time));
    }

    private static CruiseProfile generateProfile(double feet, double time)
    {
        CruiseProfile profile = new TrapezoidalProfile(Drivebase.getTicks(feet), time);
        return profile;
    }
}
