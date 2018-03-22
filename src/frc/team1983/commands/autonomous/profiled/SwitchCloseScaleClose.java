package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class SwitchCloseScaleClose extends Path
{
    public SwitchCloseScaleClose(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -18, 3, new ActionsEnum[]{ActionsEnum.SET_COLLECTOR_POSITION_DOWN}),
                new TurnDegree(drivebase, 35, 1),
                new DriveFeet(drivebase, 1, 0.5),
                new DriveFeet(drivebase, -1, 0.5),
                new DriveFeet(drivebase, 1, 0.5),
                new DriveFeet(drivebase, -3, 1),
                new TurnDegree(drivebase, 130, 1)
                                                    )));
    }
}
