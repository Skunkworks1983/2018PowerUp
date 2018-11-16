package frc.team1983.utility.path;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
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
            CommandGroup actions = new CommandGroup();

            movement.addParallel(drive);

            for(CommandBase action : drive.getActions())
            {
                actions.addParallel(action);
            }

            movement.addParallel(actions);
            addSequential(movement);
        }

        //DriveProfile.stitch(drives);
    }

    public void reverse()
    {
        for(DriveProfile drive : drives)
        {
            if(drive instanceof DriveFeet)
            {
                if(drive.useAbsoluteOrientation)
                {
                    drive.endHeading = 360 - drive.endHeading;
                }
            }
            else if(drive instanceof DriveArc)
            {
                DriveArc arc = (DriveArc) drive;
                drive.leftProfile = DriveArc.generateLeftProfile(-arc.radius, -arc.angle, arc.duration);
                drive.rightProfile = DriveArc.generateRightProfile(-arc.radius, -arc.angle, arc.duration);

                drive.endHeading = 360 - drive.endHeading;
            }
        }
    }
}