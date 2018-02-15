package frc.team1983.util.path;

import frc.team1983.util.math.Vector2;

public class PathArc extends PathComponent
{
    private Vector2 center;
    private double radius, angle;

    public PathArc(Vector2 center, double radius, double angle)
    {
        this.center = center;
        this.radius = radius; // we don't actually need a radius for pure pursuit but we're not that cool yet
        this.angle = angle;
    }
}
