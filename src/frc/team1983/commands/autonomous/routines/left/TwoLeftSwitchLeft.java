package frc.team1983.commands.autonomous.routines.left;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.utility.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoLeftSwitchLeft extends Path
{
    public TwoLeftSwitchLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                //First Cube
                new DriveFeet(drivebase, -10.5, 2, 0),
                new TurnDegree(drivebase, 90, 1, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN, ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase, 2.5, .75, 90),
                new DriveFeet(drivebase,0,.75,90, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}).setProfileTimeout(0.2),
                new DriveFeet(drivebase, -2, .75, 90),
                //Second Cube
                new TurnDegree(drivebase, -90, 0.8, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL}),
                new DriveFeet(drivebase, -8, 1, 0, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new TurnDegree(drivebase, 30, 1),
                new DriveFeet(drivebase, 4, 1.2, 90 - 45, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -0.4,.75,90 - 45, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, 0,.5 , 90 - 45),
                new DriveFeet(drivebase,0.5,1,90 - 45).setProfileTimeout(1),
                new TurnDegree(drivebase, -30, .75).setProfileTimeout(1),
                new DriveFeet(drivebase, 0, 1, 90 - 45, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})

                                           )));
    }
}
