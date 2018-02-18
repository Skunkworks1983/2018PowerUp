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
    private double t_total = 5;

    private double vel_max;
    private double acc_max;

    private double t_a1 = 1;
    private double t_a2 = 1;

    private double v_i = 500;
    private double v_f = 0;

    @Before
    public void setup()
    {
        profile = new ThreeSegmentProfile(distance, t_total, t_a1, t_a2, v_i, v_f);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void correctVelocityOutput()
    {
        assertThat(profile.evaluateVelocity(0), is(v_i));
        assertThat(profile.evaluateVelocity(profile.getAccelerationTime1()), is(profile.getCruiseVelocity()));
        assertThat(profile.evaluateVelocity(t_total / 2), is(profile.getCruiseVelocity()));
        assertThat(profile.evaluateVelocity(profile.getAccelerationTime2()), is(profile.getCruiseVelocity()));
        assertThat(profile.evaluateVelocity(t_total), is(v_f));
    }

    @Test
    public void correctPositionOutput()
    {
        assertThat(profile.evaluatePosition(0.0), is(0.0));
        assertThat(profile.evaluatePosition(t_total), is(distance));
    }
}
