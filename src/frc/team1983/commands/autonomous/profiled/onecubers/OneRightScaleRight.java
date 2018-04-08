package frc.team1983.commands.autonomous.profiled.onecubers;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class OneRightScaleRight extends Path
{
    public OneRightScaleRight(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 25, 6, 0, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                //new DriveFeet(drivebase, 1.5, 1),
                new TurnDegree(drivebase, 90, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -2, 2, 90, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 2, 2, 90, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -4, 2, 90, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}))));
    }
}