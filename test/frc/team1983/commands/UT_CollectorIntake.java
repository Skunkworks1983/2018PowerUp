package frc.team1983.commands;

import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.testutility.FakeScheduler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_CollectorIntake
{
    static
    {
        HAL.initialize(500, 0);
    }

    private CollectorIntake collectorIntake;
    private FakeScheduler fakeScheduler;
    private double left, right;
    private int leftCounter, rightCounter;
    private ArgumentCaptor<Double> leftCaptor, rightCaptor;

    @Mock
    public Collector collector;

    @Before
    public void setup()
    {
        initMocks(this);

        collectorIntake = new CollectorIntake(collector);
        fakeScheduler = new FakeScheduler();
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void neitherSwitchActiveFullIntake()
    {
        when(collector.isLeftPressed()).thenReturn(false);
        when(collector.isRightPressed()).thenReturn(false);

        collectorIntake.execute();

        verify(collector).setLeft(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
        verify(collector).setRight(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
    }

    @Test
    public void leftSwitchActiveRightFullIntakeLeftRotate()
    {
        when(collector.isLeftPressed()).thenReturn(true);
        when(collector.isRightPressed()).thenReturn(false);

        collectorIntake.execute();

        verify(collector).setLeft(Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
        verify(collector).setRight(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
    }

    @Test
    public void rightSwitchActiveLeftFullIntakeRightRotate()
    {
        when(collector.isLeftPressed()).thenReturn(false);
        when(collector.isRightPressed()).thenReturn(true);

        collectorIntake.execute();

        verify(collector).setLeft(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
        verify(collector).setRight(Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
    }

    @Test
    public void bothSwitchesActiveDoNothing()
    {
        when(collector.isLeftPressed()).thenReturn(true);
        when(collector.isRightPressed()).thenReturn(true);

        collectorIntake.execute();

        verify(collector).setLeft(0.0);
        verify(collector).setRight(0.0);
    }

    @Test
    public void logicRemainsConsistentBetweenValueChanges()
    {
        InOrder inOrder = inOrder(collector);

        fakeScheduler.add(collectorIntake);

        leftCounter = 0;
        rightCounter = 0;

        when(collector.isLeftPressed()).thenAnswer(new Answer<Boolean>()
        {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                switch(leftCounter++)
                {
                    case (0):
                    case (1):
                        return true;
                    case (2):
                    case (3):
                        return false;
                }
                return false;
            }
        });

        when(collector.isRightPressed()).thenAnswer(new Answer<Boolean>()
        {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                switch(rightCounter++)
                {
                    case (0):
                    case (2):
                        return true;
                    case (1):
                    case (3):
                        return false;
                }
                return false;
            }
        });

        fakeScheduler.run(3);

        inOrder.verify(collector).setLeft(0.0);
        inOrder.verify(collector).setRight(0.0);

        inOrder.verify(collector).setLeft(Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
        inOrder.verify(collector).setRight(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);

        inOrder.verify(collector).setLeft(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
        inOrder.verify(collector).setRight(Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);

        inOrder.verify(collector).setLeft(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
        inOrder.verify(collector).setRight(Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
    }
}
