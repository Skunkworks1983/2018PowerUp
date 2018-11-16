package frc.team1983.commands.autonomous.routines.left;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.utility.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class OneLeftSwitchLeft extends Path
{
    public OneLeftSwitchLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -10.5, 2, 0),
                new TurnDegree(drivebase, 90, 1),

                new DriveFeet(drivebase, 0, 1, 90, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase, 1, 1, 90),
                new DriveFeet(drivebase, 0, .5, 90, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, 0,1 , 90),
                new DriveFeet(drivebase, -4, 1, 90, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL})
                                           )));
    }
}
