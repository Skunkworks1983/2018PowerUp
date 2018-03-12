package frc.team1983.commands.autonomous.doublecubeautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
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

//RUN THIS IF
//switchClose == true && scaleClose == true
//TODO: make this for the other side of the field too
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
        super.addSequential(new CollectorRotate(collector, false));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 9.0));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90.0 * reflectionVariable, 1));

        //SHOOT N' SCOOT
        super.addSequential(new DriveStraight(drivebase, dashboard, 1.5));
        super.addParallel(new CollectorExpel(collector, true, 1));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, -1.0));

        //CUBE LINEUP
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90.0 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, -5.0));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -50.0 * reflectionVariable));

        //CUBE APPROACH/COLLECT
        super.addParallel(new CollectorIntake(collector));
        super.addSequential(new DriveStraight(drivebase, dashboard, 5.0, .2));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, -5.0));

        //SCALE LINEUP
        super.addSequential(new WaitCommand(1));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90.0 * reflectionVariable));


        //SHOOT N' SCOOT
        super.addSequential(new DriveStraight(drivebase, dashboard, 1));
        super.addSequential(new CollectorExpel(collector, true, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, -4.0));
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        /*
        super.addSequential(new CollectorRotate(collector, false));
        super.addSequential(new DriveStraight(drivebase, dashboard, 21.0));
        super.addParallel  (new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 135 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, 2, Constants.PidConstants.DriveStraightPid.DEFAULT_BASE_SPEED, 1));
        super.addSequential(new CollectorExpel(collector, true, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, -2.0));
        super.addParallel  (new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -15 * reflectionVariable)); //need to reorient to point at the cube we pushed out of the way
        super.addSequential(new DriveStraight(drivebase, dashboard, 3.0));
        super.addParallel(new CollectorIntake(collector));
        super.addSequential(new DriveStraight(drivebase, dashboard, -3.75));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90 * reflectionVariable));
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 2.1));
        super.addSequential(new CollectorExpel(collector, true));
        super.addSequential(new DriveStraight(drivebase, dashboard, -2.1));
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.LOW, elevator, oi));
*/
    }
}
