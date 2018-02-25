package frc.team1983.util.control;

import java.util.logging.Logger;

public class ProfileSignal
{
    protected boolean enabled = false;

    public ProfileSignal()
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
