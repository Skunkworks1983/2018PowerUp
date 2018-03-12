package frc.team1983.commands.autonomous.deadreckoningautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;


//this command group is purely to hide the logic used to select the double cube non-motion profiled auto to run.
//it takes information about the field, position of the robot, and the subsystems and uses these to select an auto.
public class DoubleCubeAutoSelector extends CommandGroup
{

    Logger logger;
    AutoManager.OwnedSide Scale;
    AutoManager.OwnedSide Switch;

    public DoubleCubeAutoSelector(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, AutoManager.OwnedSide robotPosition)
    {
        logger = LoggerFactory.createNewLogger(DoubleCubeAutoSelector.class);
        //Scale = getOwnedSide(GameDataPoller.GameFeature.SCALE);
        Scale = AutoManager.OwnedSide.LEFT; //TODO: TEMPORARY AF THIS BOY IS JUST FOR TESTING

        logger.info("Scale is on the {}", Scale);

        //Switch = getOwnedSide(GameDataPoller.GameFeature.SWITCH_NEAR);
        Switch = AutoManager.OwnedSide.LEFT; //TODO: TEMPORARY AF THIS BOY IS JUST FOR TESTING

        logger.info("Switch is on the {}", Switch);

        AutoManager.OwnedSide Position = robotPosition;

        logger.info("Robot is on the {}", robotPosition);

        if(Switch == Scale && Switch == Position)
        {
            logger.info("Running SwitchCloseScaleClose");
            Scheduler.getInstance().add(new SwitchCloseScaleClose(drivebase, dashboard, oi, elevator, collector, robotPosition));
        }
        else if(Switch != Scale && Switch == Position)
        {
            Scheduler.getInstance().add(new SwitchCloseScaleFar(drivebase, dashboard, oi, elevator, collector, robotPosition));
        }
        else if(Switch != Scale && Switch != Position)
        {
            Scheduler.getInstance().add(new SwitchFarScaleClose(drivebase, dashboard, oi, elevator, collector, robotPosition));
        }
        else if(Switch == Scale && Switch != Position)
        {
            Scheduler.getInstance().add(new SwitchFarScaleFar(drivebase, dashboard, oi, elevator, collector, robotPosition));
        }
        else
        {
            //the robot doesn't have field position information yet. dunno what to do here tho
            //Thread.sleep(10); //?
        }
    }
}
