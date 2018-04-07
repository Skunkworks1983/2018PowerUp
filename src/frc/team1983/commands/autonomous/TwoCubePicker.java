package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoLeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoLeftScaleRight;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoMidSwitchLeft;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoMidSwitchRight;
import frc.team1983.commands.autonomous.profiled.pointturns.LeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.pointturns.LeftScaleRight;
import frc.team1983.commands.autonomous.profiled.pointturns.RightScaleLeft;
import frc.team1983.commands.autonomous.profiled.pointturns.RightScaleRight;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoRightScaleLeft;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoRightScaleRight;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;


public class TwoCubePicker extends CommandGroup
{
    Logger logger;

    public TwoCubePicker(Drivebase drivebase, Collector collector, Elevator elevator,
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
                    addSequential(new TwoMidSwitchLeft(drivebase));
                }
                else if(switchPosition == AutoManager.OwnedSide.RIGHT)
                {
                    addSequential(new TwoMidSwitchRight(drivebase));
                }
                break;
            case RIGHT:
                if(scaleSame)
                {
                    addSequential(new TwoRightScaleRight(drivebase));
                }
                else
                {
                    addSequential(new TwoRightScaleLeft(drivebase));
                }
                break;
            case LEFT:
                if(scaleSame)
                {
                    addSequential(new TwoLeftScaleLeft(drivebase));
                }
                else
                {
                    addSequential(new TwoLeftScaleRight(drivebase));
                }
                break;
        }
    }
}