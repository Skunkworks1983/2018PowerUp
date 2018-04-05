package frc.team1983.commands.autonomous.profiled.forwards;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class MidSwitchRight extends Path
{
    public MidSwitchRight(Drivebase drivebase)
    {
        /*super(new ArrayList<>(Arrays.asList(
                new DriveArc(drivebase, 5, 45, 1.5, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new DriveArc(drivebase, -5, -45, 1.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase, 2, 1),
                new DriveFeet(drivebase, 0, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));*/

        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 8, 2, -45, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, 50, 1),
                new DriveFeet(drivebase, 1, 1, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -2, 1, 0, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new TurnDegree(drivebase, 40, 1),
                new DriveFeet(drivebase, 5, 1, 40, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -2, 1, 40),
                new TurnDegree(drivebase, -40, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase, 3, 1, 0),
                new DriveFeet(drivebase, 0, 0.01, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
    }
}
