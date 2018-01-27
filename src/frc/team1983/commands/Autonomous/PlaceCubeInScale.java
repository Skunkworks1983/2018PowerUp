package frc.team1983.commands.Autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.TurnAngle;
import frc.team1983.subsystems.Drivebase;

public class PlaceCubeInScale extends CommandGroup
{
    private Drivebase drivebase = Robot.getInstance().getDrivebase();
    private boolean isOurColorLeft = true; //TODO: GET SOME REAL INFORMATION FOR THIS

    public PlaceCubeInScale()
    {

        double distanceFromLeftWall = SmartDashboard.getNumber("Distance from left wall", 0);

        super.addSequential(new DriveStraight(7.0, drivebase)); //are these numbers too magical? they're field constants

        //detects which side of the switch to place in
        if (isOurColorLeft)
        {
            super.addSequential(new TurnAngle(-90, drivebase));

            //Drive to be closer to the wall than the switch
            super.addSequential(new DriveStraight((distanceFromLeftWall - 4), drivebase));
            super.addSequential(new TurnAngle(90, drivebase));

        } else
        {
            super.addSequential(new TurnAngle(90, drivebase));
            super.addSequential(new DriveStraight(7.0, drivebase));

            //Drive to be closer to the wall than the switch
            super.addSequential(new DriveStraight((13.5 - distanceFromLeftWall), drivebase));
            super.addSequential(new TurnAngle(-90, drivebase));
        }
        super.addSequential(new DriveStraight(13, drivebase));

        if (isOurColorLeft)
        {
            super.addSequential(new TurnAngle(90, drivebase));
        } else
        {
            super.addSequential(new TurnAngle(-90, drivebase));
        }

        super.addSequential(new DriveStraight(3, drivebase));

        if (isOurColorLeft)
        {
            super.addSequential(new TurnAngle(-90, drivebase));
        } else
        {
            super.addSequential(new TurnAngle(90, drivebase));
        }

        super.addSequential(new DriveStraight(3, drivebase));

        //super.addSequential(raise elevator);
        //super.addSequential(eject cube);
        //super.addSequential(lower elevator);

    }
}




