package frc.team1983.commands.autonomous.profiled.onecubers;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class OneLeftScaleRight extends Path
{
    public OneLeftScaleRight(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 17, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, 90, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -21, 3, 90),
                new TurnDegree(drivebase, -60, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.COLLECTOR_INTAKE, ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 4, 2, 30),
                new DriveFeet(drivebase, 0, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -4, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_COLLECTOR_POSITION_DOWN})
                                           )));
    }
}
