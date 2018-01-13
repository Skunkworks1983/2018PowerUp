package frc.team1983.subsystems.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;


public class Gyro extends AHRS
{

    public Gyro(Port port)
    {
        super(port);
    }
    //two constructors here, one of which requires no argument and calls the other with the correct argument.
    public Gyro()
    {
        this(Port.kMXP);
    }

    public double getGyroYaw()
    {
        return super.getAngle();
    }

    public void initDefaultCommand()
    {

    }
}

