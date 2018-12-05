package frc.team1983.utility.math;

import java.util.Arrays;

public class Bezier
{
    private Vector2[] points;

    public Bezier(Vector2... points)
    {
        if(points.length < 2)
            throw new IllegalArgumentException("cannot construct Bezier with less than two points");

        this.points = points;
    }

    public Vector2 evaluate(double t)
    {
        if(points.length == 2)
            return Vector2.add(points[0], Vector2.scale(Vector2.sub(points[1], points[0]), t));
        else
            return new Bezier(
                    new Bezier(Arrays.copyOfRange(points, 0, points.length - 1)).evaluate(t),
                    new Bezier(Arrays.copyOfRange(points, 1, points.length)).evaluate(t)
            ).evaluate(t);
    }

    public Vector2 evaluateTangent(double t)
    {
        return Vector2.sub(new Bezier(Arrays.copyOfRange(points, 1, points.length)).evaluate(t),
                           new Bezier(Arrays.copyOfRange(points, 0, points.length - 1)).evaluate(t)).getNormalized();
    }
}
