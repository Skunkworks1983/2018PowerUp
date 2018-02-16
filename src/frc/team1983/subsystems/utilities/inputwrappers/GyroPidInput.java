package frc.team1983.subsystems.utilities.inputwrappers;

import frc.team1983.subsystems.sensors.Gyro;

// Implementation of PidInputWrapper, specifically for the gyro sensor. Simply gets the yaw value of the NavX.
public class GyroPidInput extends PidInputWrapper
{
    Gyro gyro;

    public GyroPidInput(Gyro gyro)
    {
        this.gyro = gyro;
    }

    public double pidGet()
    {
        return gyro.getAngle();
    }
}
