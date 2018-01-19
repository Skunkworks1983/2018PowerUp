package frc.team1983.subsystems.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Motor extends TalonSRX
{
    public Motor(int port, NeutralMode neutralMode, boolean reversed)
    {
        super(port);
        setNeutralMode(neutralMode);
        setInverted(reversed);
    }

    //Set the percent output of the motor
    public void set(double value)
    {
        super.set(ControlMode.PercentOutput, value);
    }

    //Match the output settings of a master motor
    public void follow(Motor master)
    {
        super.set(ControlMode.Follower, master.getDeviceID());
    }

}
