package frc.team1983.util.path;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.team1983.services.parser.SmellyDeserializer;

@JsonDeserialize(using = SmellyDeserializer.class)
public class PathComponent
{
    protected double time, delay = 0;
    protected String type, action;

    public PathComponent()
    {
    }

    public enum Action {
        NONE,
        INTAKE,
        EXPEL,
        SWITCH,
        SCALE,
        BOTTOM
    }

    public double getTime()
    {
        return time;
    }

    public void setTime(double time)
    {
        this.time = time;
    }

    public double getDelay()
    {
        return delay;
    }

    public void setDelay(double delay)
    {
        this.delay = delay;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
