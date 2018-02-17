package frc.team1983.util.motion.profiles;

import frc.team1983.settings.Constants;
import frc.team1983.util.motion.MotionProfilePoint;

public class TrapezoidalProfile extends ThreeSegmentProfile
{
    // constrained
    public TrapezoidalProfile(double distance, double t_total, double vel_max, double acc_max, double t_a1, double t_a2)
    {
        super(distance, t_total, vel_max, acc_max, t_a1, t_a2, 0, 0);
    }

    // unconstrained
    public TrapezoidalProfile(double distance, double t_total)
    {
        super(distance, t_total, (t_total * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, (t_total * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, 0, 0);
    }
}
