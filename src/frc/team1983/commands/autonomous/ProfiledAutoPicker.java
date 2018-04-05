package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.autonomous.profiled.forwards.MidSwitchLeft;
import frc.team1983.commands.autonomous.profiled.forwards.MidSwitchRight;
import frc.team1983.commands.autonomous.profiled.pointturns.LeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.pointturns.LeftScaleRight;
import frc.team1983.commands.autonomous.profiled.pointturns.RightScaleLeft;
import frc.team1983.commands.autonomous.profiled.pointturns.RightScaleRight;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;


public class ProfiledAutoPicker extends CommandGroup
{
    Logger logger;

    public ProfiledAutoPicker(Drivebase drivebase, Collector collector, Elevator elevator,
                              DashboardWrapper dashboardWrapper, StatefulDashboard statefulDashboard,
                              AutoManager autoManager)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());
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
                    addSequential(new MidSwitchLeft(drivebase));
                }
                else if(switchPosition == AutoManager.OwnedSide.RIGHT)
                {
                    //addSequential(new MidToSwitch(drivebase, statefulDashboard, elevator, collector, AutoManager.OwnedSide.RIGHT));
                    addSequential(new MidSwitchRight(drivebase));
                }
                break;
            case RIGHT:
                if(scaleSame)
                {
                    addSequential(new RightScaleRight(drivebase, true));
                }
                else
                {
                    addSequential(new RightScaleLeft(drivebase, true));
                }
                break;
            case LEFT:
                if(scaleSame)
                {
                    addSequential(new LeftScaleLeft(drivebase, true));
                }
                else
                {
                    addSequential(new LeftScaleRight(drivebase, true));
                }
                break;
        }
    }
}