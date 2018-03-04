package frc.team1983.util.path;

public class PathTanarc extends PathComponent
{
    private double radius, angle;
    private boolean isRight;

    public PathTanarc(double radius, double angle, double time)
    {
        this.radius = radius;
        this.angle = angle;
        this.time = time;
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public boolean isRight()
    {
        return isRight;
    }

    public void setRight(boolean right)
    {
        isRight = right;
    }
}
