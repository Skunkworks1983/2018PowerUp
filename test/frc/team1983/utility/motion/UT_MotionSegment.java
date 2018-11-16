package frc.team1983.utility.motion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class UT_MotionSegment
{
    private MotionSegment segment;
    private MotionPoint start;
    private MotionPoint end;

    @Before
    public void setup()
    {
        start = new MotionPoint(1, 1);
        end = new MotionPoint(2, 2);

        segment = new MotionSegment(start, end);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void exceptionForUndefinedSegment()
    {
        boolean thrown = false;

        try
        {
            MotionSegment seg = new MotionSegment(new MotionPoint(0, 0), new MotionPoint(0, 1));
        } catch (RuntimeException exception)
        {
            thrown = true;
        }

        if (!thrown)
            fail();
    }

    @Test
    public void exceptionForNonDomainEvaluation()
    {
        boolean thrown = false;

        try
        {
            segment.evaluateVelocity(end.getVelocity() + 1);
        } catch (RuntimeException exception)
        {
            thrown = true;
        }

        if (!thrown)
            fail();
    }

    @Test
    public void returnsBoundsForBoundsInput()
    {
        assertThat(segment.evaluateVelocity(start.getTime()), is(start.getTime()));
        assertThat(segment.evaluateVelocity(start.getVelocity()), is(start.getVelocity()));

        assertThat(segment.evaluateVelocity(end.getX()), is(end.getTime()));
        assertThat(segment.evaluateVelocity(end.getVelocity()), is(end.getVelocity()));
    }

    @Test
    public void returnsCorrectValueForValidInput()
    {
        assertThat(segment.evaluateVelocity((start.getTime() + end.getTime()) / 2), is((start.getTime() + end.getTime()) / 2));
        assertThat(segment.evaluateVelocity((start.getVelocity() + end.getVelocity()) / 2), is((start.getVelocity() + end.getVelocity()) / 2));
    }
}