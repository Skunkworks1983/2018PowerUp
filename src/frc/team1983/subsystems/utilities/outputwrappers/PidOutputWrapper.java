package frc.team1983.subsystems.utilities.outputwrappers;

import edu.wpi.first.wpilibj.PIDOutput;

/*
  PidOutputWrapper is an implementation of PIDOutput. pidWrite is called by WPILib's PIDController, which in our case is
  over-written to allow for logging of each data point. writeHelper is the method that actual pidWrite logic should
  be over-written and implemented.
*/
public abstract class PidOutputWrapper implements PIDOutput
{
    private boolean disabled;

    //DO NOT OVERRIDE. Where logging happens.
    @Override
    public void pidWrite(double out)
    {
        if(!disabled)
        {
            //TODO: log
            writeHelper(out);
        }
    }

    //Must be implemented by any class that extends this one. Where all of your pidWrite logic should go.
    abstract void writeHelper(double out);

    public boolean isDisabled()
    {
        return disabled;
    }

    public void disable()
    {
        disabled = true;
    }

    public void enable()
    {
        disabled = false;
    }
}
