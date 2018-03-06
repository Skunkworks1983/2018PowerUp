package frc.team1983.util.path;

import com.fasterxml.jackson.core.type.TypeReference;
import frc.team1983.commands.drivebase.DriveProfile;

import java.util.ArrayList;
import java.util.List;

public class Path
{
    private ArrayList<DriveProfile> points;

    public Path(ArrayList<DriveProfile> points)
    {
        this.points = points;
    }

    public void setPoints(ArrayList<DriveProfile> points)
    {
        this.points = points;
    }

    public ArrayList<DriveProfile> getPoints()
    {
        return points;
    }

    public DriveProfile getPoint(int index)
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

    public int getComponentCount()
    {
        return points.size();
    }

    public DriveProfile getComponent(int i)
    {
        return points.get(i);
    }

    public List<DriveProfile> getPath()
    {
        return points;
    }
}
