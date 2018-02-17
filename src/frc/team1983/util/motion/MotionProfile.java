package frc.team1983.util.motion;

import java.util.List;

public abstract class MotionProfile
{
    private List<MotionSegment> segments;

    protected double distance;
    protected double t_total;

    protected double vel_max;
    protected double acc_max;

    protected int pointDuration = 100; // ms

    public MotionProfile(List<MotionSegment> segments)
    {
        this.segments = segments;
    }

    public List<MotionSegment> getSegments()
    {
         return segments;
    }

    public double evaluatePosition(double time)
    {
        if(0 <= time && time <= t_total)
        {
            double A = 0;

            for(MotionSegment segment : segments)
            {
                double a = segment.getStart().getVelocity(), b = 0, dt = 0;

                if(segment.getEnd().getTime() <= time)
                {
                    b = segment.getEnd().getVelocity();
                    dt = segment.getEnd().getTime() - segment.getStart().getTime();
                }
                else if(segment.getStart().getTime() < time && time < segment.getEnd().getTime())
                {
                    b = segment.evaluate(time);
                    dt = time - segment.getStart().getTime();
                }

                A += ((a + b) / 2) * dt;
            }

            return A;
        }
        else
        {
            throw new IllegalArgumentException("Time " + time + " is not in the domain of motion profile");
        }
    }

    public double evaluateVelocity(double time)
    {
        if(0 <= time && time <= t_total)
        {
            // can probably do some fancy math here to find which segment we need to access but i'll keep it simple
            for(MotionSegment segment : segments)
            {
                // segments overlap at one point so we can use bounds of domain
                //if(segment.getStart().getTime() <= time && time <= segment.getEnd().getTime())
                    return segment.evaluate(time);
            }

            // guaranteed that code above returns a point but java sucks so we need this
            return 0;
        }
        else
        {
            throw new IllegalArgumentException("Time " + time + " is not in the domain of motion profile");
        }
    }

    public int getPointDuration()
    {
        return pointDuration;
    }

    public double getT_total()
    {
        return t_total;
    }
}