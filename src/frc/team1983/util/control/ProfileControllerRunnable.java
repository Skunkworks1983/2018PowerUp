package frc.team1983.util.control;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import org.apache.logging.log4j.core.Logger;

import java.util.concurrent.Semaphore;

public class ProfileControllerRunnable implements Runnable
{
    private ProfileController controller;
    private ProfileSignal signal;

    private boolean running = false;
    private boolean dead = false;
    private boolean hasProcessed = false;

    private Logger logger;

    public ProfileControllerRunnable(ProfileController controller, ProfileSignal signal)
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
            SetValueMotionProfile setValue = controller.isProfileFinished() ? SetValueMotionProfile.Hold : SetValueMotionProfile.Enable;
            double auxiliaryOutput = controller.parent.getAuxiliaryOutput();

            controller.parent.set(ControlMode.MotionProfile, setValue.value, DemandType.ArbitraryFeedForward, auxiliaryOutput);

            Thread.yield();
        }
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
