package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.apache.logging.log4j.core.Logger;

public class DriveFeet extends Command
{
    private Drivebase drivebase;
    private double feet;
    private double time;

    private MotionProfile leftProfile;
    private MotionProfile rightProfile;

    private Logger logger;

    public DriveFeet(Drivebase drivebase, double feet, double time)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        this.drivebase = drivebase;
        this.feet = feet;
        this.time = time;

        // will become three-segment based on paths (todo)
        leftProfile = new TrapezoidalProfile(drivebase.getTicks(feet), time);
        rightProfile = new TrapezoidalProfile(drivebase.getTicks(feet), time);
    }

    @Override
    protected void initialize()
    {
        drivebase.setRightProfile(rightProfile);
        drivebase.setLeftProfile(leftProfile);

        drivebase.runProfiles();
    }

    @Override
    protected void execute()
    {

    }

    @Override
    protected boolean isFinished()
    {
        return drivebase.profilesAreFinished();
    }

    @Override
    protected void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {
        drivebase.stopProfiles();
    }
}
