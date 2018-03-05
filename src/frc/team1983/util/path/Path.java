package frc.team1983.util.path;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class Path
{
    private ArrayList<PathComponent> points;

    public Path(ArrayList<PathComponent> points)
    {
        this.points = points;
    }

    public void setPoints(ArrayList<PathComponent> points)
    {
        this.points = points;
    }

    public ArrayList<PathComponent> getPoints()
    {
        return points;
    }

    public PathComponent getPoint(int index)
    {
        if(index < points.size())
        {
            return points.get(index);
        }
        else
        {
            throw new IllegalArgumentException("index " + index + " is out of bounds");
        }
    }

    public List<PathComponent> getPath()
    {
        return points;
    }
}
