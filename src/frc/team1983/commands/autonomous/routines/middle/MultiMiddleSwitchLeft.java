package frc.team1983.commands.autonomous.routines.middle;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiMiddleSwitchLeft extends Path
{
    private static double fromSwitchBackup = -5;

    private static double turn1 = -37;
    private static double turn2 = -35;

    public MultiMiddleSwitchLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
// 1st cube
                new DriveFeet(drivebase, 7.5, 1.25, 37, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new DriveFeet(drivebase, 3.5, 0.75, 0),
                new DriveFeet(drivebase, 0, 0.1, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}).setProfileTimeout(0.2),
                new DriveFeet(drivebase, fromSwitchBackup, 0.9, 0),

// 2nd cube
                new TurnDegree(drivebase, turn1, 0.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new DriveFeet(drivebase, 4.5, 1.2, turn1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -5, 1.2, turn1 - 15, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new TurnDegree(drivebase, -turn1, 0.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.COLLECTOR_INTAKE}),

                // drive, deposit and backup
                new DriveFeet(drivebase, -fromSwitchBackup, 0.9, 0),
                new DriveFeet(drivebase, 0, 0.1, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}).setProfileTimeout(0.2),
                new DriveFeet(drivebase, fromSwitchBackup, 0.9, 0),
// 3rd cube
                new TurnDegree(drivebase, turn2, 0.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new DriveFeet(drivebase, 4.5, 1.2, turn2, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -4.5, 1.2, turn2 - 15, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new TurnDegree(drivebase, -turn2, 0.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.COLLECTOR_INTAKE}),

                new DriveFeet(drivebase, -fromSwitchBackup, 1, 0),
                new DriveFeet(drivebase, 0, 0.1, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}).setProfileTimeout(0.2)
                                           )));
    }
}
