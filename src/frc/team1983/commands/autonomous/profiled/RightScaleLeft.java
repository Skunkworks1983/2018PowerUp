package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class RightScaleLeft extends Path
{
    public RightScaleLeft(Drivebase drivebase)
    {
        /*super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -14, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new DriveArc(drivebase, -3, 90, 1),
                new DriveFeet(drivebase, -15, 2, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveArc(drivebase, -1.5, 90, 1),
                new DriveFeet(drivebase, 6, 1),
                new DriveArc(drivebase, -1, -90, 2.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE}),
                new DriveFeet(drivebase, 1, 1),
                new DriveFeet(drivebase, 0.5, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -2, 1),
                new DriveFeet(drivebase, -0.5, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}))));*/

        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -15, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new DriveArc(drivebase, 3, -90, 1),
                new DriveFeet(drivebase, -12, 2, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveArc(drivebase, -3, 90, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE}),
                new DriveFeet(drivebase, 2, 1.5),
                new DriveFeet(drivebase, 0.5, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
    }
}
