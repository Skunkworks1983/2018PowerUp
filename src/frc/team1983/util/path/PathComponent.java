package frc.team1983.util.path;

import edu.wpi.first.wpilibj.command.Command;

abstract public class PathComponent
{
    protected double time, delay;
    protected String type, action;

    public PathComponent()
    {
    }

    public double getTime()
    {
        return time;
    }

    public void setTime(double time)
    {
        this.time = time;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public double getDelay()
    {
        return delay;
    }

    public void setDelay(double delay)
    {
        this.delay = delay;
    }

    abstract public Command getCommand();
}
