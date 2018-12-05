package frc.team1983.utility.motion;

import frc.team1983.utility.math.Segment;

public class MotionProfile
{
    protected Segment[] segments;

    public MotionProfile(Segment[] velocitySegments)
    {
        this.segments = velocitySegments;
    }

    public MotionProfile()
    {
        this(new Segment[0]);
    }

    public double evaluateDisplacement(double t)
    {
        if(t < 0)
            throw new IllegalArgumentException("time is out of bounds [0, infinity)");

        double area = 0;

        for(Segment segment : segments)
        {
            double vel_1 = segment.getP0().getY();
            double vel_2 = 0, dt = 0;

            if(segment.getP1().getX() < t)
            {
                vel_2 = segment.getP1().getY();
                dt = (segment.getP1().getX() - segment.getP0().getX());
            }
            else if(segment.getP0().getX() <= t)
            {
                double acceleration = (segment.getP1().getY() - segment.getP0().getY()) / (segment.getP1().getX() - segment.getP0().getX());
                vel_2 = segment.getP0().getY() + + ((t - segment.getP0().getX()) * acceleration);
                dt = t - segment.getP0().getX();
            }

            area += ((vel_1 + vel_2) / 2) * dt;
        }

        return area;
    }

    public double evaluateVelocity(double t)
    {
        if(t < 0)
            throw new IllegalArgumentException("time is out of bounds [0, infinity)");

        for(Segment segment : segments)
            if(segment.getP0().getX() <= t && t <= segment.getP1().getX())
                return segment.getP0().getY() + (t - segment.getP0().getX()) * evaluateAcceleration(t);

        return 0;
    }

    public double evaluateAcceleration(double t)
    {
        if(t < 0)
            throw new IllegalArgumentException("time is out of bounds [0, infinity)");

        for(Segment segment : segments)
            if(segment.getP0().getX() <= t && t <= segment.getP1().getX())
                return (segment.getP1().getY() - segment.getP0().getY()) / (segment.getP1().getX() - segment.getP0().getX());

        return 0;
    }
}
