package frc.team1983.commands.drivebase;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

public class DriveFeet extends DriveProfile
{
    public DriveFeet(Drivebase drivebase, double distance, double time, ActionsEnum[] actions)
    {
        super(drivebase, generateProfile(distance, time), generateProfile(distance, time), time, 0, actions);
        setHeadingLoopGains(Constants.PidConstants.Drivebase.AUX_STRAIGHT);
    }

    public DriveFeet(Drivebase drivebase, double distance, double time)
    {
        this(drivebase, distance, time, new ActionsEnum[]{ActionsEnum.NONE});
    }

    // absolute orientation
    public DriveFeet(Drivebase drivebase, double distance, double time, double heading, ActionsEnum[] actions)
    {
        this(drivebase, distance, time, actions);

        useAbsoluteOrientation = true;
        startHeading = heading;
        endHeading = heading;
    }

    public DriveFeet(Drivebase drivebase, double distance, double time, double heading)
    {
        this(drivebase, distance, time, heading, new ActionsEnum[]{ActionsEnum.NONE});
    }

    private static CruiseProfile generateProfile(double distance, double time)
    {
        return new TrapezoidalProfile(Drivebase.getTicks(distance), time);
    }
}
