package frc.team1983.utility.control;

public class ProfilerSignal
{
    protected boolean enabled = false;

    public ProfilerSignal()
    {

    }

    public synchronized boolean isEnabled()
    {
        return enabled;
    }

    public synchronized void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
}
