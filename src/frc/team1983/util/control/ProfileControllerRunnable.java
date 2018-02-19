package frc.team1983.util.control;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import org.apache.logging.log4j.core.Logger;

import java.util.concurrent.Semaphore;

public class ProfileControllerRunnable implements Runnable
{
    private ProfileController controller;
    private boolean running = false;
    private boolean hasProcessed = false;
    Logger logger;

    public ProfileControllerRunnable(ProfileController controller)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.controller = controller;
    }

    public void run()
    {
        this.running = true;

        while(true)
        {
            controller.getControllerLock().lock();
            controller.getControllerLock().unlock();

            controller.parent.processMotionProfileBuffer();
            MotionProfileStatus status = controller.getProfileStatus();

            // wait until we've processed a minimum number of points to check if we're finished
            if(!hasProcessed && status.btmBufferCnt > Constants.Motion.MIN_POINTS_IN_TALON)
            {
                logger.info("motor" + controller.parent.getDeviceID() + " processed");
                hasProcessed = true;
            }

            // true if the talon runs out of trajectory points (times out or finishes profile)
            if(status.isUnderrun || status.btmBufferCnt == 0)
            {
                controller.parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
            }
            else
            {
                controller.parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
            }

            Thread.yield();
        }
    }

    public void reset()
    {
        this.hasProcessed = false;
    }

    public boolean isRunning()
    {
        return running;
    }

    public boolean hasProcessedBuffer()
    {
        return hasProcessed;
    }
}
