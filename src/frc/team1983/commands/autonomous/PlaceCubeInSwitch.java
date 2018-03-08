package frc.team1983.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.SimpleTurnAngle;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;


//Places a cube in our switch from any position on the alliance station wall. drives straight until it's centered
//between the wall and the switch, then drives to the side that is its color then drives up to the switch and places a kyoob
public class PlaceCubeInSwitch extends CommandGroup
{
    private Logger logger;

    public PlaceCubeInSwitch(Drivebase drivebase, StatefulDashboard dashboard, AutoManager autoManager)
    {
        logger = LoggerFactory.createNewLogger(PlaceCubeInSwitch.class);
        double distanceFromLeftWall = SmartDashboard.getNumber("Distance from left wall", 0);

        super.addSequential(new DriveStraight(dashboard, 7.0+ Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT, drivebase, .5)); //are these numbers too magical? they're field constants

           //detects which side of the switch to place in
            if (autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) == AutoManager.OwnedSide.LEFT)
            {
                super.addSequential(new SimpleTurnAngle(dashboard, -90, drivebase));

                //drive to be equally distant from the left wall as the left side of the switch is. (7.5 ft away)
                super.addSequential(new DriveStraight(dashboard, (distanceFromLeftWall - 3.5), drivebase, .5));
                super.addSequential(new SimpleTurnAngle(dashboard, 90, drivebase));

            } else
            {
                super.addSequential(new SimpleTurnAngle(dashboard, 90, drivebase));
                super.addSequential(new DriveStraight(dashboard, 7.0+Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT, drivebase, .5));

                //drive to be equally distant from the left wall as the right side of the switch is (19.5 ft away)
                super.addSequential(new DriveStraight(dashboard, (19.5 - distanceFromLeftWall), drivebase, .5));
                super.addSequential(new SimpleTurnAngle(dashboard, -90, drivebase));
            }
            super.addSequential(new DriveStraight(dashboard, 7, drivebase, .5));

            if(autoManager.getOwnedSide(AutoManager.GameFeature.SWITCH_NEAR) == AutoManager.OwnedSide.LEFT)
            {
                super.addSequential(new SimpleTurnAngle(dashboard, 90, drivebase));
                super.addSequential(new DriveStraight(dashboard, 1.25+ Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT, drivebase, .5));
            }

            else
            {
                super.addSequential(new SimpleTurnAngle(dashboard, -90, drivebase));
                super.addSequential(new DriveStraight(dashboard, 1.25+ Constants.AutoValues.DISTANCE_FROM_ENCODER_TO_END_OF_ROBOT, drivebase, .5));
            }

            //super.addSequential(raise elevator);
            //super.addSequential(eject cube);
            //super.addSequential(lower elevator);
    }
}







