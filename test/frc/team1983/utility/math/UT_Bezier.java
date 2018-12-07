package frc.team1983.utility.math;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UT_Bezier
{
    @Test
    public void evaluateTest()
    {
        Bezier b = new Bezier(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1));

        assertThat(Vector2.equals(b.evaluate(0), new Vector2(0, 0)), is(true));
        assertThat(Vector2.equals(b.evaluate(1), new Vector2(1, 1)), is(true));

        b = new Bezier(new Vector2(-10, -4), new Vector2(3, 7), new Vector2(3, 5));

        assertThat(Vector2.equals(b.evaluate(0), new Vector2(-10, -4)), is(true));
        assertThat(Vector2.equals(b.evaluate(1), new Vector2(3, 7)), is(true));
    }

    @Test
    public void lengthTest()
    {
        Bezier b = new Bezier(new Vector2(0, 10), new Vector2(10, 10));

        assertThat(b.getLength(), is(10.0));
    }

    @Test
    public void tangentTest()
    {
        Bezier b = new Bezier(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1));

        assertThat(Vector2.equals(b.evaluateTangent(0), new Vector2(1, 0)), is(true));
        assertThat(Vector2.equals(b.evaluateTangent(1), new Vector2(0, 1)), is(true));
    }

    @Test
    public void normalTest()
    {
        Bezier b = new Bezier(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1));

        assertThat(Vector2.equals(b.evaluateNormal(0), new Vector2(0, 1)), is(true));
        assertThat(Vector2.equals(b.evaluateNormal(1), new Vector2(-1, 0)), is(true));
    }

    @Test
    public void offsetTest()
    {
        Bezier b = new Bezier(new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1));

        assertThat(Vector2.equals(b.offset(0, 1), new Vector2(0, 1)), is(true));
        assertThat(Vector2.equals(b.offset(1, 1), new Vector2(0, 1)), is(true));
    }

    @Test
    public void radiusOfCurvatureTest()
    {

    }

    @Test
    public void centerOfCurvatureTest()
    {

    }
}