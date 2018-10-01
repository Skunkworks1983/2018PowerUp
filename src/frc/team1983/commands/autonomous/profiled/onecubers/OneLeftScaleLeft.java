package frc.team1983.commands.autonomous.profiled.onecubers;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class OneLeftScaleLeft extends Path
{
    public OneLeftScaleLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 23, 3, 0, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                //new DriveFeet(drivebase, 1.5, 1),
                new TurnDegree(drivebase, -80, 0.75, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -2, 0.75, -80, new ActionsEnum[]{ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 0, 0.1, -80, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE}),
                new DriveFeet(drivebase, 2, 0.5, -80, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -4, 1, -80),
                new DriveFeet(drivebase, 0, 0.1, -80, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL, ActionsEnum.SET_COLLECTOR_POSITION_DOWN})
                                           )));

        /*
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 15, 2, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new TurnDegree(drivebase, -25, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 5, 3),
                new DriveFeet(drivebase, 1, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
                                           */
    }
}
