package org.usfirst.frc.team1983.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Motor {
    private TalonSRX talon;
    public int port;
    private static ControlMode PERCENT_OUTPUT = ControlMode.PercentOutput;
    private static ControlMode FOLLOW = ControlMode.Follower;

    public Motor(int port, NeutralMode neutralMode, boolean reversed)
    {
        this.port = port;
        this.talon = new TalonSRX(port);
        this.talon.setNeutralMode(neutralMode);
        this.talon.setInverted(reversed);
    }

    public void set(double value) {
        talon.set(PERCENT_OUTPUT, value);
    }

    public void follow(Motor master)
    {
        talon.set(FOLLOW, master.port);
    }
}

