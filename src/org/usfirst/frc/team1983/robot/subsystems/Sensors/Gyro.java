package org.usfirst.frc.team1983.robot.subsystems.Sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;


public class Gyro extends AHRS {

    public Gyro(Port port) {
        super(port);
    }

    public Gyro() {
        this(Port.kMXP);
    }

    public double getGyroValue() {
        return super.getAngle();
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

