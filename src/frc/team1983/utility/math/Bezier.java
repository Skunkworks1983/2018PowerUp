package frc.team1983.utility.math;

import frc.team1983.Constants;

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
        if(points.length == 2)
            return Vector2.sub(points[1], points[0]).getNormalized();
        else
            return Vector2.sub(new Bezier(Arrays.copyOfRange(points, 1, points.length)).evaluate(t),
                               new Bezier(Arrays.copyOfRange(points, 0, points.length - 1)).evaluate(t)).getNormalized();
    }

    public Vector2 evaluateNormal(double t)
    {
        Vector2 tangent = evaluateTangent(t);
        return new Vector2(-tangent.getY(), tangent.getX());
    }

    public Vector2 offset(double t, double offset)
    {
        return Vector2.add(evaluate(t), Vector2.scale(evaluateNormal(t), offset));
    }

    public Vector2 evaluateCenterOfCurvature(double t)
    {
        return Ray.cast(new Ray(evaluate(t - Constants.EPSILON), evaluateNormal(t - Constants.EPSILON)),
                        new Ray(evaluate(t + Constants.EPSILON), evaluateNormal(t + Constants.EPSILON)));
    }

    public double evaluateRadiusOfCurvatuve(double t)
    {
        return Vector2.getDistance(evaluate(t), evaluateCenterOfCurvature(t));
    }

    public double getLength()
    {
        double length = 0;
        double segments = 20;
        for(int i = 0; i < segments; i++)
            length += evaluate((double) i / segments).getDistanceTo(evaluate((double) (i + 1) / segments));
        return length;
    }
}
