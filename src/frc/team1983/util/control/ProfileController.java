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

public class ProfileController
{
    protected Motor parent;

    protected MotionProfile profile;
    protected ProfileSignal signal;

    private ProfileControllerRunnable runnable;
    private Thread thread;

    private Logger logger;

    public ProfileController(Motor parent, Robot robot)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        this.parent = parent;

        signal = new ProfileSignal();

        runnable = new ProfileControllerRunnable(this, signal);
        thread = new Thread(runnable);

        reset();
        robot.addProfileController(this);
    }

    private void reset()
    {
        parent.clearMotionProfileTrajectories();
        parent.clearMotionProfileHasUnderrun(0);

        parent.configMotionProfileTrajectoryPeriod(0, 0);

        parent.changeMotionControlFramePeriod(5);
        parent.clearMotionProfileTrajectories();

        runnable.reset();
    }

    public void setProfile(MotionProfile profile)
    {
        this.profile = profile;
        streamProfile(profile);
    }

    private void streamProfile(MotionProfile profile)
    {
        boolean state = signal.isEnabled();
        // lock runnable
        signal.setEnabled(false);

        reset();

        int durationMs = profile.getPointDuration();
        double duration = durationMs * 0.001;
        int resolution = (int) (profile.getTotalTime() / duration);

        for(int i = 0; i <= resolution; i++)
        {
            double t = i * duration;

            TrajectoryPoint point = new TrajectoryPoint();

            point.position = profile.evaluatePosition(t);
            point.velocity = profile.evaluateOutput(t);

            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;

            point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms.valueOf(profile.getPointDuration());

            point.zeroPos = i == 0;
            point.isLastPoint = i == (resolution - 1);

            parent.pushMotionProfileTrajectory(point);
        }

        // unlock runnable
        signal.setEnabled(state);
    }

    public MotionProfileStatus getProfileStatus()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        parent.getMotionProfileStatus(status);

        return status;
    }

    public boolean isProfileFinished()
    {
        MotionProfileStatus status = getProfileStatus();

        return runnable.hasProcessed() && (status.isUnderrun || (status.btmBufferCnt == 1 || status.btmBufferCnt == 0));
    }

    public void setEnabled(boolean enabled)
    {
        signal.setEnabled(enabled);

        if(enabled)
        {
            if(!runnable.isRunning() && !runnable.isDead())
            {
                thread.start();
            }
        }
        else
        {
            reset();
            parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
        }

        parent.config_kF(0, enabled ? 1 : 0, 0);
    }

    public void updateRobotState(Constants.MotorMap.Mode mode)
    {
        setEnabled(false);

        reset();
    }
    protected Motor getParent()
    {
        return parent;
    }
}