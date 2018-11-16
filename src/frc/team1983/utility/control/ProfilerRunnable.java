package frc.team1983.utility.control;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.Constants;
import org.apache.logging.log4j.core.Logger;

public class ProfilerRunnable implements Runnable
{
    private Profiler controller;
    private ProfilerSignal signal;

    private boolean running = false;
    private boolean dead = false;
    private boolean hasProcessed = false;

    private Logger logger;

    public ProfilerRunnable(Profiler controller, ProfilerSignal signal)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.controller = controller;
        this.signal = signal;
    }

    public void run()
    {
        this.running = true;

        while(!dead)
        {
            while(!signal.enabled)
            {
                // wait for signal to run
                // Thread.yield();
            }

            controller.parent.processMotionProfileBuffer();

            if(!hasProcessed && controller.getProfileStatus().btmBufferCnt > Constants.Motion.MIN_POINTS_IN_TALON)
                hasProcessed = true;

            // true if the talon runs out of trajectory points (times out or finishes profile)
            SetValueMotionProfile setValue = controller.isProfileFinished() ? SetValueMotionProfile.Disable : SetValueMotionProfile.Enable;
            double auxiliaryOutput = controller.parent.getAuxiliaryOutput();

            controller.parent.set(ControlMode.MotionProfile, setValue.value, DemandType.ArbitraryFeedForward, auxiliaryOutput);

            Thread.yield();
        }
    }

    public synchronized void setSignal(ProfilerSignal signal)
    {
        this.signal = signal;
    }

    public synchronized void reset()
    {
        this.hasProcessed = false;
    }

    public synchronized void kill()
    {
        this.dead = true;
    }

    public synchronized boolean isRunning()
    {
        return running && !dead;
    }

    public synchronized boolean isDead()
    {
        return dead;
    }

    public synchronized boolean hasProcessed()
    {
        return hasProcessed;
    }
}
