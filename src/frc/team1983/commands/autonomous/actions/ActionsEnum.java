package frc.team1983.commands.autonomous.actions;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.Robot;
import frc.team1983.commands.collector.CollectorIntake;


public enum ActionsEnum
{
    NONE((((collector, elevator) -> new CommandGroup()))),
    COLLECTOR_INTAKE(((collector, elevator) -> new CollectorIntake(collector, 0.0))); //TODO find timeout

    private Action action;

    ActionsEnum(Action action)
    {
        this.action = action;
    }

    public Action getAction()
    {
        System.out.println("got action " + action.toString());
        return action;
    }
}
