package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.autonomous.profiled.CrossBaseline;
import frc.team1983.commands.autonomous.profiled.pointturns.LeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.pointturns.MidSwitchLeft;
import frc.team1983.commands.autonomous.profiled.pointturns.MidSwitchRight;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.path.Path;
import org.apache.logging.log4j.core.Logger;


public class OneCubeMPPicker extends CommandGroup
{
    Logger logger;

    public OneCubeMPPicker(Drivebase drivebase, Collector collector, Elevator elevator,
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
                    Path t = new MidSwitchLeft(drivebase);
                    CruiseProfile l = t.drives.get(t.drives.size() - 2).leftProfile;
                    CruiseProfile r = t.drives.get(t.drives.size() - 2).rightProfile;

                    l.setFinalVelocity(l.getCruiseVelocity());
                    r.setFinalVelocity(r.getCruiseVelocity());

                    addSequential(t);
                }
                else if(switchPosition == AutoManager.OwnedSide.RIGHT)
                {
                    addSequential(new MidSwitchRight(drivebase));
                }
                break;
            case RIGHT:
                addSequential(new CrossBaseline(drivebase));
            case LEFT:
                if(scaleSame)
                {
                    addSequential(new LeftScaleLeft(drivebase));
                }
                else if(!scaleSame)
                {
                    addSequential(new CrossBaseline(drivebase));
                }
                break;
        }
    }
}