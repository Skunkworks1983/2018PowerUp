package frc.team1983.commands.autonomous.deadreckoningautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
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

    public SwitchCloseScaleFar(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, AutoManager.OwnedSide robotPosition)
    {
        logger = LoggerFactory.createNewLogger(SwitchCloseScaleClose.class);

        //SWITCH APPROACH
        super.addSequential(new CollectorRotate(collector, false));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 9.0));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90.0 * reflectionVariable, 1));

        //SHOOT N' SCOOT
        super.addSequential(new DriveStraight(drivebase, dashboard, 1.5));
        super.addParallel(new CollectorExpel(collector, 1, 1));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, -.50));

        //JOURNEY TO THE MIDDLE
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90.0 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, -5.0));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90.0));
        super.addSequential(new DriveStraight(drivebase, dashboard, 16.75));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90.0));

        //CUBE COLLECT
        super.addParallel(new CollectorIntake(collector, false));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3.0, .35));
        super.addSequential(new DriveStraight(drivebase, dashboard, -3.0));

        //SCALE APPROACH
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 180));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new CollectorExpel(collector, true, 1));



    }
}
