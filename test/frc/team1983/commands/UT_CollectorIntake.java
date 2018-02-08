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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.booleanThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class UT_CollectorIntake
{
    static
    {
        HAL.initialize(500, 0);
    }

    private CollectorIntake collectorIntake;
    private FakeScheduler fakeScheduler;
    private int leftUTCounter, rightUTCounter, counter;

    @Mock
    private Collector collector;

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

        counter = 0;

        when(collector.isLeftPressed()).thenAnswer((Answer<Boolean>) invocationOnMock -> counter == 0 || counter == 26);

        when(collector.isRightPressed()).thenAnswer((Answer<Boolean>) invocationOnMock -> {
            boolean answer = counter == 0 || counter == 52;
            if(answer || counter == 26) {System.out.println(counter);}
            counter++;
            return answer;
        });

        fakeScheduler.run(103);

        List<double[]> speeds = new ArrayList<>();
        speeds.add(new double[] {0.0, 0.0});
        speeds.add(new double[] {Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED,
                Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED});
        speeds.add(new double[] {Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED,
                Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED});
        speeds.add(new double[] {Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED,
                Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED});

        for(double[] thisSpeed : speeds)
        {
            for(int i = 0; i < 26; i++)
            {
                inOrder.verify(collector).setLeft(thisSpeed[0]);
                inOrder.verify(collector).setRight(thisSpeed[1]);
            }
        }
    }
}
