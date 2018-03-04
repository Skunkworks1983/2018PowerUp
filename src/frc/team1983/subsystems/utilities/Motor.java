package frc.team1983.subsystems.utilities;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team1983.Robot;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.motion.MotionProfile;

import java.util.ArrayList;
import java.util.List;

//Wrapper class around the WpiLib TalonSRX. Allows us to modify the functionality, and for future extendability.
public class Motor extends TalonSRX
{
    protected double Kp, Ki, Kd, Ks, Kv, Ka;

    private boolean hasEncoder = false;
    public ProfileController manager;

    public List<PidControllerWrapper> pids = new ArrayList<PidControllerWrapper>();

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

    public void configSVA(int slot, double s, double v, double a)
    {
        this.Ks = s;
        this.Kv = v;
        this.Ka = a;
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

    public boolean isProfileFinished()
    {
        return manager != null && manager.isProfileFinished();
    }

    @Override
    public void set(ControlMode mode, double value)
    {
        super.set(mode, value);
    }

    @Override
    public ErrorCode config_kP(int slotIdx, double value, int timeout)
    {
        this.Kp = value;
        return super.config_kP(slotIdx, value, timeout);
    }

    @Override
    public ErrorCode config_kI(int slotIdx, double value, int timeout)
    {
        this.Ki = value;
        return super.config_kP(slotIdx, value, timeout);
    }

    @Override
    public ErrorCode config_kD(int slotIdx, double value, int timeout)
    {
        this.Kd = value;
        return super.config_kP(slotIdx, value, timeout);
    }

    @Override
    public ErrorCode config_kF(int slotIdx, double value, int timeout)
    {
        this.Ks = value;
        return super.config_kP(slotIdx, value, timeout);
    }
}
