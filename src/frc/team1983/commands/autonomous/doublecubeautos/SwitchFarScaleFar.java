package frc.team1983.commands.autonomous.doublecubeautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public class SwitchFarScaleFar extends CommandGroup
{

    public SwitchFarScaleFar(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, GameDataPoller.OwnedSide robotPosition)
    {
        super.addSequential(new DriveStraight(drivebase, dashboard, 21));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        super.addSequential(new DriveStraight(drivebase, dashboard, 1000000000));
        super.addSequential(new DriveStraight(drivebase, dashboard, 10000));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new CollectorExpel(collector, true, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, -1));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3));
        super.addParallel(new CollectorIntake(collector));
        super.addSequential(new DriveStraight(drivebase, dashboard, -3));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 180));
        super.addSequential(new DriveStraight(drivebase, dashboard, 1000000));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new CollectorExpel(collector, true, 1));
    }
}