package frc.team1983.services;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.utility.sensors.Pigeon;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class UT_StateEstimator
{
    @Mock
    private Drivebase drivebase;

    @Mock
    private Pigeon pigeon;

    private StateEstimator estimator;

    @Before
    public void setup()
    {
        initMocks(this);
    }

    @Test
    public void localizationTest()
    {

    }
}