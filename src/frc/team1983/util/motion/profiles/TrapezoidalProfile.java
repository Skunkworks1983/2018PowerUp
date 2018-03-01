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
        super(distance, duration, (duration * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2.0, (duration * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2, 0, 0);
    }

    public static void stitch(TrapezoidalProfile profile1, TrapezoidalProfile profile2)
    {
        double v_cruise1_new = ((2 * profile1.getDistance()) - (profile1.getAccelerationTime1() * profile2.getCruiseVelocity())) / (profile1.getTotalTime());

        profile1.segments.get(0).getEnd().setVelocity(v_cruise1_new);
        profile1.segments.get(1).getStart().setVelocity(v_cruise1_new);
        profile1.segments.get(1).getEnd().setVelocity(v_cruise1_new);
        profile1.segments.get(2).getStart().setVelocity(v_cruise1_new);
        profile1.segments.get(2).getEnd().setVelocity(profile2.getCruiseVelocity());

        profile2.segments.get(0).getStart().setVelocity(profile2.getCruiseVelocity());
    }
}
