package frc.team1983.services;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.services.parser.SmellyParser;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.PowerMockitoCore;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FilenameFilter;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.configuration.ConfigurationType.PowerMock;

@PowerMockIgnore("javax.management.*")
@PrepareForTest({Robot.class,Timer.class})
@RunWith(PowerMockRunner.class)
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

    @Mock
    private Robot robot;

    @Mock
    private Drivebase drivebase;

    @Mock
    private Collector collector;

    @Mock
    private Timer timer;

    @Mock
    private Scheduler scheduler;

    @Before
    public void setup()
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        initMocks(this);

        //parser = new SmellyParser(dashboard, Constants.SmellyParser.SMELLY_UT_FOLDER);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void testAll()
    {
        PowerMockito.spy(Robot.class);
        PowerMockito.spy(Timer.class);
        PowerMockito.spy(Command.class);
        try
        {
            PowerMockito.mockStatic(Timer.class);
            when(timer.get()).thenReturn(0.0);

            PowerMockito.doReturn(robot).when(Robot.class, "getInstance");
            when(robot.getDrivebase()).thenReturn(drivebase);
            when(robot.getCollector()).thenReturn(collector);

            //doNothing().when(Command.class, "requires", drivebase);



            FilenameFilter filter = (file, name) -> name.toLowerCase().endsWith(".json");

            /*File[] files = Constants.SmellyParser.SMELLY_UT_FOLDER.listFiles(filter);

            for(int i = 0; i < files.length; i++)
            {
                logger.info("Found json {}", files[i].toString());

                parser.constructPath(files[i]);
            }
            */
        }
        catch(Exception e)
        {
            logger.error("this is jank as fuck", e);
        }
    }
}
