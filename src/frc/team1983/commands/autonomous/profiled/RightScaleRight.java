package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class RightScaleRight extends Path
{
    public RightScaleRight(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -0.5, 0.5),
                new DriveFeet(drivebase, -22, 2.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new DriveArc(drivebase, -3, 90, 2.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE}),
                new DriveFeet(drivebase, 3, 2),
                new DriveFeet(drivebase, 0, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -3, 1.5),
                new DriveFeet(drivebase, -1, 1.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}))));
    }
}
