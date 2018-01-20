import frc.team1983.commands.CommandBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UT_FakeScheduler
{
    FakeScheduler fakeScheduler;
    @Mock
    CommandBase command;

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
        assertThat(new FakeScheduler().getDone(), is(false));
    }

    @Test
    public void getDoneReturnsTrueforFinishedCommand()
    {
        assertThat(new FakeScheduler().getDone(), is(false));
    }

    @Test
    public void runFinishesCommands()
    {

    }
}
