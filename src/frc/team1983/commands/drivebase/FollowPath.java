package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;
import frc.team1983.util.path.PathArc;
import frc.team1983.util.path.PathComponent;
import frc.team1983.util.path.PathSegment;

import java.util.ArrayList;
import java.util.List;

public class FollowPath extends CommandGroup
{
    private Drivebase drivebase;
    private Path path;

    private List<DriveProfile> commands;

    public FollowPath(Drivebase drivebase, Path path)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.path = path;

        commands = new ArrayList<>();

        for(int i = 0; i < path.getComponentCount(); i++)
        {
            PathComponent component = path.getComponent(i);
        }

        for(DriveProfile command : commands)
        {
            addSequential(command);
        }
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {

    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {

    }
}
