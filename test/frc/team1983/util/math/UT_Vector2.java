package frc.team1983.util.math;

import frc.team1983.util.math.Vector2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UT_Vector2
{
    private Vector2 vec1, vec2;
    private double scalar;

    @Before
    public void setup()
    {
        vec1 = new Vector2(10, 10);
        vec2 = new Vector2(20, 20);

        scalar = 10;
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void correctlyAddsAndSubtractsVectors()
    {
        Vector2 sum = new Vector2(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY());
        Vector2 difference = new Vector2(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY());

        assertThat(Vector2.add(vec1, vec2).getX(), is(vec1.getX() + vec2.getX()));
        assertThat(Vector2.add(vec1, vec2).getY(), is(vec1.getY() + vec2.getY()));

        assertThat(Vector2.sub(vec1, vec2).getX(), is(vec1.getX() - vec2.getX()));
        assertThat(Vector2.sub(vec1, vec2).getY(), is(vec1.getY() - vec2.getY()));
    }

    @Test
    public void correctlyScalesVectors()
    {
        assertThat(Vector2.scale(vec1, scalar).getX(), is(vec1.getX() * scalar));
        assertThat(Vector2.scale(vec1, scalar).getY(), is(vec1.getY() * scalar));
    }

    @Test
    public void correctlyComputesDotProduct()
    {
        double dot = (vec1.getX() * vec2.getX()) + (vec1.getY() * vec2.getY());

        assertThat(Vector2.dot(vec1, vec2), is(dot));
    }

    @Test
    public void correctlyComputesMagnitude()
    {
        double magnitude = Math.pow(Math.pow(vec1.getX(), 2) + Math.pow(vec1.getY(), 2), 1/2);

        assertThat(vec1.getMagnitude(), is(magnitude));
    }

    @Test
    public void correctlyNormalizesVectors()
    {
        Vector2 vNorm = new Vector2(vec1.getX() / vec1.getMagnitude(), vec1.getY() / vec1.getMagnitude());

        assertThat(Vector2.getNormalized(vec1).getX(), is(vNorm.getX()));
        assertThat(Vector2.getNormalized(vec1).getY(), is(vNorm.getY()));
    }
}