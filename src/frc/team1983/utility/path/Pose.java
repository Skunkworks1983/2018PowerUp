package frc.team1983.utility.path;

import frc.team1983.utility.math.Vector2;

public class Pose
{
    private Vector2 origin;
    private double degrees;

    public Pose(Vector2 origin, double degrees)
    {
        this.origin = origin;
        this.degrees = degrees;
    }
}
