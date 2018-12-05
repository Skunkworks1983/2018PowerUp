package frc.team1983.utility.motion;

import frc.team1983.utility.math.Segment;

public class MotionProfile
{
    protected Segment[] segments;

    public MotionProfile(Segment[] segments)
    {
        this.segments = segments;
    }

    public MotionProfile()
    {
        this(new Segment[0]);
    }
}
