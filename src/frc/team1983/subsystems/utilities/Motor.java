package frc.team1983.subsystems.utilities;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder;

//Wrapper class around the WpiLib TalonSRX. Allows us to modify the functionality, and for future extendability.
public class Motor extends TalonSRX
{
    private boolean hasEncoder;

    public Motor(int port, NeutralMode neutralMode, boolean reversed)
    {
        super(port);
        setNeutralMode(neutralMode);
        setInverted(reversed);
    }

    public Motor(int port, NeutralMode neutralMode, boolean reversed, boolean hasEncoder)
    {
        this(port, neutralMode, reversed);
        this.hasEncoder = hasEncoder;

        if(this.hasEncoder)
        {
            //The values passed in here are mostly a guess, because the Phoenix documentation is terrible.
            //The example code doesn't pass in a third value, but I believe if timeoutMs is 0 than it ignores the value.
            configSelectedFeedbackSensor(QuadEncoder, 0, 0);
        }
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

    public int getPosition()
    {
        return getSelectedSensorPosition(0);
    }
}
