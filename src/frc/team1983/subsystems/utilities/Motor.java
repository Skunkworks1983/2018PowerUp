package frc.team1983.subsystems.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.motion.MotionProfile;

//Wrapper class around the WpiLib TalonSRX. Allows us to modify the functionality, and for future extendability.
public class Motor extends TalonSRX
{
    private boolean hasEncoder;
    private ProfileController manager;

    public Motor(int port, boolean reversed)
    {
        super(port);
        setInverted(reversed);

        manager = new ProfileController(this);
        setProfileRunState(false);
    }

    public Motor(int port, boolean reversed, boolean hasEncoder)
    {
        super(port);
        setInverted(reversed);

        if(hasEncoder)
        {
            configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        }

        manager = new ProfileController(this);
        setProfileRunState(false);
    }

    // don't need this method but it makes things look more readable
    public void follow(Motor leader)
    {
        set(ControlMode.Follower, leader.getDeviceID());
    }

    public void setProfile(MotionProfile profile)
    {
        manager.setProfile(profile);
    }

    public void runLoadedProfile()
    {
        setProfileRunState(true);
    }

    public void stopLoadedProfile()
    {
        setProfileRunState(false);
    }

    public void setProfileRunState(boolean run)
    {
        manager.setEnabled(run);
    }
}
