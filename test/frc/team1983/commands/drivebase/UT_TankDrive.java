package frc.team1983.commands.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_TankDrive
{
    private RunTankDrive tankDrive;
    @Mock
    private Drivebase drivebase;
    @Mock
    private OI oi;


    @Before
    public void setup()
    {
        initMocks(this);
        tankDrive = new RunTankDrive(drivebase, oi);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void tankDriveSetsBothLeftAndRightToZero()
    {
        when(oi.getAxis(Constants.OIMap.Joystick.LEFT, 1)).thenReturn(0.0);
        when(oi.getAxis(Constants.OIMap.Joystick.RIGHT, 1)).thenReturn(0.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setLeft(ControlMode.PercentOutput, 0.0);
        verify(drivebase, times(1)).setRight(ControlMode.PercentOutput, 0.0);
    }

    @Test
    public void tankDriveSetsBothLeftAndRightToOne()
    {
        when(oi.getAxis(Constants.OIMap.Joystick.LEFT, 1)).thenReturn(1.0);
        when(oi.getAxis(Constants.OIMap.Joystick.RIGHT, 1)).thenReturn(1.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setLeft(ControlMode.PercentOutput, 1.0);
        verify(drivebase, times(1)).setRight(ControlMode.PercentOutput, 1.0);
    }

    @Test
    public void tankDriveSetsBothLeftAndRightToNegativeOne()
    {
        when(oi.getAxis(Constants.OIMap.Joystick.LEFT, 1)).thenReturn(-1.0);
        when(oi.getAxis(Constants.OIMap.Joystick.RIGHT, 1)).thenReturn(-1.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setLeft(ControlMode.PercentOutput, -1.0);
        verify(drivebase, times(1)).setRight(ControlMode.PercentOutput, -1.0);
    }

    @Test
    public void tankDriveSetsLeftAndRightToDifferentValues()
    {
        when(oi.getAxis(Constants.OIMap.Joystick.LEFT, 1)).thenReturn(-1.0);
        when(oi.getAxis(Constants.OIMap.Joystick.RIGHT, 1)).thenReturn(1.0);
        tankDrive.execute();
        verify(drivebase, times(1)).setRight(ControlMode.PercentOutput, 1.0);
        verify(drivebase, times(1)).setLeft(ControlMode.PercentOutput, -1.0);
    }

}
