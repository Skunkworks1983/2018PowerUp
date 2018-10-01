package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.Action;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class CrossBaselineBackwards extends Path
{
    public CrossBaselineBackwards(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -15, 3, new ActionsEnum[]{ActionsEnum.SET_ELEVATOR_SETPOINT_TRAVEL, ActionsEnum.SET_COLLECTOR_POSITION_DOWN})
                                           )));
    }
}
