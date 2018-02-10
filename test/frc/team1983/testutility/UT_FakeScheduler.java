package frc.team1983.testutility;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.CommandGroupWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_FakeScheduler
{
    private FakeScheduler fakeScheduler;
    @Mock
    private CommandBase command;
    @Mock
    private CommandBase command1;
    @Mock
    private CommandBase command2;
    private CommandGroup commandGroup;
    private CommandGroupWrapper commandGroupWrapper;
    @Before
    public void setup()
    {
        initMocks(this);
        fakeScheduler = new FakeScheduler();
        commandGroupWrapper = new CommandGroupWrapper(null);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void getDoneReturnsFalseForUnfinishedCommand()
    {
        when(command.isFinished()).thenReturn(false);
        fakeScheduler.add(command);
        assertThat(fakeScheduler.getDone(), is(false));
    }

    @Test
    public void getDoneReturnsTrueForFinishedCommand()
    {
        when(command.isFinished()).thenReturn(true);
        fakeScheduler.add(command);
        assertThat(fakeScheduler.getDone(), is(true));
    }

    @Test
    public void getDoneReturnsFalseWhenAnyCommandIsUnfinished()
    {
        when(command.isFinished()).thenReturn(true);
        when(command2.isFinished()).thenReturn(false);
        fakeScheduler.add(command);
        fakeScheduler.add(command2);
        assertThat(fakeScheduler.getDone(), is(false));
    }

    @Test
    public void runExecutesMultipleCommands()
    {
        when(command.isFinished()).thenReturn(true);
        when(command2.isFinished()).thenReturn(false, false, true);
        fakeScheduler.add(command);
        fakeScheduler.add(command2);
        fakeScheduler.run();
        verify(command, times(1)).initialize();
        verify(command, times(1)).execute();
        verify(command2, times(1)).initialize();
        verify(command2, times(2)).execute();
    }

    @Test
    public void runExecutesACommand()
    {
        when(command.isFinished()).thenReturn(true);
        fakeScheduler.add(command);
        fakeScheduler.run();
        verify(command, times(1)).initialize();
        verify(command, times(1)).execute();
    }

    @Test
    public void runExecutesCommandGroup()
    {
        when(command.isFinished()).thenReturn(true);
        when(command1.isFinished()).thenReturn(true);
        when(command2.isFinished()).thenReturn(true);
        commandGroupWrapper.addSequential(command);
        commandGroupWrapper.addParallel(command1);
        commandGroupWrapper.addSequential(command2);
        fakeScheduler.add(commandGroupWrapper);
        fakeScheduler.run();
        verify(command, times(1)).initialize();
        verify(command, times(1)).execute();
        verify(command1, times(1)).initialize();
        verify(command1, times(1)).execute();
        verify(command2, times(1)).initialize();
        verify(command2, times(1)).execute();
    }


    @Test
    public void getDoneStopsWhenMeetsLoopCutOff()
    {
        when(command.isFinished()).thenReturn(false);
        fakeScheduler.add(command);
        fakeScheduler.run(5);
        verify(command, times(1)).initialize();
        verify(command, times(6)).execute();
    }

    @Test
    public void getDoneRunsForeverWhenCounterIsInfinite()
    {
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        when(command.isFinished()).thenAnswer(new Answer<Boolean>()
        {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                counter.set(counter.get() + 1);

                return counter.get() >= 6000;
            }
        });
        fakeScheduler.add(command);
        fakeScheduler.run();
        verify(command, times(1)).initialize();
        verify(command, times(3000)).execute();
    }

    @Test
    public void orderFinishedReturnsRightOrderForCommands()
    {
        when(command.isFinished()).thenReturn(true);
        when(command1.isFinished()).thenReturn(false, true);
        when(command2.isFinished()).thenReturn(false, false, true);
        fakeScheduler.add(command);
        fakeScheduler.add(command1);
        fakeScheduler.add(command2);
        fakeScheduler.run();
        assertThat(fakeScheduler.getOrderFinished().get(0), is(command));
        assertThat(fakeScheduler.getOrderFinished().get(1), is(command1));
        assertThat(fakeScheduler.getOrderFinished().get(2), is(command2));
    }

    @Test
    public void orderFinishedReturnsRightOrderForCommandGroup()
    {
        when(command.isFinished()).thenReturn(false,true);
        when(command1.isFinished()).thenReturn(true);
        when(command2.isFinished()).thenReturn(true);
        commandGroupWrapper.addSequential(command);
        commandGroupWrapper.addParallel(command1);
        commandGroupWrapper.addSequential(command2);
        fakeScheduler.add(commandGroupWrapper);
        fakeScheduler.run();
        assertThat(fakeScheduler.getOrderFinished().get(0), is(command1));
        assertThat(fakeScheduler.getOrderFinished().get(1), is(command));
        assertThat(fakeScheduler.getOrderFinished().get(2), is(command2));
    }
}
