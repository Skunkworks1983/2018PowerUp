package frc.team1983.commands.autonomous.profiled.pointturns;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class MidSwitchLeft extends Path
{
    public MidSwitchLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 3, 1, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new TurnDegree(drivebase, 60, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, 5.5, 1),
                new TurnDegree(drivebase, -60, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, 2.5, 1.5, new ActionsEnum[]{ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 2, 4, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_SLOW})
                                           )));
    }
}
