package frc.team1983.util.motion.profiles;

import frc.team1983.util.motion.MotionSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CruiseProfile extends ThreeSegmentProfile
{
    public CruiseProfile(double distance, double duration, double initialVelocity, double finalVelocity, double accelTime1, double accelTime2)
    {
        super(generate(distance, duration, initialVelocity, finalVelocity, accelTime1, accelTime2));

        this.duration = duration;

        this.accelTime1 = accelTime1;
        this.accelTime2 = accelTime2;
    }

    private static List<MotionSegment> generate(double distance, double duration, double initialVelocity, double finalVelocity, double accelTime1, double accelTime2)
    {
        List<MotionSegment> segments;

        double cruiseVelocity = (2 * distance) - (accelTime1 * initialVelocity) - (accelTime2 * finalVelocity);
        cruiseVelocity /= (2 * duration) - accelTime1 - accelTime2;

        segments = new ArrayList<>(Arrays.asList(
                new MotionSegment(0, initialVelocity, accelTime1, cruiseVelocity),
                new MotionSegment(accelTime1, cruiseVelocity, duration - accelTime2, cruiseVelocity),
                new MotionSegment(duration - accelTime2, cruiseVelocity, duration, finalVelocity)
        ));

        return segments;
    }

    public double getCruiseVelocity()
    {
        // this is terrible
        return segments.get(0).getEnd().getVelocity();
    }

    // helper function
    protected void setCruiseVelocity(double cruiseVelocity)
    {
        segments.get(0).getEnd().setVelocity(cruiseVelocity);
        segments.get(1).getStart().setVelocity(cruiseVelocity);
        segments.get(1).getEnd().setVelocity(cruiseVelocity);
        segments.get(2).getStart().setVelocity(cruiseVelocity);
    }

    public static void stitch(List<CruiseProfile> profiles)
    {
        List<CruiseProfile> stitched = new ArrayList<>();

        for(int i = 0; i < profiles.size() - 1; i++)
        {
            CruiseProfile.stitch(profiles.get(i), profiles.get(i + 1));

            stitched.add(profiles.get(i));
            if(stitched.size() > 1)
            {
                for(int j = stitched.size() - 2; j >= 0; j--)
                {
                    CruiseProfile.stitch(stitched.get(j), stitched.get(j + 1));
                }
            }
        }
    }

    public static void stitch(CruiseProfile profile1, CruiseProfile profile2)
    {
        double vcAvg = (profile1.getCruiseVelocity() + profile2.getCruiseVelocity()) / 2;

        double d1 = profile1.getDistance();
        double d2 = profile2.getDistance();

        profile1.setFinalVelocity(vcAvg);
        profile2.setInitialVelocity(vcAvg);

        double vc1 = (2 * d1) -
                (profile1.getAccelTime1() * profile1.getInitialVelocity()) -
                (profile1.getAccelTime2() * profile1.getFinalVelocity());
        vc1 /= (2 * profile1.getDuration()) - profile1.getAccelTime1() - profile1.getAccelTime2();

        double vc2 = (2 * d2) -
                (profile2.getAccelTime1() * profile2.getInitialVelocity()) -
                (profile2.getAccelTime2() * profile2.getFinalVelocity());
        vc2 /= (2 * profile2.getDuration()) - profile2.getAccelTime1() - profile2.getAccelTime2();

        profile1.setCruiseVelocity(vc1);
        profile2.setCruiseVelocity(vc2);
    }
}
