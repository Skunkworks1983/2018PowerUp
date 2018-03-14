package frc.team1983.util.path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.services.logger.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class Path extends CommandGroup
{
    private Logger logger;
    private ArrayList<DriveProfile> points;

    public Path(ArrayList<DriveProfile> points)
    {
        this.logger = LoggerFactory.createNewLogger(this.getClass());
        this.points = points;

        DriveProfile.stitch(points);

        for(DriveProfile point : points)
        {
            CommandGroup movement = new CommandGroup();
            movement.addParallel(point);

            for(Command action : point.getActions())
            {
                movement.addParallel(action);
            }

            addSequential(movement);
        }
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
            counter += point.getActions().size() + 1;
        }
        return counter;
    }

    /*public Command getComponent(int i)
    {
        return points.get(i);
    }*/

    public void flipSide()
    {
        for(DriveProfile drive : points)
        {
            if(drive instanceof DriveArc)
            {
                
            }
        }
    }

    public CommandGroup getCommands()
    {
        CommandGroup group = new CommandGroup();

        for(DriveProfile point : points)
        {
            CommandGroup movement = new CommandGroup();

            movement.addParallel(point);
            for(Command action : point.getActions())
            {
                movement.addParallel(action);
            }

            group.addSequential(movement);
        }

        DriveProfile.stitch(points);

        return group;
    }
}
