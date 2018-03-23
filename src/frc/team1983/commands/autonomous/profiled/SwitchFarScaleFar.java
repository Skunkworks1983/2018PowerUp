package frc.team1983.commands.autonomous.profiled;

import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.path.Path;

import java.util.ArrayList;
import java.util.Arrays;

public class SwitchFarScaleFar extends Path
{
    public SwitchFarScaleFar(Drivebase drivebase)
    {
        super(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, -19, 2),
                new TurnDegree(drivebase, -90, 1),
                new DriveFeet(drivebase, -45, 1),
                new DriveFeet(drivebase, 1, 0.5)
                                           )));
    }
}
