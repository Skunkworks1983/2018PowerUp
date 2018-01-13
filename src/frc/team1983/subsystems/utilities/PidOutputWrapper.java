package frc.team1983.subsystems.utilities;

import edu.wpi.first.wpilibj.PIDOutput;

public abstract class PidOutputWrapper implements PIDOutput
{
    @Override
    public void pidWrite(double out)
    {
        //TODO: log
        writeHelper(out);
    }

    abstract void writeHelper(double out);
}
