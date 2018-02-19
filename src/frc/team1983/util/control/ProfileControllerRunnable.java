package frc.team1983.util.control;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;

public class ProfileControllerRunnable implements java.lang.Runnable
{
    private ProfileController controller;
    private int a = 0;

    public ProfileControllerRunnable(ProfileController controller)
    {
        this.controller = controller;
    }

    public void run()
    {
        while(true)
        {
            if(!DriverStation.getInstance().isDisabled())
            {
                controller.getControllerLock().lock();
                controller.getControllerLock().unlock();

                controller.getTalonLock().lock();
                controller.parent.processMotionProfileBuffer();
                controller.parent.getMotionProfileStatus(controller.status);
                controller.getTalonLock().unlock();

                if(controller.isEnabled())
                {
                    // true if the talon runs out of trajectory points (times out or finishes profile)
                    if(controller.status.isUnderrun || (controller.status.isLast && controller.status.activePointValid))
                    {
                        controller.getTalonLock().lock();
                        controller.parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
                        controller.getTalonLock().unlock();
                    }
                    else
                    {
                        controller.getTalonLock().lock();
                        controller.parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
                        controller.getTalonLock().unlock();
                    }
                }
            }
            else
            {
                if(controller.isEnabled())
                {
                    controller.getTalonLock().lock();
                    controller.parent.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
                    controller.getTalonLock().unlock();

                    controller.setEnabled(false);
                }
            }

            Thread.yield();
        }
    }
}
