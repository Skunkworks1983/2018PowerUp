package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.autonomous.deadreckoningautos.MidToSwitch;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;


public class BackupAutoPicker extends CommandGroup
{
    public BackupAutoPicker(Drivebase drivebase, Collector collector, Elevator elevator,
                            DashboardWrapper dashboardWrapper, StatefulDashboard statefulDashboard,
                            AutoManager autoManager)
    {
        AutoManager.OwnedSide switchPosition, scalePosition;
        switchPosition = autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR);
        scalePosition = autoManager.getOwnedSide(AutoManager.GameFeature.SCALE);

        switch(autoManager.getRobotPosition())
        {
            case UNKNOWN:
                if(switchPosition == AutoManager.OwnedSide.LEFT)
                {
                    addSequential(new MidToSwitch(drivebase, statefulDashboard, elevator, collector, AutoManager.OwnedSide.LEFT));
                }
                else
                {
                    // run right side auto if unknown also
                    addSequential(new MidToSwitch(drivebase, statefulDashboard, elevator, collector, AutoManager.OwnedSide.RIGHT));
                }

                break;
            case LEFT:
            case RIGHT:
                addSequential(new DriveFeet(drivebase, -10, 2));

                break;
        }
    }
}