package frc.team1983.util.motion;

import frc.team1983.util.math.Vector2;

public class MotionPoint extends Vector2
{
    public MotionPoint(double time, double velocity)
    {
        super(time, velocity);
    }

    public double getTime()
    {
        return x;
    }

    public double getVelocity()
    {
        return y;
    }

    public void setVelocity(double velocity)
    {
        this.y = velocity;
    }
}
