package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;


//Places a cube in our switch from any position on the alliance station wall. drives straight until it's centered
//between the wall and the switch, then drives to the side that is its color then drives up to the switch and places a kyoob
public class PlaceCubeInSwitch extends CommandGroup
{
    private boolean isOurColorLeft = true; //TODO: GET SOME REAL INFORMATION FOR THIS
    private Logger logger;


    public PlaceCubeInSwitch(Drivebase drivebase, StatefulDashboard dashboard, Elevator elevator, Collector collector)
    {
        logger = LoggerFactory.createNewLogger(PlaceCubeInSwitch.class);
        //double distanceFromLeftWall = SmartDashboard.getNumber("Distance from left wall", 0);
        super.addSequential(new CollectorRotate(collector, Constants.PidConstants.CollectorRotate.DOWN_TICKS));
        super.addSequential(new DriveStraight(drivebase, dashboard, 10.0)); //are these numbers too magical? they're field constants

        //detects which side of the switch to place in
        if(isOurColorLeft)
        {
            super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        }
        else
        {
            super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90));
        }

        //super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi), 2);
        super.addSequential(new DriveStraight(drivebase, dashboard, 2.0));
        super.addSequential(new CollectorExpel(collector, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED));
        //drive to be equally distant from the left wall as the left side of the switch is. (7.5 ft away)


        //super.addSequential(raise elevator);
        //super.addSequential(eject cube);
        //super.addSequential(lower elevator);
    }
}







