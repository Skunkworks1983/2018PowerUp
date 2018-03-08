package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.services.automanager.AutoManager;


public class AutoMotionProfilePicker extends CommandGroup
{

    public AutoMotionProfilePicker(AutoManager autoManager)
    {
        switch(autoManager.getRobotPosition())
        {
            case UNKNOWN:
                //add the middle profile
                break;
            case RIGHT:
                //add the side profile, with right condition
                break;
            case LEFT:
                //add the side profile, with left condition
                break;
        }
    }
}
