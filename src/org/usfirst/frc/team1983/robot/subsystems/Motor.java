package org.usfirst.frc.team1983.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Motor
{
    private TalonSRX talon;
    public int port; //why public? write a getter

    public Motor(int port, NeutralMode neutralMode, boolean reversed)
    {
        this.port = port;
        this.talon = new TalonSRX(port);
        this.talon.setNeutralMode(neutralMode);
        this.talon.setInverted(reversed);
    }

    //Write comments at the top of your functions describing what they do (~1 sentence)
    public void set(double value)
    {
        talon.set(ControlMode.PercentOutput, value);
    }

    //What do I do?
    public void follow(Motor master)
    {
        talon.set(ControlMode.Follower, master.port);
    }
}

