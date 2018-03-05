package frc.team1983.commands.autonomous.doublecubeautos;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.SimpleTurnAngle;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

import static frc.team1983.settings.Constants.OIMap.ElevatorButtons.SWITCH;
import static frc.team1983.settings.Constants.OIMap.Setpoint.TOP;

//RUN THIS IF
//switchClose == true && scaleClose == true
//TODO: make this for the other side of the field too
public class SwitchCloseScaleClose extends CommandGroup
{
    private Logger logger;

    public SwitchCloseScaleClose(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector)
    {
        logger = LoggerFactory.createNewLogger(SwitchCloseScaleClose.class);

        super.addSequential(new CollectorRotate(collector, false));
        super.addSequential(new DriveStraight(dashboard, 21.0, drivebase));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(dashboard, 135, drivebase));
        super.addSequential(new DriveStraight(dashboard, 3.75, drivebase));
        super.addSequential(new CollectorExpel(collector, true));
        super.addSequential(new DriveStraight(dashboard, -1.0, drivebase));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DriveStraight(dashboard, 2.0, drivebase));
        super.addParallel(new CollectorIntake(collector));
        super.addSequential(new DriveStraight(dashboard, -3.75, drivebase));
        super.addSequential(new DifferentialTurnAngle(dashboard, -90, drivebase));
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new DriveStraight(dashboard, 2.1, drivebase));
        super.addSequential(new CollectorExpel(collector, true));
        super.addSequential(new DriveStraight(dashboard, -2.1, drivebase));
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.LOW, elevator, oi));
    }
}
