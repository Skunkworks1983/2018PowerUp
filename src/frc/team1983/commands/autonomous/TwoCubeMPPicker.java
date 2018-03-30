package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.Robot;
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
import frc.team1983.util.path.Path;


public class TwoCubeMPPicker extends CommandGroup
{
    private Path SideCloseCloser = new SwitchCloseScaleClose(Robot.getInstance().getDrivebase()),
                 SideCloseFar = new SwitchCloseScaleFar(Robot.getInstance().getDrivebase()),
                 SideFarClose = new SwitchFarScaleClose(Robot.getInstance().getDrivebase()),
                 SideFarFar = new SwitchFarScaleFar(Robot.getInstance().getDrivebase());
    private Path Middle;

    public TwoCubeMPPicker(Drivebase drivebase, Collector collector, Elevator elevator,
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

                }
                break;
            case RIGHT:
            case LEFT:
                if(switchSame && scaleSame)
                {
                    addSequential(new SwitchCloseScaleClose(drivebase));
                }
                else if(switchSame && !scaleSame)
                {
                    addSequential(new SwitchCloseScaleFar(drivebase));
                }
                else if(!switchSame && scaleSame)
                {
                    addSequential(new SwitchFarScaleClose(drivebase));
                }
                else
                {
                    addSequential(new SwitchFarScaleFar(drivebase));
                }

                break;
        }
    }
}