package frc.team1983.utility.motion.profiles;

import frc.team1983.utility.motion.MotionProfile;
import frc.team1983.utility.motion.MotionSegment;

import java.util.List;

public class ThreeSegmentProfile extends MotionProfile
{
    // accelTime1 is first, accelTime3 is second, accelTime2 is third
    protected double accelTime1, accelTime3, accelTime2;

    public ThreeSegmentProfile(List<MotionSegment> segments)
    {
        super(segments);

        if(segments.size() != 3)
        {
            throw new IllegalArgumentException("ThreeSegmentProfile needs exactly three segments to exist");
        }
    }

    public double getAccelTime1()
    {
        return accelTime1;
    }

    public double getAccelTime2()
    {
        return accelTime2;
    }

    public double getAccelTime3()
    {
        return accelTime3;
    }
}
