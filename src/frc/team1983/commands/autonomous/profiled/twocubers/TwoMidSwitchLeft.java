package frc.team1983.commands.autonomous.profiled.twocubers;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoMidSwitchLeft extends Path
{
    private static double turn = -35;
    private static double turn2 = -35;
    private static double turn3 = 32;

    public TwoMidSwitchLeft(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 7, 1.5, 35, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                //new TurnDegree(drivebase, 40, 0.5),
                new DriveFeet(drivebase, 2.5, 0.75, 0),
                new DriveFeet(drivebase, 0, 0.4, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}).setProfileTimeout(0.5),
                new DriveFeet(drivebase, -3, 0.8, 0),

                new TurnDegree(drivebase, turn, 0.75, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new DriveFeet(drivebase, 4.5, 1.75, turn, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}).setProfileTimeout(1.75),
                new DriveFeet(drivebase, -3, 0.75, turn, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new TurnDegree(drivebase, -turn, 0.75, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase, 3.75, 1, 0),
                new DriveFeet(drivebase, 0, 0.4, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}).setProfileTimeout(0.5),
                new DriveFeet(drivebase, -3.5, 0.8, 0),

                new TurnDegree(drivebase, turn2-8, 0.75, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BARF}),
                new DriveFeet(drivebase, 4.5, 1.5, turn2, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE})
                ////new DriveFeet(drivebase, -2, 0.75, turn3, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                ////new DriveFeet(drivebase, 2.5, 0.75, turn3, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE, ActionsEnum.SET_ELEVATOR_SETPOINT_NEW_AGE_BARF}),
                ////new DriveFeet(drivebase, 0, 0.4, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
        )));

        /*
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 8, 1.5, 40),
                new DriveFeet(drivebase, -3, 0.5, 40),
                new TurnDegree(drivebase, -80, 0.5),

                new DriveFeet(drivebase, 3, 1, -40),
                new DriveFeet(drivebase, -3, 1, -40),
                new TurnDegree(drivebase, 80, 0.5),

                new DriveFeet(drivebase, 4, 1, 40),
                new DriveFeet(drivebase, -4, 1, 40),
                new TurnDegree(drivebase, -80, 0.5),

                new DriveFeet(drivebase, 2, 1, 40),
                new DriveFeet(drivebase, -2, 1, 40),
                new TurnDegree(drivebase, 80, 0.5),

                new DriveFeet(drivebase, 3, 1, 40),
                new DriveFeet(drivebase, -3, 1, 40)
                                           )));
        */

        /*
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 8.5, 2, 45, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, -50, 1),
                new DriveFeet(drivebase, 2, 1, 0),
                new DriveFeet(drivebase, 0.5, 0.5, 0, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST}),
                new DriveFeet(drivebase, -2, 1, 0),
                new TurnDegree(drivebase, -40, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_BOTTOM}),
                new DriveFeet(drivebase, 5, 1.5, -40, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -2, 1, -40, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new TurnDegree(drivebase, 40, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SWITCH}),
                new DriveFeet(drivebase, 5, 1, 0),
                new DriveFeet(drivebase, 0, 0.01, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
                                           */

    }
}