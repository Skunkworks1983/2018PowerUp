package frc.team1983.util.math;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UT_Vector3
{
    private Vector3 vec1, vec2;
    private double scalar;

    @Before
    public void setup()
    {
        vec1 = new Vector3(10, 10, 10);
        vec2 = new Vector3(20, 20, 20);

        scalar = 10;
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void correctlyAddsAndSubtractsVectors()
    {
        Vector3 sum = new Vector3(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY(), vec1.getZ() + vec2.getZ());
        Vector3 difference = new Vector3(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY(), vec1.getZ() - vec2.getZ());

        assertThat(Vector3.add(vec1, vec2).getX(), is(vec1.getX() + vec2.getX()));
        assertThat(Vector3.add(vec1, vec2).getY(), is(vec1.getY() + vec2.getY()));
        assertThat(Vector3.add(vec1, vec2).getZ(), is(vec1.getZ() + vec2.getZ()));

        assertThat(Vector3.sub(vec1, vec2).getX(), is(vec1.getX() - vec2.getX()));
        assertThat(Vector3.sub(vec1, vec2).getY(), is(vec1.getY() - vec2.getY()));
        assertThat(Vector3.sub(vec1, vec2).getZ(), is(vec1.getZ() - vec2.getZ()));
    }

    @Test
    public void correctlyScalesVectors()
    {
        assertThat(Vector3.scale(vec1, scalar).getX(), is(vec1.getX() * scalar));
        assertThat(Vector3.scale(vec1, scalar).getY(), is(vec1.getY() * scalar));
        assertThat(Vector3.scale(vec1, scalar).getZ(), is(vec1.getZ() * scalar));
    }

    @Test
    public void correctlyComputesDotProduct()
    {
        double dot = (vec1.getX() * vec2.getX()) + (vec1.getY() * vec2.getY()) + (vec1.getZ() * vec2.getZ());

        assertThat(Vector3.dot(vec1, vec2), is(dot));
    }

    @Test
    public void correctlyComputesMagnitude()
    {
        double magnitude = Math.pow(Math.pow(vec1.getX(), 2) + Math.pow(vec1.getY(), 2) + Math.pow(vec1.getZ(), 2), 1/2);

        assertThat(vec1.getMagnitude(), is(magnitude));
    }

    @Test
    public void correctlyNormalizesVectors()
    {
        Vector3 vNorm = new Vector3(vec1.getX() / vec1.getMagnitude(), vec1.getY() / vec1.getMagnitude(), vec1.getZ() / vec1.getMagnitude());

        assertThat(Vector3.getNormalized(vec1).getX(), is(vNorm.getX()));
        assertThat(Vector3.getNormalized(vec1).getY(), is(vNorm.getY()));
        assertThat(Vector3.getNormalized(vec1).getZ(), is(vNorm.getZ()));
    }
}