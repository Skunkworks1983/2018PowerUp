import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

 import java.util.LinkedList;
 import java.util.List;

 public class FakeScheduler extends Scheduler{

     List<Command> commandqueue = new LinkedList<Command>();

     @Override
     public void run(Command command)
     {


         for(int i = commandqueue.size();  i >= 0; i--)
            {
                if (command.start())
                {
                    commandqueue.remove(command);
                }
                else
                {
                    commandqueue.run(command);
                }
            }

     }

    @Override
    public void add(Command command)
    {
        if(command != null)
        {
            commandqueue.add(command);
        }
    }

    @Override
    public void remove(Command command)
    {
        if(command == null)
        {
            commandqueue.remove(command);
        }
    }


}
