package frc.team1983.services;

import edu.wpi.first.wpilibj.hal.HAL;
import org.junit.Before;

public class UT_DashboardWrapper
{
    static {
        HAL.initialize(500, 0);
    }

    private DashboardWrapper dashboard;

    @Before
    public void setup()
    {
        dashboard = new DashboardWrapper();
    }
}
