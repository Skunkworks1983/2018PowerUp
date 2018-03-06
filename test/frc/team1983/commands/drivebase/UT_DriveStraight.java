package frc.team1983.commands.drivebase;

import edu.wpi.first.wpilibj.HLUsageReporting;
import edu.wpi.first.wpilibj.Timer;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.sensors.Gyro;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.testutility.FakeScheduler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_DriveStraight
{
    private DriveStraight driveStraight;
    private FakeScheduler fakeScheduler;
    @Mock
    private StatefulDashboard dashboard;
    //private PidControllerWrapper driveStraightPid;

    @Mock
    private Gyro gyro;
    @Mock
    private Drivebase drivebase;
    @Mock
    private GyroPidInput gyroPidInput;
    @Mock
    private Timer.StaticInterface timerStaticInterface;
    @Mock
    private Timer.Interface timerInterface;
    @Mock
    private HLUsageReporting.Interface hlUsageReporting;

    @Before
    public void setup()
    {
        initMocks(this);
        when(timerStaticInterface.newTimer()).thenReturn(timerInterface);
        when(timerStaticInterface.getFPGATimestamp()).then(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return System.currentTimeMillis() / 1000.;
            }
        });
        when(timerInterface.get()).then(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return System.currentTimeMillis() / 1000.;
            }
        });

        Timer.SetImplementation(timerStaticInterface);
        HLUsageReporting.SetImplementation(hlUsageReporting);
        fakeScheduler = new FakeScheduler();
        when(drivebase.getGyro()).thenReturn(gyro);
        driveStraight = new DriveStraight(drivebase, dashboard, 5, 5);
    }

    @After
    public void teardown()
    {
        driveStraight.end();
    }

    @Test
    public void driveStraightUsesGyroWhenGyroIsWorking() throws Exception
    {
        when(gyro.isDead()).thenReturn(false);
        fakeScheduler.add(driveStraight);
        fakeScheduler.run(1);
        Thread.sleep(100); //we're waiting for pid to go
        verify(gyro, atLeastOnce()).getAngle();
    }

    @Test
    public void driveStraightUsesEncoderWhenGyroIsDead() throws Exception
    {
        when(gyro.isDead()).thenReturn(true);
        fakeScheduler.add(driveStraight);
        fakeScheduler.run(1);
        Thread.sleep(100);
        verify(drivebase, atLeastOnce()).getLeftDist();
    }

    @Test
    public void driveStraightContinuesIfDistanceIsNotMet()
    {
        when(drivebase.getLeftDist()).thenReturn(2.0);
        when(drivebase.getRightDist()).thenReturn(2.1);
        fakeScheduler.add(driveStraight);
        fakeScheduler.run(10);
        driveStraight.isFinished();
        assertThat(driveStraight.isFinished(), is(false));
    }

    @Test
    public void driveStraightEndsWhenDistanceIsMet()
    {
        //in real world the distance will increase as time goes on, answer mimics this
        //return 0 the first time it is called because we want initial position
        //this test assumes gyro is dead because we have better access to encoderPidSource
        when(gyro.isDead()).thenReturn(true);
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        when(drivebase.getLeftDist()).then(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                //
                if(counter.get() == 0)
                {
                    counter.set(counter.get() + 1);
                    return 0.;
                }
                else
                {
                    counter.set(counter.get() + 1);
                    return counter.get().doubleValue();
                }
            }
        });
        when(drivebase.getRightDist()).then(new Answer<Double>()
        {
            @Override
            public Double answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                if(counter.get() == 0)
                {
                    counter.set(counter.get() + 1);
                    return 0.;
                }
                else
                {
                    counter.set(counter.get() + 1);
                    return counter.get().doubleValue();
                }
            }
        });

        fakeScheduler.add(driveStraight);
        fakeScheduler.run(15);
        assertThat(fakeScheduler.getOrderFinished().get(0), is(driveStraight));
    }
}
