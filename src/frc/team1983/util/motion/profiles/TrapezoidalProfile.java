package frc.team1983.util.motion.profiles;

import frc.team1983.settings.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // this can be recursive but i'm too dumb to do that lol
    public static void stitch(TrapezoidalProfile profile1, TrapezoidalProfile profile2)
    {
        double d = profile1.getDistance() + profile2.getDistance();
        double d2prime = (profile2.getCruiseVelocity() * (profile2.getTotalTime() - profile2.getAccelerationTime2())) + ((profile2.getAccelerationTime2() * profile2.getCruiseVelocity()) / 2);
        double v_cruise1prime = (2.0 * (d - d2prime)) - (profile1.getAccelerationTime1() * profile2.getCruiseVelocity());
        v_cruise1prime /= ((2.0 * profile1.getTotalTime()) - profile1.getAccelerationTime1() - profile1.getAccelerationTime2());

        profile1.segments.get(0).getEnd().setVelocity(v_cruise1prime);
        profile1.segments.get(1).getStart().setVelocity(v_cruise1prime);
        profile1.segments.get(1).getEnd().setVelocity(v_cruise1prime);
        profile1.segments.get(2).getStart().setVelocity(v_cruise1prime);
        profile1.segments.get(2).getEnd().setVelocity(profile2.getCruiseVelocity());

        profile2.segments.get(0).getStart().setVelocity(profile2.getCruiseVelocity());
    }

    public static void stitch(List<TrapezoidalProfile> profiles)
    {
        List<TrapezoidalProfile> stitched = new ArrayList<>();

        for(int i = 0; i < profiles.size() - 1; i++)
        {
            TrapezoidalProfile.stitch(profiles.get(i), profiles.get(i + 1));

            stitched.add(profiles.get(i));
            if(stitched.size() > 1)
            {
                for(int j = stitched.size() - 2; j >= 0; j--)
                {
                    TrapezoidalProfile.stitch(stitched.get(j), stitched.get(j + 1));
                }
            }
        }
    }
}
