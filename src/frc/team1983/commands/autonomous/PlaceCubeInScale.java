package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.SimpleTurnAngle;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

public class PlaceCubeInScale extends CommandGroup
{
    private Drivebase drivebase = Robot.getInstance().getDrivebase();
    private boolean isOurColorLeft = true; //TODO: GET SOME REAL INFORMATION FOR THIS

    private Logger logger;

    public PlaceCubeInScale(StatefulDashboard dashboard)
    {
        logger = LoggerFactory.createNewLogger(PlaceCubeInScale.class);

        double distanceFromLeftWall = SmartDashboard.getNumber("Distance from left wall", 0);

        super.addSequential(new DriveStraight(dashboard, 7.0+1.4, drivebase, .5)); //are these numbers too magical? they're field constants

        //detects which side of the switch to place in
        if (isOurColorLeft)
        {
            super.addSequential(new SimpleTurnAngle(dashboard, -90, drivebase));

            //Drive to be closer to the wall than the switch
            super.addSequential(new DriveStraight(dashboard, (distanceFromLeftWall - 4), drivebase, .5));
            super.addSequential(new SimpleTurnAngle(dashboard, 90, drivebase));

        } else
        {
            super.addSequential(new SimpleTurnAngle(dashboard, 90, drivebase));
            super.addSequential(new DriveStraight(dashboard, 7.0+1.4, drivebase, .5));

            //Drive to be closer to the wall than the switch
            super.addSequential(new DriveStraight(dashboard, (13.5 - distanceFromLeftWall), drivebase, .5));
            super.addSequential(new SimpleTurnAngle(dashboard, -90, drivebase));
        }
        super.addSequential(new DriveStraight(dashboard, 13+1.4, drivebase, .5));

        if (isOurColorLeft)
        {
            super.addSequential(new SimpleTurnAngle(dashboard, 90, drivebase));
        } else
        {
            super.addSequential(new SimpleTurnAngle(dashboard, -90, drivebase));
        }

        super.addSequential(new DriveStraight(dashboard , 3+1.4, drivebase, .5));

        if (isOurColorLeft)
        {
            super.addSequential(new SimpleTurnAngle(dashboard, -90, drivebase, .5));
        } else
        {
            super.addSequential(new SimpleTurnAngle(dashboard , 90, drivebase));
        }

        super.addSequential(new DriveStraight(dashboard , 3+1.4, drivebase, .5));

        //super.addSequential(raise elevator);
        //super.addSequential(eject cube);
        //super.addSequential(lower elevator);

    }
}




