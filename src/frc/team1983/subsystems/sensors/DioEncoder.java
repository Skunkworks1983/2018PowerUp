package frc.team1983.subsystems.sensors;

import edu.wpi.first.wpilibj.Encoder;

//Created for extensibility down the line. Identical to the WpiLib Encoder class
public class DioEncoder extends Encoder
{
    public DioEncoder(int channelA, int channelB, boolean reversed)
    {
        super(channelA, channelB, reversed);
    }
}
