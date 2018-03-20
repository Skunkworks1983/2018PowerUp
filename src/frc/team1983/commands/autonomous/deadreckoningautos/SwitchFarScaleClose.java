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

public class SwitchFarScaleClose extends CommandGroup
{

    private int reflectionVariable;
    private Logger logger;

    public SwitchFarScaleClose(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, AutoManager.OwnedSide robotPosition)
    {
        logger = LoggerFactory.createNewLogger(SwitchCloseScaleClose.class);
        logger.info("Robot is on the {}", robotPosition);
        if (robotPosition == AutoManager.OwnedSide.LEFT) { reflectionVariable = 1; }
        else { reflectionVariable = -1; }
        logger.info("Reflection variable is {}", reflectionVariable);

        //SWITCH APPROACH & DROPOFF
        super.addSequential(new CollectorRotate(collector, Constants.PidConstants.CollectorRotate.DOWN_TICKS));
        super.addSequential(new DriveStraight(drivebase, dashboard, 16, 0.75));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TRAVEL, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        super.addSequential(new DriveStraight(drivebase, dashboard, 12, 0.75)); //??
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        super.addSequential(new DriveStraight(drivebase, dashboard, 1, 0.25, 0.5));
        super.addSequential(new CollectorExpel(collector, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED, 0.5));

        //CUBE PICKUP
        super.addSequential(new DriveStraight(drivebase, dashboard, -3));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addParallel(new CollectorIntake(collector, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3));

        //WE GO FOR THE SCALE BUT WE RUN OUT OF TIME
        super.addSequential(new DriveStraight(drivebase, dashboard, -3));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        super.addSequential(new DriveStraight(drivebase, dashboard, 11));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3));
        super.addSequential(new CollectorExpel(collector, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED, 0.5));



        // less old version but it's still wrong. pretty good scale dropoff tho. maybe make this the elims auto? if so maybe 2 cube scale auto?
        /*
        //SCALE APPROACH Y DROPOFF
        super.addSequential(new CollectorRotate(collector, false));
        super.addSequential(new DriveStraight(drivebase, dashboard, 15, 0.75)); //I dunno what any of the numbers in this block actually work out to
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TRAVEL, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        super.addSequential(new DriveStraight(drivebase, dashboard, 2));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90));
        super.addSequential(new WaitCommand(1));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 1));
        super.addSequential(new CollectorExpel(collector, 1, 1));

        //MAKING MY WAY DOWNTOWN
        super.addSequential(new DriveStraight(drivebase, dashboard, -.75));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, 12, 0.75)); //runs out of time halfway thru this
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90 * reflectionVariable));

        //CUBE PICKUP
        super.addParallel(new CollectorIntake(collector, false));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3, 0.2));
        super.addSequential(new DriveStraight(drivebase, dashboard, -3));

        //CUBE DROPOFF
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 5));
        super.addSequential(new CollectorExpel(collector, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED));

        //ok so this just goes to the far switch rn
        /* old version
        super.addSequential(new CollectorRotate(collector, false));
        super.addSequential(new DriveStraight(drivebase, dashboard, 21.0));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, 12, Constants.PidConstants.DriveStraightPid.DEFAULT_BASE_SPEED, 1));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3, Constants.PidConstants.DriveStraightPid.DEFAULT_BASE_SPEED, 1));
        super.addSequential(new CollectorExpel(collector, true, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, -1.0));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        */

    }
}
