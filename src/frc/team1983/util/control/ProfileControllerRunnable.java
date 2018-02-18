package frc.team1983.util.control;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team1983.services.logger.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

import java.util.concurrent.Semaphore;

public class ProfileControllerRunnable implements Runnable
{
    private ProfileController controller;
    Logger logger;

    public ProfileControllerRunnable(ProfileController controller)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
        this.controller = controller;
    }

    public void run()
    {
        while(true)
        {
            controller.getControllerLock().lock();
            controller.getControllerLock().unlock();

            logger.info("thread running");

            controller.parent.processMotionProfileBuffer();
            controller.parent.getMotionProfileStatus(controller.status);

            // true if the talon runs out of trajectory points (times out or finishes profile)
            if(controller.status.isUnderrun)
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
}
