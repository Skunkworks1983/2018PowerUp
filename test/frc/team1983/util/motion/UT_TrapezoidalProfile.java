package frc.team1983.util.motion;

import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void stitchesProfilesCorrectly()
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
}
