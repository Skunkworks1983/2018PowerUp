package frc.team1983.util.motion;

import frc.team1983.settings.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class UT_MotionProfile
{
    private MotionProfile profile;

    private double distance = 4096;
    private double totalTime = 5;
    private double vel_max = 2046;
    private double acc_max = 4096;

    @Before
    public void setup()
    {
        profile = new MotionProfile(distance, totalTime, vel_max, acc_max);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void exceptionForUndefinedProfile()
    {
        boolean thrown = false;

        try
        {
            MotionProfile profile = new MotionProfile(4096, 1, 1, 1);
        }
        catch (RuntimeException exception)
        {
            thrown = true;
        }

        if(!thrown)
            fail();
    }

    @Test
    public void exceptionForNonDomainInput()
    {
        boolean thrown = false;

        try
        {
            profile.evaluateVelocity(-1);
        }
        catch (RuntimeException exception)
        {
            thrown = true;
        }

        if(!thrown)
            fail();
    }

    @Test
    public void returnsZeroForBounds()
    {
        assertThat(profile.evaluateVelocity(0), closeTo(0.0, 0.1));
        assertThat(profile.evaluateVelocity(totalTime), closeTo(0.0, 0.1));
    }

    @Test
    public void returnsConstantVelocityForMiddleInput()
    {
        double t_a = (totalTime * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2;
        assertThat(profile.evaluateVelocity(totalTime / 2), is(distance / (totalTime - t_a)));
    }

    @Test
    public void travelsCorrectDistance()
    {
        assertThat(profile.evaluatePosition(totalTime), closeTo(distance, 0.1));
        assertThat(profile.evaluatePosition(totalTime / 2), closeTo(distance / 2, 0.1));
        assertThat(profile.evaluatePosition(0), closeTo(0, 0.1));
    }
}
