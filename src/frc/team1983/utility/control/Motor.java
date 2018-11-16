package frc.team1983.utility.control;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Motor extends TalonSRX
{
    private static Motor[] motors = new Motor[16];

    private Profiler profiler;

    public Motor(int port, boolean reversed, boolean hasEncoder)
    {
        super(port);
        setInverted(reversed);

        if(hasEncoder)
        {
            configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
            setSelectedSensorPosition(0, 0, 0);
        }

        motors[port] = this;
        profiler = new Profiler(this);
    }

    public Motor(int port, boolean reversed)
    {
        this(port, reversed, false);
    }

    public static Motor getByID(int id)
    {
        if(motors[id] != null)
            return motors[id];
        else
            throw new IllegalArgumentException("Motor " + id + " does not exist");
    }

    public void configPIDF(int slot, double P, double I, double D, double F)
    {

    }
}
