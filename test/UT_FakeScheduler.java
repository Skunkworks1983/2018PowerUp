import frc.team1983.commands.CommandBase;
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

public class UT_FakeScheduler
{
    private FakeScheduler fakeScheduler;
    @Mock
    private CommandBase command;
    @Mock
    private CommandBase command2;

    @Before
    public void setup()
    {
        initMocks(this);
        fakeScheduler = new FakeScheduler();
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

}
