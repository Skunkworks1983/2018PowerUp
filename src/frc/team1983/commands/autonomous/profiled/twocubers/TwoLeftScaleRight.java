package frc.team1983.commands.autonomous.profiled.twocubers;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class TwoLeftScaleRight extends Path
{
    public TwoLeftScaleRight(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -21, 2, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL, ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, -90, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, -24.5, 3, -90),
                new TurnDegree(drivebase, -45, 1, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_SCALE, ActionsEnum.COLLECTOR_INTAKE}),
                new DriveFeet(drivebase, 5, 3, -135),
                new DriveFeet(drivebase, 0, 1, new ActionsEnum[]{ActionsEnum.COLLECTOR_EXPEL_FAST})
                                           )));
    }
}
