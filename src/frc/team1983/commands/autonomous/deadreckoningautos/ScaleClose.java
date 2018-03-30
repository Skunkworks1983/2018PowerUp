package frc.team1983.commands.autonomous.deadreckoningautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.drivebase.deadreckoning.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.deadreckoning.DriveStraight;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public class ScaleClose extends CommandGroup
{

    private int reflectionVariable;
    public ScaleClose(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, AutoManager.OwnedSide robotPosition)
    {
        if (robotPosition == AutoManager.OwnedSide.LEFT) { reflectionVariable = 1; }
        else { reflectionVariable = -1; }

        super.addSequential(new DriveStraight(drivebase, dashboard, 15));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 45 * reflectionVariable));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3, 0.25));
        super.addSequential(new CollectorExpel(collector, 1));
    }
}