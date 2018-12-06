package frc.team1983.utility.math;

public class Vector2
{
    private double x, y;

    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "<" + x + ", " + y + ">";
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void set(double x, double y)
    {
        setX(x);
        setY(y);
    }

    public void set(Vector2 v)
    {
        set(v.x, v.y);
    }

    public double getMagnitude()
    {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 getNormalized()
    {
        return Vector2.scale(this, 1.0 / getMagnitude());
    }

    public void normalize()
    {
        set(getNormalized());
    }

    public static double getDistance(Vector2 left, Vector2 right)
    {
        return Math.sqrt(Math.pow(left.x - right.x, 2) + Math.pow(left.y - right.y, 2));
    }

    public double getDistanceTo(Vector2 other)
    {
        return Vector2.getDistance(this, other);
    }

    public static Vector2 add(Vector2 left, Vector2 right)
    {
        return new Vector2(left.x + right.x, left.y + right.y);
    }

    public void add(Vector2 right)
    {
        set(Vector2.add(this, right));
    }

    public static Vector2 sub(Vector2 left, Vector2 right)
    {
        return new Vector2(left.x - right.x, left.y - right.y);
    }

    public void sub(Vector2 right)
    {
        set(Vector2.sub(this, right));
    }

    public static Vector2 scale(Vector2 left, double right)
    {
        return new Vector2(left.x * right, left.y * right);
    }

    public void scale(double scalar)
    {
        set(Vector2.scale(this, scalar));
    }

    public static Vector2 twist(Vector2 point, Vector2 center, double degrees)
    {
        degrees *= Math.PI / 180;
        double r = Vector2.getDistance(point, center);
        double theta = Math.atan2(point.y - center.y, point.x - center.x);
        return Vector2.add(center, Vector2.scale(new Vector2(Math.cos(theta + degrees), Math.sin(theta + degrees)), r));
    }

    public void twist(Vector2 center, double degrees)
    {
        set(Vector2.twist(this, center, degrees));
    }
}
