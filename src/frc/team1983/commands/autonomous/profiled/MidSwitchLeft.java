package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class MidSwitchLeft extends Path
{
    public MidSwitchLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveArc(drivebase, -3, 90, 1.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL, ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, 4, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new DriveArc(drivebase, 3, 90, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 2.5, 1),
                new DriveFeet(drivebase, 0.5, 1),
                new DriveFeet(drivebase, -2, 1)
                                           )));
    }
}
