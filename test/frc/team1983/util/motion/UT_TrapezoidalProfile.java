package frc.team1983.util.motion;

import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class UT_TrapezoidalProfile
{
    private TrapezoidalProfile profile;

    private double distance = 10000;
    private double duration = 5;

    private double accelTime1 = 1;
    private double accelTime2 = 1;

    @Before
    public void setup()
    {
        profile = new TrapezoidalProfile(distance, duration, accelTime1, accelTime2);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void returnsZeroForBoundsInputs()
    {
        assertThat(profile.evaluateVelocity(0), is(0.0));
        assertThat(profile.evaluateVelocity(duration), is(0.0));
    }

    @Test
    public void stitchesTwoProfilesCorrectly()
    {
        double d1 = 1000.0, t1 = 1.0;
        double d2 = 1000.0, t2 = 2.0;

        TrapezoidalProfile profile1 = new TrapezoidalProfile(d1, t1);
        TrapezoidalProfile profile2 = new TrapezoidalProfile(d2, t2);

        assertThat(profile1.evaluatePosition(t1), closeTo(d1, 0.1));
        assertThat(profile2.evaluatePosition(t2), closeTo(d2, 0.1));

        TrapezoidalProfile.stitch(profile1, profile2);

        assertThat(profile1.evaluateVelocity(t1), closeTo(profile2.getCruiseVelocity(), 0.1));

        double d1prime = profile1.evaluatePosition(profile1.getTotalTime());
        double d2prime = profile2.evaluatePosition(profile2.getTotalTime());

        assertThat(d1prime + d2prime, closeTo(d1 + d2, 0.1));
    }

    @Test
    public void stitchesProfilelistCorrectly()
    {
        double d1 = 1000.0, d2 = 1500.0, d3 = 2000.0, d4 = 2500.0;
        double t1 = 1.0, t2 = 1.0, t3 = 1.0, t4 = 1.0;

        List<TrapezoidalProfile> profiles = new ArrayList<>(Arrays.asList(
                new TrapezoidalProfile(d1, t1),
                new TrapezoidalProfile(d2, t2)/*,
                new TrapezoidalProfile(d3, t3),
                new TrapezoidalProfile(d4, t4)*/
                                                                         ));

        System.out.println(profiles.get(0).getCruiseVelocity() + "|" + profiles.get(1).getCruiseVelocity());
        TrapezoidalProfile.stitch(profiles.get(0), profiles.get(1));
        System.out.println(profiles.get(0).getCruiseVelocity() + "|" + profiles.get(1).getCruiseVelocity());

        double ot = 0;

        for(int i = 0; i < profiles.size(); i++)
        {
            for(double a = 0; a <= 20; a++)
            {
                double t = (a / 20) * profiles.get(i).getTotalTime();
                System.out.println((ot + t) + ", " + profiles.get(i).evaluateVelocity(t));
            }

            ot += profiles.get(i).getTotalTime();
        }

        double d = 0;
        double t = 0;

        for(TrapezoidalProfile profile : profiles)
        {
            d += profile.evaluatePosition(profile.getTotalTime());
            t += profile.getTotalTime();
        }

        assertThat(d, closeTo(d1 + d2 + d3 + d4, 0.1));
        assertThat(t, closeTo(t1 + t2 + t3 + t4, 0.1));
    }
}
