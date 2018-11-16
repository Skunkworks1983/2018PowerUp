package frc.team1983.utility.math;

public class Vector3
{
    protected double x = 0, y = 0, z = 0;

    public Vector3()
    {

    }

    public Vector3(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setZ(double z)
    {
        this.z = z;
    }


    public double getMagnitude()
    {
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), 1 / 2);
    }


    public static Vector3 getNormalized(Vector3 vec)
    {
        return Vector3.scale(vec, 1 / vec.getMagnitude());
    }

    public void normalize()
    {
        scale(this, 1 / getMagnitude());
    }


    public static Vector3 unm(Vector3 vec)
    {
        return new Vector3(-vec.x, -vec.y, -vec.z);
    }


    public static Vector3 add(Vector3 left, Vector3 right)
    {
        return new Vector3(left.x + right.x, left.y + right.y, left.z + right.z);
    }

    public Vector3 add(Vector3 right)
    {
        return Vector3.add(this, right);
    }


    public static Vector3 sub(Vector3 left, Vector3 right)
    {
        return Vector2.add(left, Vector3.unm(right));
    }

    public Vector3 sub(Vector2 right)
    {
        return Vector3.sub(this, right);
    }


    public static Vector3 scale(Vector3 vec, double scalar)
    {
        return new Vector3(vec.x * scalar, vec.y * scalar, vec.z * scalar);
    }

    public Vector3 scale(double scalar)
    {
        return Vector3.scale(this, scalar);
    }


    public static double dot(Vector3 left, Vector3 right)
    {
        return (left.x * right.x) + (left.y * right.y) + (left.z * right.z);
    }

    public double dot(Vector3 right)
    {
        return Vector3.dot(this, right);
    }
}
