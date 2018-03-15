package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnTo;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class SwitchFarScaleFar extends Path
{
    public SwitchFarScaleFar(Drivebase drivebase) {
        super(new ArrayList<>(Arrays.asList(
            new DriveFeet(drivebase, -15, 2, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
            new DriveArc(drivebase, -3, 90, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
            new DriveFeet(drivebase, -17, 2, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
            new DriveArc(drivebase, -3, -90, 1.25, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.COLLECTOR_INTAKE}),
            new DriveFeet(drivebase, 0, 0.5, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
            new DriveFeet(drivebase, -1.5, 0.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
            new DriveFeet(drivebase, 1, 0.5, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
            new DriveArc(drivebase, -2, 110, 2),
            new TurnTo(drivebase, 0, 1),
            new DriveFeet(drivebase, 4, 1.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.COLLECTOR_INTAKE}),
            new DriveFeet(drivebase, 0, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
    }
}
