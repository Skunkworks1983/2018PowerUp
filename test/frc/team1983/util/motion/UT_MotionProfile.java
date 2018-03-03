package frc.team1983.util.motion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class UT_MotionProfile
{
    private MotionProfile profile;

    @Before
    public void setup()
    {
        profile = new MotionProfile(new ArrayList<MotionSegment>(Arrays.asList(
                new MotionSegment(0, 1000, 1, 1000),
                new MotionSegment(1, 1000, 2, 2000)
        )));

        profile.duration = 2;
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void profileCorrectlyEvaluatesPosition()
    {
        System.out.println(profile.isContinuous());

        assertThat(profile.evaluatePosition(0), is(0.0));
        assertThat(profile.evaluatePosition(1), is(1000.0));
    }

    @Test
    public void profileCorrectlyEvaluatesVelocity()
    {
        assertThat(profile.evaluateVelocity(0), is(1000.0));
        assertThat(profile.evaluateVelocity(profile.getDuration()), is(2000.0));
    }

    @Test
    public void profileCorrectlyEvaluatesAcceleration()
    {
        assertThat(profile.evaluateAcceleration(0), closeTo(0.0, 0.1));
        assertThat(profile.evaluateAcceleration(profile.getDuration()), is(1000.0));
    }
}
