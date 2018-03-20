package frc.team1983.util.motion.profiles;

public class TrapezoidalProfile extends CruiseProfile
{
    public TrapezoidalProfile(double distance, double duration, double accelTime1, double accelTime2)
    {
        super(distance, duration, 0, 0, accelTime1, accelTime2);
    }

    public TrapezoidalProfile(double distance, double duration)
    {
        super(distance, duration, 0, 0, duration * 0.25, duration * 0.25);
    }
}
