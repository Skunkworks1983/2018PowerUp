package frc.team1983.commands.drivebase;

import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_TankDrive
{
    private TankDrive tankDrive;
    @Mock
    private Drivebase drivebase;
    @Mock
    private OI oi;


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
    public void tankDriveSetsBothLeftAndRightToZero()
    {
        when(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(0.0);
        when(oi.getAxis(Constants.OIMap.Port.RIGHT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(0.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setLeft(0.0);
        verify(drivebase, times(1)).setRight(0.0);
    }

    @Test
    public void tankDriveSetsBothLeftAndRightToOne()
    {
        when(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(1.0);
        when(oi.getAxis(Constants.OIMap.Port.RIGHT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(1.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setLeft(1.0);
        verify(drivebase, times(1)).setRight(1.0);
    }

    @Test
    public void tankDriveSetsBothLeftAndRightToNegativeOne()
    {
        when(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(-1.0);
        when(oi.getAxis(Constants.OIMap.Port.RIGHT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(-1.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setLeft(-1.0);
        verify(drivebase, times(1)).setRight(-1.0);
    }

    @Test
    public void tankDriveSetsLeftAndRightToDifferentValues()
    {
        when(oi.getAxis(Constants.OIMap.Port.LEFT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(-1.0);
        when(oi.getAxis(Constants.OIMap.Port.RIGHT_JOY, Constants.OIMap.JoyAxes.Y)).thenReturn(1.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setRight(1.0);
        verify(drivebase, times(1)).setLeft(-1.0);
    }

}
