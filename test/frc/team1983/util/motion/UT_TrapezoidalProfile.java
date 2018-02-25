package frc.team1983.util.motion;

import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
