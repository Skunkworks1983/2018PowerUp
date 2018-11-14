package frc.team1983.utility.control;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team1983.Robot;
import frc.team1983.utility.control.ClosedLoopGains;
import frc.team1983.utility.control.Profiler;
import frc.team1983.utility.control.ProfilerSignal;
import frc.team1983.utility.motion.MotionProfile;

import java.util.HashMap;

//Wrapper class around the WpiLib TalonSRX. Allows us to modify the functionality, and for future extendability.
public class Motor extends TalonSRX
{
    protected HashMap<Integer, ClosedLoopGains> gains;
    public Profiler manager;

    private double auxiliaryOutput;

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

    public void configPIDF(int slot, double p, double i, double d, double f)
    {
        config_kP(slot, p, 0);
        config_kI(slot, i, 0);
        config_kD(slot, d, 0);
        config_kF(slot, f, 0);
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
            super.config_kP(i, gains.get(i).get_kP(), 0);
            super.config_kI(i, gains.get(i).get_kI(), 0);
            super.config_kD(i, gains.get(i).get_kD(), 0);
            super.config_kF(i, gains.get(i).get_kF(), 0);
        }
    }

    public void setProfile(MotionProfile profile)
    {
        if(manager == null)
            manager = new Profiler(this, Robot.getInstance());

        manager.setProfile(profile);
    }

    public void linkSignal(ProfilerSignal signal)
    {
        if(manager == null)
            manager = new Profiler(this, Robot.getInstance());

        manager.linkSignal(signal);
    }

    public void unlinkSignal()
    {
        if(manager == null)
            manager = new Profiler(this, Robot.getInstance());

        manager.unlinkSignal();
    }

    public void runProfile()
    {
        setProfileRunState(true);
    }

    public void stopProfile()
    {
        setProfileRunState(false);
        auxiliaryOutput = 0;
    }

    public void setProfileRunState(boolean run)
    {
        if(manager == null)
            manager = new Profiler(this, Robot.getInstance());

        manager.setEnabled(run);
    }

    public boolean isProfileFinished()
    {
        return manager != null && manager.isProfileFinished();
    }

    public synchronized void setAuxiliaryOutput(double auxiliaryOutput)
    {
        this.auxiliaryOutput = auxiliaryOutput;
    }

    public synchronized double getAuxiliaryOutput()
    {
        return auxiliaryOutput;
    }

    @Override
    public void setInverted(boolean inverted)
    {
        super.setInverted(inverted);
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
