package frc.team1983.subsystems.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team1983.Robot;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.motion.MotionProfile;

//Wrapper class around the WpiLib TalonSRX. Allows us to modify the functionality, and for future extendability.
public class Motor extends TalonSRX
{
    private boolean hasEncoder = false;
    private ProfileController manager;

    public Motor(int port, boolean reversed)
    {
        super(port);
        setInverted(reversed);
    }

    public Motor(int port, boolean reversed, boolean hasEncoder)
    {
        this(port, reversed);

        if(hasEncoder)
        {
            configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
            setSelectedSensorPosition(0, 0, 0);
        }
    }

    public void configPIDF(int slot, double p, double i, double d, double f)
    {
        config_kP(slot, p, 0);
        config_kI(slot, i, 0);
        config_kD(slot, d, 0);
        config_kF(slot, f, 0);
    }

    // don't need this method but it makes things look more readable
    public void follow(Motor leader)
    {
        set(ControlMode.Follower, leader.getDeviceID());
    }

    public void setProfile(MotionProfile profile)
    {
        if(manager == null)
            manager = new ProfileController(this, Robot.getInstance());

        manager.setProfile(profile);
    }

    public void runProfile()
    {
        setProfileRunState(true);
    }

    public void stopProfile()
    {
        setProfileRunState(false);
    }

    public void setProfileRunState(boolean run)
    {
        if(manager == null)
            manager = new ProfileController(this, Robot.getInstance());

        manager.setEnabled(run);
    }
}
