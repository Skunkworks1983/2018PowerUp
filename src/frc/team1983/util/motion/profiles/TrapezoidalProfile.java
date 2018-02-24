package frc.team1983.util.motion.profiles;

import frc.team1983.settings.Constants;

/*
trapezoidal profiles are three segment profiles with the constraint
that the first and last point of the profile must be along the x-axis
 */
public class TrapezoidalProfile extends ThreeSegmentProfile
{
    public TrapezoidalProfile(double distance, double duration, double accelTime1, double accelTime2)
    {
        super(distance, duration, accelTime1, accelTime2, 0, 0);
    }

    public TrapezoidalProfile(double distance, double duration)
    {
        super(distance, duration, (duration * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, (duration * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, 0, 0);
    }
}
