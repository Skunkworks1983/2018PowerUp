package frc.team1983.utility.motion.profiles;

import frc.team1983.utility.math.Segment;
import frc.team1983.utility.math.Vector2;
import frc.team1983.utility.motion.MotionProfile;

public class TrapezoidalVelocityProfile extends MotionProfile
{
    public TrapezoidalVelocityProfile(double distance, double time, double accelTime1, double accelTime2)
    {
        super(new Segment[] {
            new Segment(new Vector2(0, 0),
                        new Vector2(accelTime1, 2 * distance / ((2 * time) - accelTime1 - accelTime2))),
            new Segment(new Vector2(accelTime1, 2 * distance / ((2 * time) - accelTime1 - accelTime2)),
                        new Vector2(time - accelTime2, 2 * distance / ((2 * time) - accelTime1 - accelTime2))),
            new Segment(new Vector2(time - accelTime2, 2 * distance / ((2 * time) - accelTime1 - accelTime2)),
                        new Vector2(time, 0))
        });
    }

    public TrapezoidalVelocityProfile(double distance, double time)
    {
        this(distance, time, time / 4, time / 4);
    }
}
