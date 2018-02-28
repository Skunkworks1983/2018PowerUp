package frc.team1983.util.motion;

import java.util.List;

public class MotionProfile
{
    protected double Ks = 0, Kv = 0, Ka = 0;

    protected List<MotionSegment> segments;

    protected double distance;
    protected double duration;

    protected double vel_max;
    protected double acc_max;

    protected int pointDuration = 100; // ms

    public MotionProfile(List<MotionSegment> segments)
    {
        this.segments = segments;
    }

    public double evaluateOutput(double time)
    {
        return (evaluateVelocity(time) * Kv) + (evaluateAccelertion(time) * Ka) + Ks;
    }

    public double evaluatePosition(double time)
    {
        // check if time is in domain of profile
        if(0 <= time && time <= duration)
        {
            double area = 0;

            // trapezoidal sum of all segments to find area
            for(MotionSegment segment : segments)
            {
                double vel_1 = segment.getStart().getVelocity();
                double vel_2 = 0, dt = 0;

                // check if we are trying to evaluate a portion of a segment or a whole segment
                if(segment.getEnd().getTime() < time)
                {
                    // evaluate the whole segment
                    vel_2 = segment.getEnd().getVelocity();
                    dt = (segment.getEnd().getTime() - segment.getStart().getTime());
                }
                else if(segment.getStart().getTime() <= time)
                {
                    // evaluate a portion of the segment, [start, time]
                    vel_2 = segment.evaluate(time);
                    dt = time - segment.getStart().getTime();
                }

                // area of a trapezoid
                area += ((vel_1 + vel_2) / 2) * dt;
            }

            return area;
        }
        else
        {
            throw new IllegalArgumentException("time " + time + " is not in domain of profile");
        }
    }

    public double evaluateVelocity(double time)
    {
        return getSegment(time).evaluate(time);
    }

    public double evaluateAccelertion(double time)
    {
        return getSegment(time).getAcceleration();
    }

    protected MotionSegment getSegment(double time)
    {
        if(0 <= time && time <= segments.size())
        {
            for(MotionSegment segment : segments)
            {
                if(segment.getStart().getTime() <= time && time <= segment.getEnd().getTime())
                {
                    return segment;
                }
            }

            return null;
        }
        else
        {
            throw new IllegalArgumentException("segment " + time + " is not in domain of profile");
        }
    }

    public void configSVA(double Ks, double Kv, double Ka)
    {
        this.Ks = Ks;
        this.Kv = Kv;
        this.Ka = Ka;
    }

    public double getDistance()
    {
        return distance;
    }

    public double getTotalTime()
    {
        return duration;
    }

    public double getMaxVelocity()
    {
        return vel_max;
    }

    public double getMaxAcceleration()
    {
        return acc_max;
    }

    public int getPointDuration()
    {
        return pointDuration;
    }
}