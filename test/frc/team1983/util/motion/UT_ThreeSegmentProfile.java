package frc.team1983.util.motion;

import frc.team1983.util.motion.profiles.ThreeSegmentProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class UT_ThreeSegmentProfile
{
    private ThreeSegmentProfile profile;

    private double distance = 10000;
    private double duration = 5;

    private double vel_max;
    private double acc_max;

    private double accelTime1 = 1;
    private double accelTime2 = 1;

    private double initialVelocity = 500;
    private double finalVelocity = 0;

    @Before
    public void setup()
    {
        profile = new ThreeSegmentProfile(distance, duration, accelTime1, accelTime2, initialVelocity, finalVelocity);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void correctVelocityOutput()
    {
        assertThat(profile.evaluateVelocity(0), is(initialVelocity));
        assertThat(profile.evaluateVelocity(profile.getAccelerationTime1()), is(profile.getCruiseVelocity()));
        assertThat(profile.evaluateVelocity(duration / 2), is(profile.getCruiseVelocity()));
        assertThat(profile.evaluateVelocity(profile.getAccelerationTime2()), is(profile.getCruiseVelocity()));
        assertThat(profile.evaluateVelocity(duration), is(finalVelocity));
    }

    @Test
    public void correctPositionOutput()
    {
        assertThat(profile.evaluatePosition(0.0), is(0.0));
        assertThat(profile.evaluatePosition(duration), is(distance));
    }
}
