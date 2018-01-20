package frc.team1983.testutility;

import frc.team1983.commands.CommandBase;

import java.util.LinkedList;
import java.util.List;

public class FakeScheduler
{
    private List<CommandBase> commandQueue = new LinkedList<>();

    public void run()
    {
        for(CommandBase c : commandQueue)
        {
            c.initialize();
            c.execute();
        }
        while(!getDone())
        {
            for(CommandBase c : commandQueue)
            {
                if(!c.isFinished())
                {
                    c.execute();
                }
            }
        }
    }

    public boolean getDone()
    {
        boolean done;
        for(CommandBase c : commandQueue)
        {
            if(!c.isFinished())
            {
                return false;
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

    public void remove(CommandBase command)
    {
        if(command == null)
        {
            commandQueue.remove(command);
        }
    }
}
