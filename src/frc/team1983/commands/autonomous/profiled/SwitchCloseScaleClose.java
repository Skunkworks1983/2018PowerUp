package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class SwitchCloseScaleClose extends Path
{
    public SwitchCloseScaleClose(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -16.5, 2),
                new DriveArc(drivebase, 4.5, -45, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase, 5.5, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -3, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST, ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new DriveFeet(drivebase, 2.5, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveArc(drivebase, -2, 180, 0.5),
                new DriveFeet(drivebase, 7.5, 3, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE}),
                new DriveFeet(drivebase, 0, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
    }
}
