package frc.team1983.commands.autonomous.actions;


import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.collector.CollectorIntake;


public enum ActionsEnum
{
    NONE(null),
    COLLECTOR_INTAKE(((collector, elevator) -> new CollectorIntake(collector, 0.0))); //TODO find timeout

    private Action action;

    ActionsEnum(Action action)
    {
        this.action = action;
    }

    public Command getAction()
    {
        System.out.println("got action " + action.toString());
        return action.createAction(Robot.getInstance().getCollector(), Robot.getInstance().getElevator());
    }
}
