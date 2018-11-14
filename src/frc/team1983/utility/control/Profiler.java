package frc.team1983.utility.control;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.utility.motion.MotionProfile;
import org.apache.logging.log4j.core.Logger;

public class Profiler
{
    protected Motor parent;

    protected MotionProfile profile;
    protected ProfilerSignal signal;
    protected ProfilerSignal linkedSignal;

    private ProfilerRunnable runnable;
    private Thread thread;

    private static Profiler[] profilers = new Profiler[16];

    private Logger logger;

    public Profiler(Motor parent, Robot robot)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        this.parent = parent;

        signal = new ProfilerSignal();

        runnable = new ProfilerRunnable(this, signal);
        thread = new Thread(runnable);

        reset();

        profilers[parent.getBaseID()] = this;
    }

    private void reset()
    {
        parent.clearMotionProfileTrajectories();
        parent.clearMotionProfileHasUnderrun(0);

        parent.changeMotionControlFramePeriod(5);

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

        int durationMs = 100;
        double duration = durationMs * 0.001;
        int resolution = (int) (profile.getDuration() / duration);

        parent.configMotionProfileTrajectoryPeriod(durationMs, 0);

        ClosedLoopGains gains = parent.getGains(0);

        for(int i = 0; i <= resolution; i++)
        {
            double t = i * duration;

            TrajectoryPoint point = new TrajectoryPoint();

            point.position = -profile.evaluatePosition(t);
            // velocity is actually percent output

            double output = (gains.get_kV() * profile.evaluateVelocity(t)) + (gains.get_kA() * profile.evaluateAcceleration(t));
            double ks = gains.get_kV() * (output / Math.abs(output));
            point.velocity = ks + output;

            point.auxiliaryPos = 0;

            point.profileSlotSelect0 = 0;

            point.timeDur = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms;

            point.zeroPos = i == 0;
            point.isLastPoint = i == (resolution) || i == resolution - 1;

            point.velocity = point.isLastPoint ? gains.get_kV() : point.velocity;

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
        if(linkedSignal == null)
        {
            signal.setEnabled(enabled);
        }

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

    public void linkSignal(ProfilerSignal signal)
    {
        this.linkedSignal = signal;
        runnable.setSignal(linkedSignal);
    }

    public void unlinkSignal()
    {
        this.linkedSignal = null;
        runnable.setSignal(signal);
    }

    public void updateRobotState(Constants.MotorMap.Mode mode)
    {
        setEnabled(false);
        parent.set(ControlMode.PercentOutput, 0);

        reset();
    }
    protected Motor getParent()
    {
        return parent;
    }
}