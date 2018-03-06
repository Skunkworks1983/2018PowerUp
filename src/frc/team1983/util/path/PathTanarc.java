package frc.team1983.util.path;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.drivebase.DriveArc;

@JsonDeserialize(as = PathTanarc.class)
public class PathTanarc extends PathComponent
{
    private double radius, angle;
    private boolean isRight;

    public PathTanarc(double radius, double angle, double time)
    {
        this.radius = radius;
        this.angle = angle;
        this.time = time;
    }

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

    public void setIsRight(boolean right)
    {
        isRight = right;
    }
}
