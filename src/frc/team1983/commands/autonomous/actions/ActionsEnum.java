package frc.team1983.commands.autonomous.actions;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.Robot;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.settings.Constants;


public enum ActionsEnum
{
    // thsee names MUST match those in the json file or the world will explode
    NONE((((collector, elevator) -> new CommandGroup()))),
    SET_COLLECTOR_POSITION_UP(((collector, elevator) -> new CollectorRotate(collector, true))),
    SET_COLLECTOR_POSITION_DOWN(((collector, elevator) -> new CollectorRotate(collector, false))),
    SET_ELEVATOR_SETPOINT_BOTTOM(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator))),
    SET_ELEVATOR_SETPOINT_SWITCH(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator))),
    SET_ELEVATOR_SETPOINT_MID(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.LOW, elevator))),
    SET_ELEVATOR_SETPOINT_LOW(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.MID, elevator))),
    SET_ELEVATOR_SETPOINT_TOP(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator))),
    COLLECTOR_INTAKE(((collector, elevator) -> new CollectorIntake(collector, 0.0))), //TODO find timeout
    COLLECTOR_EXPEL_FAST(((collector, elevator) -> new CollectorExpel(collector, true, 0))), //TODO find timeout
    COLLECTOR_EXPEL_SLOW(((collector, elevator) -> new CollectorExpel(collector, false, 0))); //TODO find timeout

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
