package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import frc.team1983.util.path.Path;

public class FollowPath extends Command
{
    private Drivebase drivebase;
    private Path path;

    public FollowPath(Drivebase drivebase, Path path)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.path = path;
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
