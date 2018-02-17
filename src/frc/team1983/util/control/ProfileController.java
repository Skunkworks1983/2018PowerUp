package frc.team1983.util.control;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;

import java.util.concurrent.locks.ReentrantLock;

public class ProfileController
{
    protected Motor parent;

    protected MotionProfile profile;
    protected MotionProfileStatus status;

    private ReentrantLock controllerLock = new ReentrantLock();
    private ProfileControllerRunnable runnable;

    private boolean enabled = false;

    public ProfileController(Motor parent, Robot robot)
    {
        this.parent = parent;
        this.parent.changeMotionControlFramePeriod(5);
        this.parent.clearMotionProfileTrajectories();

        status = new MotionProfileStatus();

        robot.addProfileController(this);

        runnable = new ProfileControllerRunnable(this);

        controllerLock.lock();
        runnable.start();
    }

    public void setProfile(MotionProfile profile)
    {
        this.profile = profile;
        streamProfile(profile);
    }

    private void streamProfile(MotionProfile profile)
    {
        controllerLock.lock();

        int durationMs = profile.getPointDuration();
        double duration = durationMs * 0.001;
        int resolution = (int) (profile.getT_total() / duration);

        parent.clearMotionProfileTrajectories();
        parent.clearMotionProfileHasUnderrun(100);

        parent.configMotionProfileTrajectoryPeriod(0, 100);

        for(int i = 0; i <= resolution; i++)
        {
            double t = i * duration;

            TrajectoryPoint point = new TrajectoryPoint();

            point.position = profile.evaluatePosition(t);
            point.velocity = profile.evaluateVelocity(t);

            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;

            point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms.valueOf(profile.getPointDuration());

            point.zeroPos = i == 0;
            point.isLastPoint = i == (resolution - 1);

            parent.pushMotionProfileTrajectory(point);
        }

        controllerLock.unlock();
    }

    public MotionProfileStatus getProfileStatus()
    {
        return status;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;

        if(!enabled)
        {
            if(!controllerLock.isLocked())
            {
                controllerLock.lock();
                parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
            }
        }
        else
        {
            if(controllerLock.isHeldByCurrentThread())
            {
                controllerLock.unlock();
            }
        }
    }

    public void updateRobotState(Constants.Robot.Mode mode)
    {
        setEnabled(mode != Constants.Robot.Mode.DISABLED);
    }

    protected ReentrantLock getControllerLock()
    {
        return controllerLock;
    }

    protected Motor getParent()
    {
        return parent;
    }
}