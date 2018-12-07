package frc.team1983.utility.math;

public class Segment extends Bezier
{
    private Vector2 p0, p1;

    public Segment(Vector2 p0, Vector2 p1)
    {
        super(p0, p1);

        this.p0 = p0;
        this.p1 = p1;
    }

    public Vector2 getP0()
    {
        return p0;
    }

    public Vector2 getP1()
    {
        return p1;
    }

    public static Vector2 intersection(Segment left, Segment right)
    {
        Ray r1 = new Ray(left.p0, Vector2.sub(left.p1, left.p0).getNormalized());
        Ray r2 = new Ray(right.p0, Vector2.sub(right.p1, right.p0).getNormalized());
        Vector2 intersection = Ray.cast(r1, r2);

        if(intersection == null)
            return null;

        double t1 = r1.getDirection().getX() == 0 ? (intersection.getY() - left.p0.getY()) / r1.getDirection().getY()
                                                  : (intersection.getX() - left.p0.getX()) / r1.getDirection().getX();
        double t2 = r2.getDirection().getX() == 0 ? (intersection.getY() - right.p0.getY()) / r2.getDirection().getY()
                                                  : (intersection.getX() - right.p0.getX()) / r2.getDirection().getX();

        if(t1 > left.getLength() || t2 > right.getLength())
            return null;

        return intersection;
    }

    public Vector2 intersection(Segment other)
    {
        return Segment.intersection(this, other);
    }
}
