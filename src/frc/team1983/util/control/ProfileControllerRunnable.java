package frc.team1983.util.control;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;

import java.util.concurrent.Semaphore;

public class ProfileControllerRunnable implements java.lang.Runnable
{
    private ProfileController controller;

    public ProfileControllerRunnable(ProfileController controller)
    {
        this.controller = controller;
    }

    public void run()
    {
        while(true)
        {
            controller.getControllerLock().lock();
            controller.getControllerLock().unlock();

            controller.parent.processMotionProfileBuffer();
            controller.parent.getMotionProfileStatus(controller.status);

            // true if the talon runs out of trajectory points (times out or finishes profile)
            if(controller.status.isUnderrun || (controller.status.isLast && controller.status.activePointValid))
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
