package frc.team1983.util.motion;

import java.util.List;

public class MotionProfile
{
    protected List<MotionSegment> segments;

    protected double duration;

    // constraints
    protected double maxVelocity;
    protected double maxAccel;

    public MotionProfile(List<MotionSegment> segments)
    {
        this.segments = segments;
    }

    public double evaluatePosition(double time)
    {
        // check if time is in domain of profile
        if(0 <= time && time <= getDuration())
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
                    vel_2 = segment.evaluateVelocity(time);
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
        return getSegment(time).evaluateVelocity(time);
    }

    public double evaluateAcceleration(double time)
    {
        return time >= duration ? 0 : getSegment(time).evaluateAcceleration();
    }

    protected MotionSegment getSegment(double time)
    {
        if(0 <= time && time <= getDuration())
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

    public boolean isContinuous()
    {
        boolean continuous = true;

        for(int i = 0; i < segments.size(); i++)
        {
            if(i > 1)
            {
                if(segments.get(i).getStart().getVelocity() != segments.get(i - 1).getEnd().getVelocity() ||
                        segments.get(i).getStart().getTime() != segments.get(i - 1).getEnd().getTime())
                    continuous = false;
            }
        }

        return continuous;
    }

    public double getDuration()
    {
        return duration;
    }

    public double getDistance()
    {
        return evaluatePosition(getDuration());
    }

    public double getInitialVelocity()
    {
        return evaluateVelocity(0);
    }

    protected void setInitialVelocity(double velocity)
    {
        getSegment(0).getStart().setVelocity(velocity);
    }

    public double getFinalVelocity()
    {
        return evaluateVelocity(duration);
    }

    protected void setFinalVelocity(double velocity)
    {
        getSegment(duration).getEnd().setVelocity(velocity);
    }

    public double getMaxVelocity()
    {
        return maxVelocity;
    }

    public double getMaxAcceleration()
    {
        return maxAccel;
    }
}