package frc.team1983.util.motion.profiles;

import frc.team1983.settings.Constants;

public class TrapezoidalProfile extends ThreeSegmentProfile
{
    public TrapezoidalProfile(double distance, double t_total, double t_a1, double t_a2)
    {
        super(distance, t_total, t_a1, t_a2, 0, 0);
    }

    public TrapezoidalProfile(double distance, double t_total)
    {
        super(distance, t_total, (t_total * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, (t_total * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, 0, 0);
    }
}
