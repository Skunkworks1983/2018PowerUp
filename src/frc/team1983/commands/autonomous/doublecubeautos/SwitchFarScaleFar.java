package frc.team1983.commands.autonomous.doublecubeautos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.drivebase.DifferentialTurnAngle;
import frc.team1983.commands.drivebase.DriveStraight;
import  frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

import static frc.team1983.settings.Constants.OIMap.ElevatorButtons.SWITCH;

public class SwitchFarScaleFar extends CommandGroup
{
    public int reflectionVariable;

    public SwitchFarScaleFar(Drivebase drivebase, StatefulDashboard dashboard, OI oi, Elevator elevator, Collector collector, GameDataPoller.OwnedSide robotPosition)
    {
        if (robotPosition == GameDataPoller.OwnedSide.LEFT) { reflectionVariable = 1; }
        else { reflectionVariable = -1; }

        //JOURNEY TO THE CENTER OF THE FIELD
        super.addSequential(new DriveStraight(drivebase, dashboard, 21.0));
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 90.0 * reflectionVariable));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 16.75));

        //SCALE LINEUP
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, -90.0 * reflectionVariable));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, 4.0, .35));

        //SHOOT N' SCOOT
        super.addSequential(new CollectorExpel(collector, true, 1));
        super.addParallel(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator, oi));
        super.addSequential(new DriveStraight(drivebase, dashboard, -4.0, .35));

        //SWITCH APPROACH & CUBE COLLECT
        super.addSequential(new DifferentialTurnAngle(drivebase, dashboard, 180));
        super.addParallel(new CollectorIntake(collector));
        super.addSequential(new DriveStraight(drivebase, dashboard, 3.0, .35));

        //TWO KEWB AUTO BB
        super.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator, oi));
        super.addParallel(new CollectorExpel(collector, true, 1));
        super.addSequential(new DriveStraight(drivebase, dashboard, 1, .35));

    }
}