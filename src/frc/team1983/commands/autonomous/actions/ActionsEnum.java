package frc.team1983.commands.autonomous.actions;


import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.DoNothing;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.settings.Constants;


public enum ActionsEnum
{
    // thsee names MUST match those in the json file or the world will explode
    NONE((((collector, elevator) -> new DoNothing()))),
    SET_COLLECTOR_POSITION_UP(((collector, elevator) -> new CollectorRotate(collector, Constants.PidConstants.CollectorRotate.UP_TICKS))),
    SET_COLLECTOR_POSITION_DOWN(((collector, elevator) -> new CollectorRotate(collector, Constants.PidConstants.CollectorRotate.DOWN_TICKS))),
    SET_ELEVATOR_SETPOINT_BOTTOM(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator))),
    SET_ELEVATOR_SETPOINT_TRAVEL(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.TRAVEL, elevator))),
    SET_ELEVATOR_SETPOINT_SWITCH(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator))),
    SET_ELEVATOR_SETPOINT_SCALE(((collector, elevator) -> new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator))),
    COLLECTOR_INTAKE(((collector, elevator) -> new CollectorIntake(collector, 1.5))), //TODO find timeout
    COLLECTOR_EXPEL_FAST(((collector, elevator) -> new CollectorExpel(collector, 1, 0.25))), //TODO find timeout
    COLLECTOR_EXPEL_SLOW(((collector, elevator) -> new CollectorExpel(collector, 0.25, 1.5))),
    COLLECTOR_ROTATE_MID(((collector, elevator) -> new CollectorRotate(collector, Constants.PidConstants.CollectorRotate.MID_TICKS)));

    // ugly please don't get mad at me im lazy

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
