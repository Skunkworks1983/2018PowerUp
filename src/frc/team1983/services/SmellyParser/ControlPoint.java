package frc.team1983.services.SmellyParser;

//Represents a control points in the SmellyPath json file
public class ControlPoint
{
    //These values MUST match the *exact* key generated in the smelly app
    private int i;
    private String type;
    private double startx, starty, angle, duration;

    public int getI()
    {
        return i;
    }

    public void setI(int i)
    {
        this.i = i;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public double getStartx()
    {
        return startx;
    }

    public void setStartx(double startx)
    {
        this.startx = startx;
    }

    public double getStarty()
    {
        return starty;
    }

    public void setStarty(double starty)
    {
        this.starty = starty;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public double getDuration()
    {
        return duration;
    }

    public void setDuration(double duration)
    {
        this.duration = duration;
    }
}
