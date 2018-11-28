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
        scale(1.0 / getMagnitude());
    }

    public static Vector2 add(Vector2 left, Vector2 right)
    {
        return new Vector2(left.x + right.x, left.y + right.y);
    }

    public Vector2 add(Vector2 right)
    {
        return Vector2.add(this, right);
    }

    public static Vector2 sub(Vector2 left, Vector2 right)
    {
        return new Vector2(left.x - right.x, left.y - right.y);
    }

    public Vector2 sub(Vector2 right)
    {
        return Vector2.sub(this, right);
    }

    public static Vector2 scale(Vector2 left, double right)
    {
        return new Vector2(left.x * right, left.y * right);
    }

    public Vector2 scale(double right)
    {
        return Vector2.scale(this, right);
    }
}
