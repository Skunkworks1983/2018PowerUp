package frc.team1983.commands.autonomous.profiled.pointturns;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class RightScaleRight extends Path
{
    public RightScaleRight(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -27, 4, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, -90, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -3.5, 0.75),
                new DriveFeet(drivebase, 0, 1.5, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE}),
                new DriveFeet(drivebase, 3.5, 2),
                new DriveFeet(drivebase, 0, 2, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
    }
}
