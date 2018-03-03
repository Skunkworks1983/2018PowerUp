package frc.team1983.util.motion;

/*
velocities are in units per second
*/
public class MotionSegment
{
    public MotionPoint start, end;

    public MotionSegment(MotionPoint start, MotionPoint end)
    {
        if(start.getTime() != end.getTime())
        {
            this.start = start;
            this.end = end;
        }
        else
        {
            throw new IllegalArgumentException("MotionSegment slope is undefined");
        }
    }

    public MotionSegment(double startTime, double startVelocity, double endTime, double endVelocity)
    {
        this(new MotionPoint(startTime, startVelocity), new MotionPoint(endTime, endVelocity));
    }

    public double evaluateVelocity(double time)
    {
        if(start.getTime() <= time && time <= end.getTime())
        {
            return start.getVelocity() + ((time - start.getTime()) * evaluateAcceleration());
        }
        else
        {
            throw new IllegalArgumentException("time " + time + " is not in domain of MotionSegment [" + start.getTime() + ", " + end.getTime() + "]");
        }
    }

    public double evaluateAcceleration()
    {
        return (end.getVelocity() - start.getVelocity()) / (end.getTime() - start.getTime());
    }

    public MotionPoint getStart()
    {
        return start;
    }

    public MotionPoint getEnd()
    {
        return end;
    }
}
