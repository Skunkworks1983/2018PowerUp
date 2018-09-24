package frc.team1983.commands.drivebase;

import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class UT_DriveProfile
{
    private DriveProfile profile;

    @Mock
    private Drivebase drivebase;

    @Before
    public void setup()
    {
        initMocks(this);
        profile = new DriveFeet(drivebase, 6, 1);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void t()
    {

    }
}
