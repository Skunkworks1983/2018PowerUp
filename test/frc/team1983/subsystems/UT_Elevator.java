package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.Robot;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@PowerMockIgnore("javax.management.*")
@PrepareForTest(Robot.class)
@RunWith(PowerMockRunner.class)
public class UT_Elevator
{
    static
    {
        HAL.initialize(500, 0);
    }

    private Logger logger;

    @Mock
    private Elevator elevator;

    @Mock
    private Robot robot;

    @Before
    public void setup()
    {
        initMocks(this);

        logger = LoggerFactory.createNewLogger(this.getClass());

        Mockito.doNothing().when(elevator).set(isA(ControlMode.class), isA(double.class));
        Mockito.doNothing().when(elevator).setSensorPhase(isA(boolean.class));

        Mockito.doCallRealMethod().when(elevator).periodic();

        PowerMockito.spy(Robot.class);
        try
        {
            when(Robot.getInstance()).thenReturn(robot);
        }
        catch(Exception e)
        {
            logger.warn("Error in setup", e);
        }
    }

    @Test
    public void elevatorDoesNotGoWhenCollectorUpAndSetpointIsHigh()
    {
        when(robot.isCollectorUp()).thenReturn(true);
        when(elevator.getSetpoint()).thenReturn(Constants.ElevatorSetpoints.TOP);
        elevator.periodic();

        verify(elevator).set(ControlMode.Position, Constants.ElevatorSetpoints.TRAVEL.getEncoderTicks());
    }

    @Test
    public void elevatorDoesGoWhenCollectorDownAndSetpointIsHigh()
    {
        when(robot.isCollectorUp()).thenReturn(false);
        when(elevator.getSetpoint()).thenReturn(Constants.ElevatorSetpoints.TOP);

        elevator.periodic();
        verify(elevator).set(ControlMode.Position, Constants.ElevatorSetpoints.TOP.getEncoderTicks());
    }

    @Test
    public void elevatorDoesGoWhenCollectorUpAndSetpointIsLow()
    {
        when(robot.isCollectorUp()).thenReturn(true);
        when(elevator.getSetpoint()).thenReturn(Constants.ElevatorSetpoints.BOTTOM);

        elevator.periodic();
        verify(elevator).set(ControlMode.Position, Constants.ElevatorSetpoints.BOTTOM.getEncoderTicks());
    }

    @Test
    public void elevatorDoesGoWhenCollectorDownAndSetpointIsLow()
    {
        when(robot.isCollectorUp()).thenReturn(false);
        when(elevator.getSetpoint()).thenReturn(Constants.ElevatorSetpoints.BOTTOM);

        elevator.periodic();
        verify(elevator).set(ControlMode.Position, Constants.ElevatorSetpoints.BOTTOM.getEncoderTicks());
    }
}
