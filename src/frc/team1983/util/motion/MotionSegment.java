package frc.team1983.util.motion;

public class MotionSegment
{
    private MotionProfilePoint start, end;
    private double acceleration;

    public MotionSegment(MotionProfilePoint start, MotionProfilePoint end) throws RuntimeException
    {
        // check if segment is a function
        if(start.getTime() == end.getTime())
            throw new RuntimeException("MotionSegment not a function");

        this.start = start;
        this.end = end;

        this.acceleration = calculateAcceleration();
    }

    public double evaluate(double time) throws RuntimeException
    {
        if(start.getTime() <= time && time <= end.getTime())
        {
            return start.getVelocity() + (time - start.getTime()) * calculateAcceleration();
        }
        else
        {
            throw new RuntimeException("Time " + time + " not in domain of segment");
        }
    }

    public double getAcceleration()
    {
        return calculateAcceleration();
    }

    protected double calculateAcceleration()
    {
        return (end.getVelocity() - start.getVelocity()) / (end.getTime() - start.getTime());
    }

    public MotionProfilePoint getStart()
    {
        return start;
    }

    public MotionProfilePoint getEnd()
    {
        return end;
    }
}
