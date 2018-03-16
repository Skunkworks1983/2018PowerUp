package frc.team1983.commands.autonomous.deadreckoningautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorRotate;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import org.apache.logging.log4j.core.Logger;

public class MidToSwitch extends CommandGroup
{

    private Logger logger;
    private int reflectionVariable;
    public MidToSwitch(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, AutoManager.OwnedSide robotPosition)
    {
        if (robotPosition == AutoManager.OwnedSide.LEFT) { reflectionVariable = 1; }
        else { reflectionVariable = -1; }

        //EVERYTHING
        super.addParallel(new CollectorRotate(collector, Constants.PidConstants.CollectorRotate.DOWN_TICKS));
        super.addSequential(new DriveStraight(drivebase, dashboard, -5.));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90. * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, -5.));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90. * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3.));
        super.addSequential(new CollectorExpel(collector, -0.5));
    }
}
