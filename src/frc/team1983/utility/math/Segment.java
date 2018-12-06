package frc.team1983.utility.math;

public class Segment
{
    private Vector2 p0, p1;

    public Segment(Vector2 p0, Vector2 p1)
    {
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
}
