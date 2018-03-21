package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.autonomous.profiled.SwitchCloseScaleClose;
import frc.team1983.commands.autonomous.profiled.SwitchCloseScaleFar;
import frc.team1983.commands.autonomous.profiled.SwitchFarScaleClose;
import frc.team1983.commands.autonomous.profiled.SwitchFarScaleFar;
import frc.team1983.commands.autonomous.profiled.pointturns.MidSwitchRight;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;


public class OneCubeMPPicker extends CommandGroup
{
    public OneCubeMPPicker(Drivebase drivebase, Collector collector, Elevator elevator,
                           DashboardWrapper dashboardWrapper, StatefulDashboard statefulDashboard,
                           AutoManager autoManager)
    {
        AutoManager.OwnedSide switchPosition, scalePosition;
        switchPosition = autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR);
        scalePosition = autoManager.getOwnedSide(AutoManager.GameFeature.SCALE);

        boolean switchSame, scaleSame;

        switchSame = switchPosition == autoManager.getRobotPosition();
        scaleSame = scalePosition == autoManager.getRobotPosition();

        switch(autoManager.getRobotPosition())
        {
            case UNKNOWN:
                if(switchPosition == AutoManager.OwnedSide.LEFT)
                {

                }
                else if(switchPosition == AutoManager.OwnedSide.RIGHT)
                {
                    addSequential(new MidSwitchRight(drivebase));
                }
                break;
            case RIGHT:
            case LEFT:
                if(switchSame && scaleSame)
                {

                }
                else if(switchSame && !scaleSame)
                {

                }
                else if(!switchSame && scaleSame)
                {

                }
                else
                {

                }

                break;
        }
    }
}