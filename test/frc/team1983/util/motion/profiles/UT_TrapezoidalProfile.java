package frc.team1983.util.motion.profiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class UT_TrapezoidalProfile
{
    private TrapezoidalProfile profile;

    @Before
    public void setup()
    {
        profile = new TrapezoidalProfile(1000, 1, 0.25, 0.25);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void returnsZeroVelocityForBoundsInputs()
    {
        assertThat(profile.evaluateVelocity(0), is(0.0));
        assertThat(profile.evaluateVelocity(1), is(0.0));
    }

    @Test
    public void travelsCorrectDistance()
    {
        assertThat(profile.evaluatePosition(0), is(0.0));
        assertThat(profile.evaluatePosition(1), closeTo(1000.0, 0.1));
    }
}
