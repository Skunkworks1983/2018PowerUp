package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommandGroup.class)
public class UT_CommandGroupWrapper
{
    private CommandGroupWrapper commandGroupWrapper;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CommandBase command0;

    @Mock
    private CommandBase command1;

    @Mock
    private CommandBase command2;

    private CommandGroup commandGroup;

    @Before
    public void setup()
    {
        commandGroup = PowerMockito.mock(CommandGroup.class);
        initMocks(this);
        doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                return null;
            }
        }).when(commandGroup).addSequential(any());
        commandGroupWrapper = new CommandGroupWrapper(commandGroup);
    }

    @After
    public void teardown()
    {

    }

    @Test
    public void addSequentialAddsListOfCommandToListOfListOfCommands()
    {
        commandGroupWrapper.addSequential(command0);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(1));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
    }

    @Test
    public void addSequentialAddsListOfCommandsToListOfListOfCommandsWithTimeout()
    {
        commandGroupWrapper.addSequential(command0, 3);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(1));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
    }

    @Test
    public void addParallelAddsCommandToListOfCommands()
    {
        commandGroupWrapper.addSequential(command0);
        commandGroupWrapper.addParallel(command1);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(1));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(1), is(command1));
    }

    @Test
    public void addParallelAddsCommandToListOfCommandsWithTimeout()
    {
        commandGroupWrapper.addSequential(command0, 3);
        commandGroupWrapper.addParallel(command1, 4);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(1));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(1), is(command1));
    }

    @Test
    public void multipleAddSequentialCreatesMultipleLists()
    {
        commandGroupWrapper.addSequential(command0);
        commandGroupWrapper.addSequential(command1);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(2));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(1).get(0), is(command1));
    }

    @Test
    public void multipleAddSequentialCreatesMultipleListsWithTimeout()
    {
        commandGroupWrapper.addSequential(command0, 4);
        commandGroupWrapper.addSequential(command1, 3);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(2));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(1).get(0), is(command1));
    }

    @Test
    public void addParallelCanBeCalledFirst()
    {
        commandGroupWrapper.addParallel(command0);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(1));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
    }

    @Test
    public void addParallelCanBeCalledFirstWithTimeout()
    {
        commandGroupWrapper.addParallel(command0, 3);
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().size(), is(1));
        assertThat(commandGroupWrapper.getCommandGroupWrapperList().get(0).get(0), is(command0));
    }

    @Test
    public void commandGroupWrapperUsesCommandGroup()
    {
        commandGroupWrapper.addSequential(command0);
        commandGroupWrapper.addParallel(command1);
        commandGroupWrapper.addSequential(command2, 3);
        commandGroupWrapper.addParallel(command0, 4);
        verify(commandGroup, times(1)).addSequential(command0);
        verify(commandGroup, times(1)).addParallel(command1);
        verify(commandGroup, times(1)).addSequential(command2, 3);
        verify(commandGroup, times(1)).addParallel(command0, 4);
    }

}