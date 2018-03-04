package frc.team1983.commands.drivebase;

import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.profiles.CruiseProfile;

import java.util.ArrayList;
import java.util.List;

public class DriveProfile extends CommandBase
{
    private Drivebase drivebase;
    protected CruiseProfile leftProfile, rightProfile;

    protected boolean finished = false;

    private double inRangeTime = 0;
    private double lastMilli = 0;

    public DriveProfile(Drivebase drivebase, CruiseProfile leftProfile, CruiseProfile rightProfile)
    {
        requires(drivebase);

        this.drivebase = drivebase;
        this.leftProfile = leftProfile;
        this.rightProfile = rightProfile;
    }

    @Override
    public void initialize()
    {
        drivebase.setLeftProfile(leftProfile);
        drivebase.setRightProfile(rightProfile);

        drivebase.runProfiles();
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished()
    {
        if(isOnTarget())
        {
            inRangeTime += (System.currentTimeMillis() - lastMilli) * 0.001;
        }
        else
        {
            inRangeTime = 0;
        }

        lastMilli = System.currentTimeMillis();

        return finished || drivebase.profilesAreFinished() && isOnTarget() && inRangeTime >= Constants.Motion.DRIVEBASE_IN_RANGE_END_TIME;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {
        finished = true;

        drivebase.stopProfiles();

        System.out.println("FINISHED left error: " + Drivebase.getFeet(drivebase.getLeftError()) +
                                 " , right error: " + Drivebase.getFeet(drivebase.getRightError()));
    }

    public boolean isOnTarget()
    {
        return Math.abs(drivebase.getLeftEncoderValue() - leftProfile.getDistance()) <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE &&
               Math.abs(drivebase.getRightEncoderValue() - rightProfile.getDistance()) <= Constants.Motion.DRIVEBASE_TICKS_END_RANGE;
    }

    // ohhhhh my god please
    public static void stitch(DriveProfile drive1, DriveProfile drive2)
    {
        CruiseProfile.stitch(drive1.leftProfile, drive2.leftProfile);
        CruiseProfile.stitch(drive1.rightProfile, drive2.rightProfile);
    }

    // this is terrible please put me out of my misery
    public static void stitch(List<DriveProfile> drives)
    {
        List<CruiseProfile> leftProfiles = new ArrayList<CruiseProfile>();
        List<CruiseProfile> rightProfiles = new ArrayList<CruiseProfile>();

        for(DriveProfile drive : drives)
        {
            leftProfiles.add(drive.leftProfile);
            rightProfiles.add(drive.rightProfile);
        }

        CruiseProfile.stitch(leftProfiles);
        CruiseProfile.stitch(rightProfiles);
    }
}
