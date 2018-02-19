package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motion.MotionProfileStatus;
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
        leftProfile = new TrapezoidalProfile(drivebase.feetToEncoderTicks(feet), time);
        rightProfile = new TrapezoidalProfile(drivebase.feetToEncoderTicks(feet), time);
    }

    @Override
    protected void initialize()
    {
        drivebase.setLeftProfile(leftProfile);
        drivebase.setRightProfile(rightProfile);

        drivebase.runProfiles();
    }

    @Override
    protected void execute()
    {
        logger.info("left count: " + drivebase.left1.manager.getProfileStatus().btmBufferCnt
                            + " right: " + drivebase.right1.manager.getProfileStatus().btmBufferCnt);
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
        logger.info("finished with " + drivebase.left1.manager.getProfileStatus().btmBufferCnt + " in left"
                   + " and " + drivebase.right1.manager.getProfileStatus().btmBufferCnt + " in right");

        drivebase.stopProfiles();
    }
}
