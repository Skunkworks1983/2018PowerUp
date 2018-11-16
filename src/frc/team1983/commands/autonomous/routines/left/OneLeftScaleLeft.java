package frc.team1983.commands.autonomous.routines.left;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class OneLeftScaleLeft extends Path
{
    public OneLeftScaleLeft(Drivebase drivebase) {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -4.5, 1, 25, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new DriveFeet(drivebase, -14, 2, 0),
                new DriveFeet(drivebase, -4.5, 1, -25),
                new TurnDegree(drivebase, 90, .8),
                new DriveFeet(drivebase, -3, .8, 90),
                new DriveFeet(drivebase, 0, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_MID}),
                new DriveFeet(drivebase, 0, 0.2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}
                ))));

    }
}