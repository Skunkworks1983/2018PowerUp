package frc.team1983.util.motion.profiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class UT_CruiseProfile
{
    private CruiseProfile profile;

    @Before
    public void setup()
    {
        profile = new CruiseProfile(1000, 1, 500, 750, 0.25, 0.25);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void travelsCorrectDistance()
    {
        assertThat(profile.evaluatePosition(0), is(0.0));
        assertThat(profile.evaluatePosition(1), closeTo(1000.0, 0.1));
    }

    @Test
    public void returnsBoundsVelocitiesForBoundsInputs()
    {
        assertThat(profile.evaluateVelocity(0), is(500.0));
        assertThat(profile.evaluateVelocity(1), is(750.0));
    }

    @Test
    public void stitchesProfilesCorrectly()
    {
        CruiseProfile profile1 = new CruiseProfile(1000, 1, 500, 750, 0.25, 0.25);
        CruiseProfile profile2 = new CruiseProfile(1000, 1, 500, 750, 0.25, 0.25);

        assertThat(profile1.getDistance(), closeTo(1000.0, 0.1));
        assertThat(profile2.getDistance(), closeTo(1000.0, 0.1));

        CruiseProfile.stitch(profile1, profile2);

        assertThat(profile1.getDistance() + profile2.getDistance(), closeTo(2000.0, 0.1));
    }

    @Test
    public void stitchesListProfilesCorrectly()
    {
        List<CruiseProfile> profiles = new ArrayList<>(Arrays.asList(
                new CruiseProfile(1000, 1, 500, 750, 0.25, 0.25),
                new CruiseProfile(2000, 1, 500, 750, 0.25, 0.25),
                new CruiseProfile(3000, 1, 500, 750, 0.25, 0.25),
                new CruiseProfile(2000, 1, 500, 750, 0.25, 0.25),
                new CruiseProfile(1000, 1, 500, 750, 0.25, 0.25)
                                                                    ));

        CruiseProfile.stitch(profiles);

        double d = 0;

        for(CruiseProfile profile : profiles)
        {
            d += profile.getDistance();
        }

        assertThat(d, closeTo(1000 + 2000 + 3000 + 2000 + 1000, 0.1));
    }
}
