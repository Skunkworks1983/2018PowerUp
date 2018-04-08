package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.autonomous.profiled.CrossBaselineForwards;
import frc.team1983.commands.autonomous.profiled.onecubers.OneLeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.onecubers.OneLeftScaleRight;
import frc.team1983.commands.autonomous.profiled.onecubers.OneMidSwitchLeft;
import frc.team1983.commands.autonomous.profiled.onecubers.OneMidSwitchRight;
import frc.team1983.commands.autonomous.profiled.onecubers.OneRightScaleLeft;
import frc.team1983.commands.autonomous.profiled.onecubers.OneRightScaleRight;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

public class OneCubePicker extends CommandGroup
{
    Logger logger;
    public OneCubePicker(Drivebase drivebase, Collector collector, Elevator elevator,
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
                    addSequential(new OneMidSwitchLeft(drivebase));
                }
                else if(switchPosition == AutoManager.OwnedSide.RIGHT)
                {
                    addSequential(new OneMidSwitchRight(drivebase));
                }
                break;
            case RIGHT:
                if(scaleSame)
                {
                    addSequential(new OneRightScaleRight(drivebase));
                }
                else
                {
                    //addSequential(new OneRightScaleLeft(drivebase));
                    addSequential(new CrossBaselineForwards(drivebase));
                }
                break;
            case LEFT:
                if(scaleSame)
                {
                    addSequential(new OneLeftScaleLeft(drivebase));
                }
                else
                {
                    //addSequential(new OneLeftScaleRight(drivebase));
                    addSequential(new CrossBaselineForwards(drivebase));
                }
                break;
        }
    }
}
