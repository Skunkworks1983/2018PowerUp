package org.usfirst.frc.team1983.robot.subsystems.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Motor
{
    private TalonSRX talon;
    private int port;

    public Motor(int port, NeutralMode neutralMode, boolean reversed)
    {
        this.port = port;
        this.talon = new TalonSRX(port);
        this.talon.setNeutralMode(neutralMode);
        this.talon.setInverted(reversed);
    }

    //Set the percent output of the motor
    public void set(double value)
    {
        talon.set(ControlMode.PercentOutput, value);
    }

    //Match the output settings of a master motor
    public void follow(Motor master)
    {
        talon.set(ControlMode.Follower, master.port);
    }

    //Returns the port of this motor so slave motors can follow it
    public int getPort() {
        return port;
    }
}

