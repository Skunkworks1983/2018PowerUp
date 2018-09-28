package frc.team1983.commands.autonomous.routines.right;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiRightScaleRight extends Path
{
    public MultiRightScaleRight(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
// 1st cube
                new DriveFeet(drivebase, -16, 2, 0),
                new DriveFeet(drivebase, -5.5, 2.75, 35, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_MEDIUM}).setProfileTimeout(0.15),
//2nd cube
                new DriveFeet(drivebase, 0, 0.2, -25, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, -30, 0.5),
                new DriveFeet(drivebase, 5.5, 1.5, -15, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                //new DriveFeet(drivebase, 0, 1).setProfileTimeout(1),
                new DriveFeet(drivebase, -3, 1.75, -15, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -2.5, 1.5,20, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE, ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_MEDIUM}),
                new DriveFeet(drivebase, 0, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new DriveFeet(drivebase, 2, 5, 0)
//3rd cube
                /*
                new DriveFeet(drivebase, 0, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new DriveFeet(drivebase, 2, 0.75, 0),
                new DriveFeet(drivebase, 5, 1, -28, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -3, 1, -28, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -3, 1, 0),
                new TurnDegree(drivebase, 35, 0.5),
                new DriveFeet(drivebase, 0, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_MEDIUM})
                                          */ )));
    }
}
