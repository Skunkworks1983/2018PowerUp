package frc.team1983.commands.autonomous.pickers;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public class SwitchPicker extends CommandGroup
{
    public SwitchPicker(Drivebase drivebase, Collector collector, Elevator elevator,
                                   DashboardWrapper dashboardWrapper, StatefulDashboard statefulDashboard,
                                   AutoManager autoManager)
    {
        AutoManager.OwnedSide switchPosition, scalePosition;
        switchPosition = autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR);
        scalePosition = autoManager.getOwnedSide(AutoManager.GameFeature.SCALE);

        boolean switchSame = switchPosition == autoManager.getRobotPosition();
        boolean scaleSame = scalePosition == autoManager.getRobotPosition();

        switch(autoManager.getRobotPosition())
        {
            case UNKNOWN:
                if(switchPosition == AutoManager.OwnedSide.LEFT)
                    addSequential(autoManager.multiMiddleSwitchLeft);
                else
                    addSequential(autoManager.multiMiddleSwitchRight);
            case RIGHT:
                if(switchSame)
                    addSequential(autoManager.twoRightSwitchRight);
                else
                    addSequential(autoManager.rightCross);
            case LEFT:
                if(switchSame)
                    addSequential(autoManager.twoLeftSwitchLeft);
                else
                    addSequential(autoManager.leftCross);
        }
    }
}
