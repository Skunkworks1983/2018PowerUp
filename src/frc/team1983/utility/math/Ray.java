package frc.team1983.utility.math;

import frc.team1983.Constants;

public class Ray
{
    private Vector2 origin;
    private Vector2 direction;

    public Ray(Vector2 origin, Vector2 direction)
    {
        this.origin = origin;
        this.direction = direction.getNormalized();
    }

    public Ray(Vector2 origin, double degrees)
    {
        this(origin, new Vector2(Math.cos(degrees * Math.PI / 180), Math.sin(degrees * Math.PI / 180)));
    }

    public Vector2 getOrigin()
    {
        return origin;
    }

    public Vector2 getDirection()
    {
        return direction;
    }

    public static Vector2 cast(Ray left, Ray right)
    {
        if(Math.abs(Vector2.dot(left.direction, right.direction) - 1) < Constants.EPSILON)
            return null;

        double t1 = (right.origin.getY() - left.origin.getY()) * right.direction.getX() - (right.origin.getX() - left.origin.getX()) * right.direction.getY();
        t1 /= right.direction.getX() * left.direction.getY() - right.direction.getY() * left.direction.getX();

        double t2 = (left.origin.getY() - right.origin.getY()) * left.direction.getX() - (left.origin.getX() - right.origin.getX()) * left.direction.getY();
        t2 /= left.direction.getX() * right.direction.getY() - left.direction.getY() * right.direction.getX();

        if(t1 < 0 || t2 < 0)
            return null;

        return Vector2.add(left.origin, Vector2.scale(left.direction, t1));
    }

    public Vector2 cast(Ray other)
    {
        return cast(this, other);
    }
}
