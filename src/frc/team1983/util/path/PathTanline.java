package frc.team1983.util.path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.DriveFeet;

public class PathTanline extends PathComponent
{
    private double distance;

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    @Override
    public Command getCommand()
    {
        CommandGroup group = new CommandGroup();
        group.addSequential(new DriveFeet(Robot.getInstance().getDrivebase(), distance, time));
        group.addSequential(new WaitCommand(delay));

        return group;
    }
}
