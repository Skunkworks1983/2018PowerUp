package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.SimpleTurnAngle;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;


/*  This autonomous places the starting cube in the exchange zone, then drives in reverse past the auto line.
    More specifically, it moves to get some breathing room, then turns to face the right wall, then moves to be
    even with the exchange zone, then turns and moves in, then reverses past the auto line.
*/
public class PlaceCubeInExchangeZone extends CommandGroup
{

    private Drivebase drivebase;
    private double distanceFromLeftWall = SmartDashboard.getNumber("Distance from left wall", 5);
    private StatefulDashboard dashboard;

    private Logger logger;

    public PlaceCubeInExchangeZone(Drivebase drivebase, StatefulDashboard dashboard)
    {
        this.drivebase = drivebase;
        logger = LoggerFactory.createNewLogger(PlaceCubeInExchangeZone.class);
        //these are approximate values! they'll be subbed out later for stuff that works with motion profiling
        //goes forward and crosses the line, then turns toward middle
        super.addSequential(new DriveStraight(dashboard, 5+ Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT, drivebase, .25));
        super.addSequential(new DifferentialTurnAngle(dashboard, 85, drivebase));

        //drives perpendicular to exchange port, and then turns toward it
        super.addSequential(new DriveStraight(dashboard, 8.25 - distanceFromLeftWall, drivebase, .25));
        super.addSequential(new DifferentialTurnAngle(dashboard, 85, drivebase));

        //drives into exchange port, then expels cube, and then backs up
        super.addSequential(new DriveStraight(dashboard, 3+Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT, drivebase, .25));
        //super.addSequential(new Elevator(exchange zone height, elevator);
        //super.addSequential(new Collector(expel, collector);
        //super.addSequential(new DriveStraight(dashboard, -6.75+Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBO T, drivebase, .5));



    }
}
