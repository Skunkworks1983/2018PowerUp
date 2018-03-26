package frc.team1983.util.motion;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class competitiontest
{
    @Before
    public void setup()
    {

    }

    @After
    public void teardown()
    {

    }

    @Test
    public void test()
    {
        TrapezoidalProfile prof = new TrapezoidalProfile(Drivebase.getTicks(2), 2);

        for(double t = 0; t < 2; t += 0.1)
        {
            double s = prof.evaluatePosition(t);
            double v = prof.evaluateVelocity(t);
            double a = prof.evaluateAcceleration(t);
            double o = v + a;
            System.out.println(t + ", " + s + ", " + v + ", " + a + ", " + o);
        }
    }
}
