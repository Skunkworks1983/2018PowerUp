package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.autonomous.profiled.LeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.LeftScaleRight;
import frc.team1983.commands.autonomous.profiled.MidSwitchLeft;
import frc.team1983.commands.autonomous.profiled.MidSwitchRight;
import frc.team1983.commands.autonomous.profiled.RightScaleLeft;
import frc.team1983.commands.autonomous.profiled.RightScaleRight;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;


public class AutoPicker extends CommandGroup
{
    public AutoPicker(Drivebase drivebase, Collector collector, Elevator elevator,
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
                    addSequential(new MidSwitchLeft(drivebase));
                }
                else
                {
                    // run right side auto if unknown also
                    addSequential(new MidSwitchRight(drivebase));
                }

                break;
            case LEFT:
                if(switchPosition == AutoManager.OwnedSide.LEFT)
                {
                    addSequential(new LeftScaleLeft(drivebase));
                }
                else
                {
                    addSequential(new LeftScaleRight(drivebase));
                    // run right side auto if unknown also
                }

                break;
            case RIGHT:
                if(switchPosition == AutoManager.OwnedSide.RIGHT)
                {
                    addSequential(new RightScaleRight(drivebase));
                }
                else
                {
                    addSequential(new RightScaleLeft(drivebase));
                    // run right side auto if unknown also
                }

                break;
        }
    }
}