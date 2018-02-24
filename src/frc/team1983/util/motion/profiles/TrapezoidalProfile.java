package frc.team1983.util.motion.profiles;

import frc.team1983.settings.Constants;

public class TrapezoidalProfile extends ThreeSegmentProfile
{
    public TrapezoidalProfile(double distance, double duration, double t_a1, double t_a2)
    {
        super(distance, duration, t_a1, t_a2, 0, 0);
    }

    public TrapezoidalProfile(double distance, double duration)
    {
        super(distance, duration, (duration * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, (duration * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, 0, 0);
    }
}
