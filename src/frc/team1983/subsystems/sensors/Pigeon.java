package frc.team1983.subsystems.sensors;

import com.ctre.phoenix.sensors.PigeonIMU;
import frc.team1983.subsystems.utilities.Motor;

public class Pigeon extends PigeonIMU
{
    public Pigeon(Motor motor)
    {
        super(motor);
    }

    public Pigeon(int id)
    {
        super(id);
    }

    public boolean isDead()
    {
        return false; //TODO Write logic
    }

    public double getAngle()
    {
        double[] xyz = new double[3];
        getAccumGyro(xyz);
        return xyz[1];
    }
}
