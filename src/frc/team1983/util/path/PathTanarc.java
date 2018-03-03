package frc.team1983.util.path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.drivebase.DriveArc;

public class PathTanarc extends PathComponent
{
    private double radius, angle;
    private boolean isRight;

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(double radius)
    {
        this.radius = radius;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }

    public boolean isRight()
    {
        return isRight;
    }

    public void setRight(boolean right)
    {
        isRight = right;
    }

    @Override
    public Command getCommand()
    {
        CommandGroup group = new CommandGroup();
        CommandBase action = super.getAction();
        group.addSequential(new DriveArc(Robot.getInstance().getDrivebase(), radius, angle, time, isRight));
        group.addSequential(new WaitCommand(delay));

        return group;
    }
}
