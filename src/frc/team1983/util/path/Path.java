package frc.team1983.util.path;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.drivebase.DriveProfile;

import java.util.ArrayList;
import java.util.List;

public class Path
{
    private ArrayList<ArrayList<Command>> points;

    public Path(ArrayList<ArrayList<Command>> points)
    {
        this.points = points;
    }

    public void setPoints(ArrayList<ArrayList<Command>> points)
    {
        this.points = points;
    }

    public ArrayList<ArrayList<Command>> getPoints()
    {
        return points;
    }

    public Command getPoint(int i, int j)
    {
        if(i < points.size())
        {
            if(j < points.get(i).size())
            {
                return points.get(i).get(j);
            }
            else
            {
                throw new IllegalArgumentException("index " + j + " is out of bounds");
            }
        }
        else
        {
            throw new IllegalArgumentException("index " + i + " is out of bounds");
        }
    }

    public int getComponentCount()
    {
        int count = 0;
        for(int i = 0; i < points.size(); i++)
        {
            for(int j = 0; j < points.get(i).size(); j++)
            {
                count++;
            }
        }
        return count;
    }

    /*public Command getComponent(int i)
    {
        return points.get(i);
    }*/

    public CommandGroup getCommands()
    {
        CommandGroup group = new CommandGroup();

        for(ArrayList<Command> command : points)
        {
            group.addSequential(command.get(0));
            for(int i = 1; i < command.size(); i++)
            {
                group.addParallel(command.get(i));
            }
        }

        return group;
    }

    /*public List<Command> getPath()
    {
        return points;
    }*/
}
