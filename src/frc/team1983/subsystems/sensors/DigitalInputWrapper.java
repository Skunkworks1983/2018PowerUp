package frc.team1983.subsystems.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInputWrapper extends DigitalInput
{
    private boolean reversed;

    public DigitalInputWrapper(int port, boolean reversed)
    {
        super(port);

        this.reversed = reversed;
    }

    public boolean get()
    {
        return super.get() ^ reversed;
    }
}
