package frc.team1983.utility.math;

public class Ray
{
    private Vector2 origin;
    private Vector2 direction;

    public Ray(Vector2 origin, Vector2 direction)
    {
        this.origin = origin;
        this.direction = direction;
    }

    public Ray(Vector2 origin, double degrees)
    {
        this(origin, new Vector2(Math.cos(degrees * Math.PI / 180), Math.sin(degrees * Math.PI / 180)));
    }
}
