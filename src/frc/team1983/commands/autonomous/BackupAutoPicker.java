package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import org.apache.logging.log4j.core.Logger;


public class BackupAutoPicker extends CommandGroup
{
    public BackupAutoPicker(AutoManager autoManager)
    {
        Logger logger = LoggerFactory.createNewLogger(this.getClass());

        if(autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) ==
                autoManager.getOwnedSide(AutoManager.GameFeature.SCALE) &&
                autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) ==
                        autoManager.getRobotPosition())
        {
            logger.info("Running SwitchCloseScaleClose");
            //Scheduler.getInstance().add(new SwitchCloseScaleClose(drivebase, dashboard, oi, elevator, collector, robotPosition));
        }
        else if(autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) !=
                autoManager.getOwnedSide(AutoManager.GameFeature.SCALE) &&
                autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) ==
                        autoManager.getRobotPosition())
        {
            //switch close scale far
        }
        else if(autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) !=
                autoManager.getOwnedSide(AutoManager.GameFeature.SCALE) &&
                autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) !=
                        autoManager.getRobotPosition())
        {
            //switch far scale close
        }
        else if(autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) ==
                autoManager.getOwnedSide(AutoManager.GameFeature.SCALE) &&
                autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) !=
                        autoManager.getRobotPosition())
        {
            //switch far scale far
        }
        else
        {
            //the robot doesn't have field position information yet. dunno what to do here tho
        }
        //this isn't fleshed out yet - Mike took a picture of the logic on a whiteboard. yell at him if he leaves it unfinished.
    }
}