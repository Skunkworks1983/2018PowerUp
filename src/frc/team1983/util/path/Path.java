package frc.team1983.util.path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.drivebase.DriveProfile;

import java.util.ArrayList;

public class Path
{
    private ArrayList<DriveProfile> points;

    public Path(ArrayList<DriveProfile> points)
    {
        this.points = points;
    }

    public ArrayList<DriveProfile> getPoints()
    {
        return points;
    }

    public Command getPoint(int i)
    {
        if(i < points.size())
        {
            return points.get(i);
        }
        else
        {
            throw new IllegalArgumentException("index " + i + " is out of bounds");
        }
    }

    public int getComponentCount()
    {
        int counter = 0;
        for(DriveProfile point : points)
        {
            counter++;
            if(point.getAction() != null)
            {
                counter++;
            }
        }
        return counter;
    }

    /*public Command getComponent(int i)
    {
        return points.get(i);
    }*/

    public CommandGroup getCommands()
    {
        CommandGroup group = new CommandGroup();

        for(DriveProfile point : points)
        {
            group.addSequential(point);
            if(point.getAction() != null)
            {
                group.addParallel(point.getAction());
            }
        }

        return group;
    }
}
