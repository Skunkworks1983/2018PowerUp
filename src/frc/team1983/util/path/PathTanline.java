package frc.team1983.util.path;

public class PathTanline extends PathComponent
{
    private double distance;

    public PathTanline(double distance, double time)
    {
        this.distance = distance;
        this.time = time;
    }

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }
}
