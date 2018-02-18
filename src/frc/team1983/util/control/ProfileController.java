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

    private ReentrantLock controllerLock = new ReentrantLock();
    private ProfileControllerRunnable runnable;

    private Thread thread;

    private Logger logger;

    private boolean enabled = false;

    public ProfileController(Motor parent, Robot robot)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        this.parent = parent;
        this.parent.changeMotionControlFramePeriod(5);
        this.parent.clearMotionProfileTrajectories();

        status = new MotionProfileStatus();

        robot.addProfileController(this);

        runnable = new ProfileControllerRunnable(this);
        thread = new Thread(runnable);
    }

    public void setProfile(MotionProfile profile)
    {
        this.profile = profile;
        streamProfile(profile);
    }

    private void streamProfile(MotionProfile profile)
    {
        logger.info("locked");
        controllerLock.lock();

        int durationMs = profile.getPointDuration();
        double duration = durationMs * 0.001;
        int resolution = (int) (profile.getTotalTime() / duration);

        parent.clearMotionProfileTrajectories();
        parent.clearMotionProfileHasUnderrun(0);

        parent.configMotionProfileTrajectoryPeriod(0, 0);

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
        logger.info("unlocked");
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
                logger.info("locked");
                controllerLock.lock();
            }

            parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
        }
        else
        {
            if(controllerLock.isHeldByCurrentThread())
            {
                controllerLock.unlock();
                logger.info("unlocked");

                if(!thread.isAlive())
                {
                    thread.start();
                }
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