package org.usfirst.frc.team1983.robot.subsystems.Sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;


public class Gyro extends AHRS
{

    public Gyro(Port port)
    {
        super(port);
    }

    public Gyro() //there are two constructors here! turns out you can have multiple constructors based on what your arguments are!
    //this buddy lets you call the gyro with no arguments and it'll call the other constructor with the correct arguments to construct a gyro!
    //what a cool dude. a real helper.
    {
        this(Port.kMXP);
    }

    public double getGyroYaw()
    {
        return super.getAngle();
    }

    public void initDefaultCommand()
    {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

