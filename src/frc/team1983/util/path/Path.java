package frc.team1983.util.path;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.services.logger.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class Path extends CommandGroup
{
    private Logger logger;

    public ArrayList<DriveProfile> drives;

    public Path()
    {
        drives = new ArrayList<>();
    }

    public Path(ArrayList<DriveProfile> drives)
    {
        this.logger = LoggerFactory.createNewLogger(this.getClass());

        this.drives = drives;

        for(DriveProfile drive : drives)
        {
            CommandGroup movement = new CommandGroup();
            movement.addParallel(drive);

            for(CommandBase action : drive.getActions())
            {
                movement.addParallel(action);
            }

            addSequential(movement);
        }

        //DriveProfile.stitch(drives);
    }
}