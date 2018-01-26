package frc.team1983.services;

import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_Stateful_Dashboard
{
    static {
        HAL.initialize(500, 0);
    }

    private StatefulDashboard dashboard;

    @Rule
    private TemporaryFolder testFolder = new TemporaryFolder();

    @Mock
    private DashboardWrapper smartdashboardWrapper;

    @Before
    public void setup() throws IOException
    {
        initMocks(this);

        File tempFile = testFolder.newFile("testDashboardValues.txt");

        dashboard = new StatefulDashboard(smartdashboardWrapper, tempFile);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void populateAndStoreAgree()
    {
        dashboard.add("test", );

    }
}
