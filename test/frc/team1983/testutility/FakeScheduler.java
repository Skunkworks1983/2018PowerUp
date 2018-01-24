package frc.team1983.testutility;

import frc.team1983.commands.CommandBase;

import java.util.LinkedList;
import java.util.List;

public class FakeScheduler
{
    private List<CommandBase> commandQueue = new LinkedList<>();
    private int counter = 0;
    private int loopCutOff = 1; //loop number

    public void run()
    {
        run(-1);
    }

    public void run(int loopCutOff)
    {
        this.loopCutOff = loopCutOff;
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
            }
        }
    }

    public boolean getDone()
    {
        if(loopCutOff == -1 || counter < loopCutOff)
        {
            for(CommandBase c : commandQueue)
            {
                if(!c.isFinished())
                {
                    return false;
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

    public void remove(CommandBase command)
    {
        if(command == null)
        {
            commandQueue.remove(command);
        }
    }
}
