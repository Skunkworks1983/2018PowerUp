package frc.team1983.testutility;

import frc.team1983.commands.CommandBase;
import frc.team1983.commands.CommandGroupWrapper;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FakeScheduler
{
    private List<CommandBase> commandQueue = new LinkedList<>();
    private Set<CommandBase> orderFinished = new LinkedHashSet<>();
    private List<CommandBase> commandGroupRemoval = new LinkedList<>();
    private List<CommandBase> commandBaseRemoval = new LinkedList<>();
    private int counter = 0;
    private int loopCutOff = 1; //loop number

    private Map<CommandGroupWrapper, Integer> commandGroupMap = new LinkedHashMap<>();

    public void run()
    {
        run(-1);
    }

    public void run(int loopCutOff)
    {
        this.loopCutOff = loopCutOff;
        for(CommandGroupWrapper c : commandGroupMap.keySet())
        {
            commandGroupMap.get(c);
            for(CommandBase command : c.getCommandGroupWrapperList().get(0))
            {
                command.initialize();
                command.execute();
            }
        }
        for(CommandBase c : commandQueue)
        {
            c.initialize();
            c.execute();
        }
        while(!getDone())
        {
            counter++;
            for(CommandBase c : commandQueue)
            {
                if(!c.isFinished())
                {
                    c.execute();
                }
                else
                {
                    c.end();
                    commandBaseRemoval.add(c);
                    orderFinished.add(c);
                }

            }
            for(CommandBase finished : commandBaseRemoval)
            {
                commandQueue.remove(finished);
            }
            for(CommandGroupWrapper c : commandGroupMap.keySet())
            {
                for(CommandBase command : c.getCommandGroupWrapperList().get(commandGroupMap.get(c)))
                {

                    if(!command.isFinished())
                    {
                        command.execute();
                    }
                    else
                    {
                        command.end();
                        commandGroupRemoval.add(command);
                        orderFinished.add(command);
                    }
                }
                for(CommandBase finished : commandGroupRemoval)
                {
                    c.getCommandGroupWrapperList().get(commandGroupMap.get(c)).remove(finished);
                }
                if(c.getCommandGroupWrapperList().get(commandGroupMap.get(c)).size() == 0)
                {
                    if(commandGroupMap.get(c) < c.getCommandGroupWrapperList().size() - 1)
                    {
                        commandGroupMap.put(c, commandGroupMap.get(c) + 1);
                        for(CommandBase d : c.getCommandGroupWrapperList().get(commandGroupMap.get(c)))
                        {
                            d.initialize();
                            d.execute();
                        }

                    }
                }
            }
        }

    }

    public boolean getDone()
    {
        if(loopCutOff == -1 || counter < loopCutOff)
        {
            for(CommandGroupWrapper c : commandGroupMap.keySet())
            {
                if(commandGroupMap.get(c) == c.getCommandGroupWrapperList().size() - 1)
                {
                    for(CommandBase command : c.getCommandGroupWrapperList().get(c.getCommandGroupWrapperList().size() - 1))
                    {
                        if(!command.isFinished())
                        {
                            return false;
                        }
                        else
                        {
                            orderFinished.add(command);
                        }
                    }
                }
                else
                {
                    return false;
                }
            }
            for(CommandBase c : commandQueue)
            {
                if(!c.isFinished())
                {
                    return false;
                }
                else
                {
                    orderFinished.add(c);
                    commandBaseRemoval.add(c);

                }
            }

        }
        return true;
    }

    public void add(CommandBase command)
    {
        if(command != null)
        {
            commandQueue.add(command);
        }
    }

    public void add(CommandGroupWrapper commandGroupWrapper)
    {
        commandGroupMap.put(commandGroupWrapper, 0);
    }

    public void remove(CommandBase command)
    {
        if(command == null)
        {
            commandQueue.remove(command);
        }
    }

    public List<CommandBase> getOrderFinished()
    {
        return new LinkedList<>(orderFinished);
    }

}
