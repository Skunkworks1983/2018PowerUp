package frc.team1983.commands.autonomous.deadreckoningautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
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

public class SwitchCloseScaleClose extends CommandGroup
{
    private Logger logger;
    public int reflectionVariable;


    public SwitchCloseScaleClose(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, AutoManager.OwnedSide robotPosition)
    {
        logger = LoggerFactory.createNewLogger(SwitchCloseScaleClose.class);

        if (robotPosition == AutoManager.OwnedSide.LEFT) { reflectionVariable = 1; }
        else { reflectionVariable = -1; }
        logger.info("Reflection variable is {}", reflectionVariable);

        //SWITCH APPROACH
        super.addSequential(new DriveStraight(drivebase, dashboard, -10.0, .75));
        //super.addParallel(new CollectorRotate(collector, false));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90.0 * reflectionVariable, 1));

        //SHOOT N' SCOOT
        super.addSequential(new DriveStraight(drivebase, dashboard, 2, .4));
        super.addParallel(new CollectorExpel(collector, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED, 1));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, -1.0, .75));

        //CUBE LINEUP
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90.0 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, -3, .75));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -45.0 * reflectionVariable));

        //CUBE APPROACH/COLLECT
        super.addParallel(new CollectorIntake(collector, false));
        super.addSequential(new DriveStraight(drivebase, dashboard, 5.0, .2));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, -5.0, .75));

        //SCALE LINEUP
        super.addSequential(new WaitCommand(1));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90.0 * reflectionVariable));


        //SHOOT N' SCOOT
        super.addSequential(new DriveStraight(drivebase, dashboard, 1, .5));
        super.addSequential(new CollectorExpel(collector, Constants.MotorSetpoints.COLLECTOR_EXPEL_SPEED, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, -4.0, .75));
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
    }
}
