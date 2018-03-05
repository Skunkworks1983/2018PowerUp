package frc.team1983.commands.drivebase;

import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

public class DriveArc extends DriveProfile
{
    public DriveArc(Drivebase drivebase, double radius, double angle, double time)
    {
        super(drivebase, generateLeftProfile(drivebase, radius, angle, time), generateRightProfile(drivebase, radius, angle, time), angle);
        this.duration = time;
    }

    private static CruiseProfile generateLeftProfile(Drivebase drivebase, double radius, double angle, double time)
    {
        double leftCircumference = 2 * (radius + Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double leftDistance = (angle / 360) * leftCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(leftDistance), time);
    }

    private static CruiseProfile generateRightProfile(Drivebase drivebase, double radius, double angle, double time)
    {
        double rightCircumference = 2 * (radius - Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double rightDistance = (angle / 360) * rightCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(rightDistance), time);
    }
}
