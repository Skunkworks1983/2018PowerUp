package frc.team1983.subsystems.utilities;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team1983.Robot;
import frc.team1983.util.control.ClosedLoopGains;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.motion.MotionProfile;

import java.util.HashMap;

//Wrapper class around the WpiLib TalonSRX. Allows us to modify the functionality, and for future extendability.
public class Motor extends TalonSRX
{
    protected HashMap<Integer, ClosedLoopGains> gains;
    private boolean hasEncoder = false;
    public ProfileController manager;

    public Motor(int port, boolean reversed)
    {
        super(port);
        setInverted(reversed);

        gains = new HashMap<>();
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

    // bad
    public ClosedLoopGains getGains(int slot)
    {
        if(!gains.containsKey(slot))
        {
            ClosedLoopGains newGains = new ClosedLoopGains();
            gains.put(slot, newGains);
        }

        return gains.get(slot);
    }

    public void setGains(int slot, ClosedLoopGains gains)
    {
        this.gains.put(slot, gains);
        updateTalonGains();
    }

    private void updateTalonGains()
    {
        for(Integer i : gains.keySet())
        {
            config_kP(i, gains.get(i).get_kP(), 0);
            config_kI(i, gains.get(i).get_kI(), 0);
            config_kD(i, gains.get(i).get_kD(), 0);
            config_kF(i, gains.get(i).get_kF(), 0);
        }
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
    public ErrorCode config_kP(int slot, double value, int timeout)
    {
        getGains(slot).config_kP(value);
        updateTalonGains();
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode config_kI(int slot, double value, int timeout)
    {
        getGains(slot).config_kI(value);
        updateTalonGains();
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode config_kD(int slot, double value, int timeout)
    {
        getGains(slot).config_kD(value);
        updateTalonGains();
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode config_kF(int slot, double value, int timeout)
    {
        getGains(slot).config_kF(value);
        updateTalonGains();
        return ErrorCode.OK;
    }
}
