package frc.team1983.util.motion;

import frc.team1983.util.math.Vector2;

public class MotionProfilePoint extends Vector2
{
    public MotionProfilePoint(double time, double velocity)
    {
        super(time, velocity);
    }


    public double getTime()
    {
        return super.getX();
    }

    public void setTime(double time)
    {
        super.setX(time);
    }

    public double getVelocity()
    {
        return super.getY();
    }

    public void setVelocity(double velocity)
    {
        super.setY(velocity);
    }
}
