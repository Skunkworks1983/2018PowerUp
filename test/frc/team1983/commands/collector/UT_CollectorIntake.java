package frc.team1983.commands.collector;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.hal.HAL;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.testutility.FakeScheduler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

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
    private int leftUTCounter, rightUTCounter, counter;

    @Mock
    private Collector collector;

    @Before
    public void setup()
    {
        initMocks(this);

        collectorIntake = new CollectorIntake(collector, 0.0); //TODO figure out why this was wrong
        fakeScheduler = new FakeScheduler();
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void neitherSwitchActiveFullIntake()
    {
        when(collector.isLeftSwitchDown()).thenReturn(false);
        when(collector.isRightSwitchDown()).thenReturn(false);

        collectorIntake.execute();

        verify(collector).setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
        verify(collector).setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
    }

    @Test
    public void leftSwitchActiveRightFullIntakeLeftRotate()
    {
        when(collector.isLeftSwitchDown()).thenReturn(true);
        when(collector.isRightSwitchDown()).thenReturn(false);

        collectorIntake.execute();

        verify(collector).setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
        verify(collector).setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
    }

    @Test
    public void rightSwitchActiveLeftFullIntakeRightRotate()
    {
        when(collector.isLeftSwitchDown()).thenReturn(false);
        when(collector.isRightSwitchDown()).thenReturn(true);

        collectorIntake.execute();

        verify(collector).setLeft(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED);
        verify(collector).setRight(ControlMode.PercentOutput, Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED);
    }

    @Test
    public void bothSwitchesActiveDoNothing()
    {
        when(collector.isLeftSwitchDown()).thenReturn(true);
        when(collector.isRightSwitchDown()).thenReturn(true);

        collectorIntake.execute();

        verify(collector).setLeft(ControlMode.PercentOutput, 0.0);
        verify(collector).setRight(ControlMode.PercentOutput, 0.0);
    }

    @Test
    public void logicRemainsConsistentBetweenValueChanges()
    {
        InOrder inOrder = inOrder(collector);

        fakeScheduler.add(collectorIntake);

        counter = 0;

        when(collector.isLeftSwitchDown()).thenAnswer((Answer<Boolean>) invocationOnMock ->
                counter == 0 || counter == Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME + 1);

        when(collector.isRightSwitchDown()).thenAnswer((Answer<Boolean>) invocationOnMock -> {
            boolean answer = counter == 0 || counter == Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME * 2 + 2;
            counter++;
            return answer;
        });


        List<double[]> speeds = new ArrayList<>();
        speeds.add(new double[]{0.0, 0.0});
        speeds.add(new double[]{Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED,
                Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED});
        speeds.add(new double[]{Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED,
                Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED});
        speeds.add(new double[]{Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED,
                Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED});

        fakeScheduler.run(Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME * speeds.size()
                                  + speeds.size());

        for(double[] thisSpeed : speeds)
        {
            for(int i = 0; i < Constants.MotorSetpoints.COLLECTOR_SWITCH_DEBOUNCE_TIME; i++)
            {
                inOrder.verify(collector).setLeft(ControlMode.PercentOutput, thisSpeed[0]);
                inOrder.verify(collector).setRight(ControlMode.PercentOutput, thisSpeed[1]);
            }
        }
    }
}
