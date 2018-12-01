package frc.team1983.utility.control;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.util.HashMap;

public class Motor extends TalonSRX
{
    private static HashMap<Integer, Motor> motors = new HashMap<>();

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

        if(!motors.containsKey(port))
            motors.put(port, this);
        else
            throw new IllegalArgumentException("Motor already exists on port " + port);

        profiler = new Profiler(this);
    }

    public Motor(int port, boolean reversed)
    {
        this(port, reversed, false);
    }

    public static Motor getByID(int id)
    {
        if(motors.containsKey(id))
            return motors.get(id);
        else
            throw new IllegalArgumentException("Motor " + id + " does not exist");
    }
}
