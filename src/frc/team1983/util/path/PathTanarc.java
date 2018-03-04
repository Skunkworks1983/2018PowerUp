package frc.team1983.util.path;

public class PathTanarc extends PathComponent
{
    private double radius, angle;
    private boolean isRight;

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
