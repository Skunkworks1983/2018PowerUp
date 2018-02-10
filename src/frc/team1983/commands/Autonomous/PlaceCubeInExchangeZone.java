package frc.team1983.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.TurnAngle;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;


/*  This autonomous places the starting cube in the exchange zone, then drives in reverse past the auto line.
    More specifically, it moves to get some breathing room, then turns to face the right wall, then moves to be
    even with the exchange zone, then turns and moves in, then reverses past the auto line.
*/
public class PlaceCubeInExchangeZone extends CommandGroup
{

    private Drivebase drivebase = Robot.getInstance().getDrivebase();
    double distanceFromLeftWall = SmartDashboard.getNumber("Distance from left wall", 0);

    private Logger placeCubeInExchangeZoneLogger;

    public PlaceCubeInExchangeZone()
    {
        placeCubeInExchangeZoneLogger = LoggerFactory.createNewLogger(PlaceCubeInExchangeZone.class);
        //these are approximate values! they'll be subbed out later for stuff that works with motion profiling
        super.addSequential(new DriveStraight(5, drivebase));
        super.addSequential(new TurnAngle(90, drivebase));
        super.addSequential(new DriveStraight(distanceFromLeftWall - 8.5, drivebase));
        super.addSequential(new TurnAngle(90, drivebase));
        super.addSequential(new DriveStraight(5, drivebase));
        //super.addSequential(new Elevator(exchange zone height, elevator);
        //super.addSequential(new Collector(expel, collector);
        super.addSequential(new DriveStraight(-14, drivebase));



    }
}
