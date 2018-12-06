package frc.team1983.utility.sensors;

import com.ctre.phoenix.sensors.PigeonIMU;
import frc.team1983.utility.control.Motor;

public class Pigeon extends PigeonIMU
{
    public Pigeon(Motor motor)
    {
        super(motor);
    }

    public void reset()
    {
        setFusedHeading(0);
    }

    public double getHeading()
    {
        return -getFusedHeading();
    }
}
