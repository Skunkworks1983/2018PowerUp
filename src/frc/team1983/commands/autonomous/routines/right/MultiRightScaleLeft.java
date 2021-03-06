package frc.team1983.commands.autonomous.routines.right;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiRightScaleLeft extends Path
{
    public MultiRightScaleLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -15.5, 2, 0, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new DriveFeet(drivebase, -16, 2.5, 90, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -4, 2, -10, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}).setProfileTimeout(0.2),
                new DriveFeet(drivebase, 0, 0.1, 0, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),

                //2nd Cube!
                new DriveFeet(drivebase, 5, 1.75, 10, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -5, 2.5, -10, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.SET_COLLECTOR_POSITION_BACK}),
                new DriveFeet(drivebase, 0, 0.2, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_MEDIUM}).setProfileTimeout(0.2),
                new DriveFeet(drivebase, 0, 1, 0, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM})
                                           )));
    }
}
