package frc.team1983.services;

import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.services.SmellyParser.SmellyParser;
import frc.team1983.settings.Constants;
import frc.team1983.util.path.PathTanline;
import org.hamcrest.number.IsCloseTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_Smelly_Parser
{
    static
    {
        HAL.initialize(500, 0);
    }

    private SmellyParser parser;

    @Mock
    private DashboardWrapper dashboard;

    @Before
    public void setup() throws IOException
    {
        initMocks(this);

        parser = new SmellyParser(dashboard, Constants.SmellyParser.SMELLYUTFOLDER);

        parser.constructPath();
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void test()
    {
        assertThat(parser.getPath().getComponent(0).getType(), is("tanline"));
        assertThat(((PathTanline) parser.getPath().getComponent(0)).getDistance(),
                   new IsCloseTo(107.99999999999999, 0.000001));
        assertThat((parser.getPath().getComponent(0)).getTime(),
                   new IsCloseTo(2.0, 0.00001));
    }
}
