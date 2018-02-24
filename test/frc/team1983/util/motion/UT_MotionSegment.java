package frc.team1983.util.motion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class UT_MotionSegment
{
    private MotionSegment segment;
    private MotionProfilePoint start;
    private MotionProfilePoint end;

    @Before
    public void setup()
    {
        start = new MotionProfilePoint(1, 1);
        end = new MotionProfilePoint(2, 2);

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
            MotionSegment seg = new MotionSegment(new MotionProfilePoint(0, 0), new MotionProfilePoint(0, 1));
        }
        catch (RuntimeException exception)
        {
            thrown = true;
        }

        if(!thrown)
            fail();
    }

    @Test
    public void exceptionForNonDomainEvaluation()
    {
        boolean thrown = false;

        try
        {
            segment.evaluate(end.getVelocity() + 1);
        }
        catch (RuntimeException exception)
        {
            thrown = true;
        }

        if(!thrown)
            fail();
    }

    @Test
    public void returnsBoundsForBoundsInput()
    {
        assertThat(segment.evaluate(start.getTime()), is(start.getTime()));
        assertThat(segment.evaluate(start.getVelocity()), is(start.getVelocity()));

        assertThat(segment.evaluate(end.getX()), is(end.getTime()));
        assertThat(segment.evaluate(end.getVelocity()), is(end.getVelocity()));
    }

    @Test
    public void returnsCorrectValueForValidInput()
    {
        assertThat(segment.evaluate((start.getTime() + end.getTime()) / 2), is((start.getTime() + end.getTime()) / 2));
        assertThat(segment.evaluate((start.getVelocity() + end.getVelocity()) / 2), is((start.getVelocity() + end.getVelocity()) / 2));
    }
}
