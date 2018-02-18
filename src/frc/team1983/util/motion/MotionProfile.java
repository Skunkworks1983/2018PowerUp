package frc.team1983.util.motion;

import java.util.List;

public class MotionProfile
{
    protected List<MotionSegment> segments;

    protected double distance;
    protected double t_total;

    protected double v_max;
    protected double a_max;

    protected int pointDuration = 100; // ms

    public MotionProfile(List<MotionSegment> segments)
    {
        this.segments = segments;
    }

    public double evaluateVelocity(double time)
    {
        // check if time is in domain of profile
        if(0 <= time && time <= t_total)
        {
            for(MotionSegment segment : segments)
            {
                if(segment.getStart().getTime() <= time && time <= segment.getEnd().getTime())
                {
                    return segment.evaluate(time);
                }
            }

            return 0;
        }
        else
        {
            throw new IllegalArgumentException("time " + time + " is not in domain of profile");
        }
    }

    public double evaluatePosition(double time)
    {
        // check if time is in domain of profile
        if(0 <= time && time <= t_total)
        {
            double A = 0;

            for(MotionSegment segment : segments)
            {
                double a = segment.getStart().getVelocity();
                double b = 0, dt = 0;

                if(segment.getEnd().getTime() < time)
                {
                    b = segment.getEnd().getVelocity();
                    dt = (segment.getEnd().getTime() - segment.getStart().getTime());
                }
                else if(segment.getStart().getTime() <= time)
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
            throw new IllegalArgumentException("time " + time + " is not in domain of profile");
        }
    }

    public double getDistance()
    {
        return distance;
    }

    public double getTotalTime()
    {
        return t_total;
    }

    public double getMaxVelocity()
    {
        return v_max;
    }

    public double getMaxAcceleration()
    {
        return a_max;
    }

    public int getPointDuration()
    {
        return pointDuration;
    }
}