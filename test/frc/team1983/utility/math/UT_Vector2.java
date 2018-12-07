package frc.team1983.utility.math;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public class UT_Vector2
{
    @Test
    public void additionTest()
    {
        assertThat(Vector2.equals(
                Vector2.add(new Vector2(1, 2), new Vector2(3, 4)),
                new Vector2(4, 6)),
                is(true));

        assertThat(Vector2.equals(
                Vector2.add(new Vector2(-5, 6), new Vector2(3, -4)),
                new Vector2(-2, 2)),
                is(true));
    }

    @Test
    public void subtractionTest()
    {
        assertThat(Vector2.equals(
                Vector2.sub(new Vector2(4, 7), new Vector2(1, 3)),
                new Vector2(3, 4)),
                is(true));

        assertThat(Vector2.equals(
                Vector2.sub(new Vector2(9, -3), new Vector2(-5, 9)),
                new Vector2(14, -12)),
                is(true));
    }

    @Test
    public void scalingTest()
    {
        assertThat(Vector2.equals(
                Vector2.scale(new Vector2(4, 6), 1.5),
                new Vector2(6, 9)),
                is(true));

        assertThat(Vector2.equals(
                Vector2.scale(new Vector2(5, 2), -1),
                new Vector2(-5, -2)),
                is(true));
    }

    @Test
    public void magnitudeTest()
    {
        assertThat(new Vector2(0, 10).getMagnitude(), is(10.0));
        assertThat(new Vector2(0, -10).getMagnitude(), is(10.0));
        assertThat(new Vector2(3, 4).getMagnitude(), is(5.0));
    }

    @Test
    public void normalizingTest()
    {
        assertThat(Vector2.equals(new Vector2(10, 10).getNormalized(), new Vector2(1, 1).getNormalized()), is(true));
        assertThat(Vector2.equals(new Vector2(-10, -10).getNormalized(), new Vector2(-1, -1).getNormalized()), is(true));
        assertThat(new Vector2(10, 10).getNormalized().getMagnitude(), closeTo(1.0, 0.01));
    }

    @Test
    public void distanceTest()
    {
        assertThat(Vector2.getDistance(new Vector2(0, 0), new Vector2(10, 0)), is(10.0));
        assertThat(Vector2.getDistance(new Vector2(0, 0), new Vector2(1, 0)), is(1.0));
        assertThat(Vector2.getDistance(new Vector2(0, 0), new Vector2(1, 1)), is(Math.sqrt(2)));
        assertThat(Vector2.getDistance(new Vector2(1, 1), new Vector2(2, 2)), is(Math.sqrt(2)));
        assertThat(Vector2.getDistance(new Vector2(-1, 0), new Vector2(-10, 0)), is(9.0));
    }

    @Test
    public void twistingTest()
    {
        assertThat(Vector2.equals(Vector2.twist(new Vector2(1, 0), new Vector2(0, 0), 90), new Vector2(0, 1)), is(true));
        assertThat(Vector2.equals(Vector2.twist(new Vector2(0, 1), new Vector2(0, 0), 90), new Vector2(-1, 0)), is(true));
        assertThat(Vector2.equals(Vector2.twist(new Vector2(-1, 0), new Vector2(0, 0), 90), new Vector2(0, -1)), is(true));
        assertThat(Vector2.equals(Vector2.twist(new Vector2(0, 0), new Vector2(1, 0), -90), new Vector2(1, 1)), is(true));
    }
}