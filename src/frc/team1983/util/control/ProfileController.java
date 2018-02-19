package frc.team1983.util.control;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class ProfileController
{
    protected Motor parent;

    protected MotionProfile profile;
    protected MotionProfileStatus status;

    private ReentrantLock controllerLock;
    private ProfileControllerRunnable runnable;

    private Logger logger;

    private Thread thread;

    private boolean enabled = false;

    public ProfileController(Motor parent, Robot robot)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        this.parent = parent;
        this.parent.changeMotionControlFramePeriod(5);
        this.parent.clearMotionProfileTrajectories();

        status = new MotionProfileStatus();

        robot.addProfileController(this);

        controllerLock = new ReentrantLock();

        runnable = new ProfileControllerRunnable(this);
        thread = new Thread(runnable);
    }

    private void reset()
    {
        controllerLock.lock();

        runnable.reset();

        parent.clearMotionProfileTrajectories();
        parent.clearMotionProfileHasUnderrun(0);
        parent.configMotionProfileTrajectoryPeriod(0, 0);

        controllerLock.unlock();
    }

    public void setProfile(MotionProfile profile)
    {
        this.profile = profile;
        streamProfile(profile);
    }

    private void streamProfile(MotionProfile profile)
    {
        controllerLock.lock();

        reset();

        int durationMs = profile.getPointDuration();
        double duration = durationMs * 0.001;
        int resolution = (int) (profile.getTotalTime() / duration);

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

        logger.info("finished streaming");
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;

        if(!enabled)
        {
            if(!controllerLock.isLocked())
            {
                controllerLock.lock();
            }

            parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
        }
        else
        {
            if(controllerLock.isHeldByCurrentThread())
            {
                controllerLock.unlock();
            }
            else if(!runnable.isRunning())
            {
                thread.start();
            }
        }

        logger.info("setEnabled called");
    }

    public void updateRobotState(Constants.Robot.Mode mode)
    {
        setEnabled(false);
    }

    public boolean isProfileFinished()
    {
        controllerLock.lock();

        MotionProfileStatus status = new MotionProfileStatus();
        parent.getMotionProfileStatus(status);

        controllerLock.unlock();

        return hasProcessedBuffer() && status.btmBufferCnt == 1;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean hasProcessedBuffer()
    {
        return runnable.hasProcessedBuffer();
    }

    public MotionProfileStatus getProfileStatus()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        parent.getMotionProfileStatus(status);

        return status;
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