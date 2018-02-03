package frc.team1983.testutility;

import frc.team1983.commands.CommandBase;
import frc.team1983.commands.CommandGroupWrapper;

import java.util.LinkedList;
import java.util.List;

public class FakeScheduler
{
    private List<List<CommandBase>> commandGroupList = new LinkedList<>();
    private List<CommandBase> commandQueue = new LinkedList<>();
    private List<CommandBase> orderFinished = new LinkedList<>();
    private int counter = 0;
    private int loopCutOff = 1; //loop number

    /*public  getCommandGroupList() //is this method even necessary
    {
        //return commandGroupList.get(commandGroupList.size()); //this could probably go into the run method
        //runCommandGroup()?
    }*/
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
                else
                {
                    orderFinished.add(c);
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

    public void addCommandGroup(CommandGroupWrapper commandGroupWrapper)
    {
        if(commandGroupWrapper != null)
        {
            //Collections.addAll(commandQueue, commandGroupWrapperList);
            //commandQueue.addAll(new List(commandGroupWrapperList);
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
