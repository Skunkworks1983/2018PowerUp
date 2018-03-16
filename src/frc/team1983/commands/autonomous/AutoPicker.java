package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.services.automanager.AutoManager;


public class AutoPicker extends CommandGroup
{
    public AutoPicker(AutoManager autoManager)
    {
        AutoManager.OwnedSide switchPosition, scalePosition;
        switchPosition = autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR);
        scalePosition = autoManager.getOwnedSide(AutoManager.GameFeature.SCALE);

        switch(autoManager.getRobotPosition())
        {
            case UNKNOWN:
                if(switchPosition == AutoManager.OwnedSide.LEFT)
                {

                }
                else
                {
                    // run right side auto if unknown also
                }

                break;
            case LEFT:
                if(switchPosition == AutoManager.OwnedSide.LEFT)
                {

                }
                else
                {
                    // run right side auto if unknown also
                }

                break;
            case RIGHT:
                if(switchPosition == AutoManager.OwnedSide.LEFT)
                {

                }
                else
                {
                    // run right side auto if unknown also
                }

                break;
        }
    }
}