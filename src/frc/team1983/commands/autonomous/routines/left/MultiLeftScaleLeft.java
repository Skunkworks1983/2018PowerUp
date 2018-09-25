package frc.team1983.commands.autonomous.routines.left;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiLeftScaleLeft extends Path
{
    public MultiLeftScaleLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
// 1st cube
                new DriveFeet(drivebase, -14, 2, 0),
                new DriveFeet(drivebase, -5, 1, -25),
                new DriveFeet(drivebase, 0, 2, -25, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_MEDIUM}),
//2nd cube
                new DriveFeet(drivebase, 0, 0.2, -25, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, 35, 0.5),
                new DriveFeet(drivebase, 6, 1.75, 10, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -5.5, 1.25,10, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new TurnDegree(drivebase, -35, 0.5),
                new DriveFeet(drivebase, 0, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_MEDIUM}),
//3rd cube
                new DriveFeet(drivebase, 0, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new DriveFeet(drivebase, 2, 0.75, 0),
                new DriveFeet(drivebase, 5, 1, 28, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -3, 1, 28, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -3, 1, 0),
                new TurnDegree(drivebase, -35, 0.5),
                new DriveFeet(drivebase, 0, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_MEDIUM})
                                           )));
    }
}
