package frc.team1983.commands.drivebase;

import frc.team1983.services.OI;
import frc.team1983.subsystems.Drivebase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_TankDrive
{
    private TankDrive tankDrive;
    @Mock
    private Drivebase drivebase;
    @Mock
    private OI oi;

    double leftSpeed;
    double rightSpeed;

    @Before
    public void setup()
    {
        initMocks(this);
        tankDrive = new TankDrive(drivebase, oi);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void setLeftSpeedReturnsZeroForZeroInput()
    {
        leftSpeed = 0.0;
        assertThat(new TankDrive(drivebase, oi).getLeftSpeed(0.0), is(0.0));
    }

    @Test
    public void setRightSpeedReturnsZeroForZeroInput()
    {
        rightSpeed = 0.0;
        assertThat(new TankDrive(drivebase, oi).getRightSpeed(rightSpeed), is(0.0));
    }

    @Test
    public void setLeftSpeedReturnsOneForOneInput()
    {
        leftSpeed = 1.0;
        assertThat(new TankDrive(drivebase, oi).getLeftSpeed(leftSpeed), is(1.0));
    }

    @Test
    public void setRightSpeedReturnsOneForOneInput()
    {
        rightSpeed = 1.0;
        assertThat(new TankDrive(drivebase, oi).getRightSpeed(rightSpeed), is(1.0));
    }

    @Test
    public void setLeftSpeedReturnsNegativeOneForNegativeOneInput()
    {
        leftSpeed = -1.0;
        assertThat(new TankDrive(drivebase, oi).getLeftSpeed(leftSpeed), is(-1.0));
    }

    @Test
    public void setRightSpeedReturnsNegativeOneForNegativeOneInput()
    {
        rightSpeed = -1.0;
        assertThat(new TankDrive(drivebase, oi).getRightSpeed(rightSpeed), is(-1.0));
    }
}
