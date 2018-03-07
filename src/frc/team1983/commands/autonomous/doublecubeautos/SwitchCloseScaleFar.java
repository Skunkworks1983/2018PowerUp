package frc.team1983.commands.autonomous.doublecubeautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

public class SwitchCloseScaleFar extends CommandGroup
{

    private Logger logger;
    public int reflectionVariable;

    public SwitchCloseScaleFar(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, GameDataPoller.OwnedSide robotPosition)
    {
        logger = LoggerFactory.createNewLogger(SwitchCloseScaleClose.class);

        super.addSequential(new CollectorRotate(collector, false));
        super.addSequential(new DriveStraight(drivebase, dashboard, 21.0));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 135 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3, Constants.PidConstants.DriveStraightPid.DEFAULT_BASE_SPEED, 1));
        super.addSequential(new CollectorExpel(collector, true, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, -1.0));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 1000000 * reflectionVariable)); //need to reorient to point at the cube we pushed out of the way
        super.addSequential(new DriveStraight(drivebase, dashboard, 2.0));
        super.addParallel(new CollectorIntake(collector));
        super.addSequential(new DriveStraight(drivebase, dashboard, -3.75));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 1000000 * reflectionVariable)); //need to reorient to drive parallel to the switch
        super.addSequential(new DriveStraight(drivebase, dashboard, 100000)); //drive to be close to the scale
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90 * reflectionVariable)); //need to reorient to point at the scale
        super.addSequential(new DriveStraight(drivebase, dashboard, 10000000)); //drive to get close enough to place in scale
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new CollectorExpel(collector, true, 1));





    }
}
