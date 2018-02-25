package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

public class DriveArc extends Command
{
    private Drivebase drivebase;
    private double radius;
    private double angle;
    private double time;

    private MotionProfile leftProfile;
    private MotionProfile rightProfile;

    // feet, degrees, seconds
    public DriveArc(Drivebase drivebase, double radius, double angle, double time)
    {
        requires(drivebase);

        this.drivebase = drivebase;

        this.radius = radius;
        this.angle = angle;
        this.time = time;

        // todo investigate +/- left/right
        double width = Constants.MotorMap.Drivebase.WHEELBASE_WIDTH;

        double leftCircumference = (2 * (radius + (width / 2))) * Math.PI;
        double rightCircumference = (2 * (radius - (width / 2))) * Math.PI;

        double leftDistance = (angle / 360) * leftCircumference;
        double rightDistance = (angle / 360) * rightCircumference;

        // will become three-segment based on paths (todo)
        leftProfile = new TrapezoidalProfile(drivebase.getTicks(leftDistance), time);
        rightProfile = new TrapezoidalProfile(drivebase.getTicks(rightDistance), time);
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
