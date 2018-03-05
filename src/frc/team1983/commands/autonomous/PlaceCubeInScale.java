package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.SimpleTurnAngle;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

public class PlaceCubeInScale extends CommandGroup
{
    private Drivebase drivebase = Robot.getInstance().getDrivebase();
    private boolean isOurColorLeft = true; //TODO: GET SOME REAL INFORMATION FOR THIS

    private Logger logger;

    public PlaceCubeInScale(Drivebase drivebase, StatefulDashboard dashboard)
    {
        logger = LoggerFactory.createNewLogger(PlaceCubeInScale.class);

        double distanceFromLeftWall = SmartDashboard.getNumber("Distance from left wall", 0);

        super.addSequential(new DriveStraight(dashboard, 7.0+ Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT, drivebase, .5)); //are these numbers too magical? they're field constants


        //super.addSequential(raise elevator);
        //super.addSequential(eject cube);
        //super.addSequential(lower elevator);

    }
}




