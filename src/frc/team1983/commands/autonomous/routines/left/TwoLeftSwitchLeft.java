package frc.team1983.commands.autonomous.routines.left;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoLeftSwitchLeft extends Path
{
    public TwoLeftSwitchLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -10.5, 2, 0),// new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new TurnDegree(drivebase, 90, 1),
                new DriveFeet(drivebase, 0.5, 1, 0),
                new DriveFeet(drivebase, 0, .1, 0),// new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                //Second Cube
                new DriveFeet(drivebase, -0.5, 1, 180),
                new TurnDegree(drivebase, -90, 1),
                new DriveFeet(drivebase, 8, 1, 0),// new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new TurnDegree(drivebase, 45, 1),
                new DriveFeet(drivebase, 1.5, 1, 45),// new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -0.4,1,45),// new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase,0.4,1,45),// new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -4, 2, 180)// new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL})

                                           )));
    }
}
