package frc.team1983.commands.autonomous.profiled.twocubers;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoLeftScaleLeft extends Path
{
    public TwoLeftScaleLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 17, 2.5, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                //new TurnDegree(drivebase, -25, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 7, 3, -30, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.COLLECTOR_ROTATE_MID}),
                new DriveFeet(drivebase, 0, 0.25, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new TurnDegree(drivebase, -135, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new DriveFeet(drivebase, 3, 2, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -1, 1),
                new TurnDegree(drivebase, 135, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE}),
                new DriveFeet(drivebase, 3, 3, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
    }
}