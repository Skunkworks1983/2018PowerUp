package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.MotionProfile;

public class DriveFeet extends Command
{
    private Drivebase drivebase;
    private double feet;
    private double time;

    public DriveFeet(Drivebase drivebase, double feet, double time)
    {
        this.drivebase = drivebase;
        this.feet = feet;
        this.time = time;
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        drivebase.runProfiles();
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
