package frc.team1983.services;

import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.util.path.PathTanline;
import org.apache.logging.log4j.core.Logger;
import org.hamcrest.number.IsCloseTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_Smelly_Parser
{
    static
    {
        HAL.initialize(500, 0);
    }

    private SmellyParser parser;
    private Logger logger;

    @Mock
    private DashboardWrapper dashboard;

    @Before
    public void setup() throws IOException
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        initMocks(this);

        parser = new SmellyParser(dashboard, Constants.SmellyParser.SMELLYUTFOLDER);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void testAll()
    {
        FilenameFilter filter = (file, name) -> name.toLowerCase().endsWith(".json");

        File[] files = Constants.SmellyParser.SMELLYUTFOLDER.listFiles(filter);

        for(double i = 0; i < files.length; i++)
        {
            logger.info("Found json {}", files[(int) i].toString());

            when(dashboard.getNumber(Constants.SmellyParser.AUTOPATHKEY, 0.0)).thenReturn(i);

            parser.constructPath();
        }

    }

    @Test
    public void testValues()
    {
        when(dashboard.getNumber(Constants.SmellyParser.AUTOPATHKEY, 0.0)).thenReturn(4.0);

        assertThat(parser.getPath().getComponent(0).getType(), is("tanline"));
        assertThat(((PathTanline) parser.getPath().getComponent(0)).getDistance(),
                   new IsCloseTo(107.99999999999999, 0.000001));
        assertThat((parser.getPath().getComponent(0)).getTime(),
                   new IsCloseTo(2.0, 0.00001));
    }
}
