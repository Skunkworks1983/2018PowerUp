package frc.team1983.commands.drivebase;

import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.utility.path.Path;

public class DrivePath extends CommandBase
{
    private Drivebase drivebase;
    private Path path;

    public DrivePath(Drivebase drivebase, Path path)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.path = path;
    }

    public DrivePath(Path path)
    {
        this(Robot.getInstance().getDrivebase(), path);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {

    }
}
