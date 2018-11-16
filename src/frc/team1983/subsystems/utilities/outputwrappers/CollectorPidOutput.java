package frc.team1983.subsystems.utilities.outputwrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.subsystems.Collector;

public class CollectorPidOutput extends PidOutputWrapper
{
    private Collector collector;

    public CollectorPidOutput(Collector collector)
    {
        this.collector = collector;
    }

    //Sets the speed of the drivebase sides opposite to each other, based on pidWrite.
    public void writeHelper(double out)
    {
        collector.setRotate(ControlMode.PercentOutput, out);
    }
}