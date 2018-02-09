package frc.team1983.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.LinkedList;
import java.util.List;

public class CommandGroupWrapper
{
    private CommandGroup commandGroup;
    private List<CommandBase> addSequentialList = new LinkedList<>();
    private List<List<CommandBase>> commandGroupWrapperList = new LinkedList<>();

    public CommandGroupWrapper(CommandGroup commandGroup)
    {
        this.commandGroup = commandGroup;
    }

    public final synchronized void addSequential(CommandBase command) //adds to List of List of commands
    {
            addSequentialList = new LinkedList<>();
            addSequentialList.add(command);
            commandGroupWrapperList.add(addSequentialList);
            if(commandGroup != null)
            {
                commandGroup.addSequential(command);
            }
    }

    public final synchronized void addSequential(CommandBase command, double timeout)
    {
        addSequentialList = new LinkedList<>();
        addSequentialList.add(command);
        commandGroupWrapperList.add(addSequentialList);
        if(commandGroup != null)
        {
            commandGroup.addSequential(command, timeout);
        }
    }

    public final synchronized void addParallel(CommandBase command) //adds to List of commands
    {
        addSequentialList.add(command);
        int size = commandGroupWrapperList.size();
        commandGroupWrapperList.add(size, addSequentialList);
        if(commandGroup != null)
        {
            commandGroup.addParallel(command);
        }
    }

    public final synchronized void addParallel(CommandBase command, double timeout)
    {
        addSequentialList.add(command);
        int size = commandGroupWrapperList.size();
        commandGroupWrapperList.add(size, addSequentialList);
        if(commandGroup != null)
        {
            commandGroup.addParallel(command, timeout);
        }
    }

    public List<List<CommandBase>> getCommandGroupWrapperList()
    {
        return new LinkedList<>(commandGroupWrapperList);
    }

    public CommandGroup getCommandGroup()
    {
        return commandGroup;
    }
}