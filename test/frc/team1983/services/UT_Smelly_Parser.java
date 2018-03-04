package frc.team1983.services;

import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import org.apache.logging.log4j.core.Logger;
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
    public void setup()
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        initMocks(this);

        parser = new SmellyParser(dashboard, Constants.SmellyParser.SMELLY_UT_FOLDER);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void testAll()
    {
        FilenameFilter filter = (file, name) -> name.toLowerCase().endsWith(".json");

        File[] files = Constants.SmellyParser.SMELLY_UT_FOLDER.listFiles(filter);

        for(double i = 0; i < files.length; i++)
        {
            logger.info("Found json {}", files[(int) i].toString());

            when(dashboard.getNumber(Constants.SmellyParser.AUTO_PATH_KEY, 0.0)).thenReturn(i);

            parser.constructPath();
        }

    }
}
