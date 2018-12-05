package frc.team1983.autonomous.routines;

import frc.team1983.autonomous.AutoRoutine;
import frc.team1983.commands.drivebase.DriveFeet;

public class TestRoutine extends AutoRoutine
{
    public TestRoutine()
    {
        addSequential(new DriveFeet(6));
    }
}
