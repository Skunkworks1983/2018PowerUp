package frc.team1983.commands.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Climber;
import frc.team1983.subsystems.Drivebase;


public class Climb extends Command {
    private Climber climber;
    private Drivebase drivebase;


    public Climb(Climber climber, Drivebase drivebase) {
        requires(climber);
        requires(drivebase);
        this.climber = climber;
        this.drivebase = drivebase;
    }

    @Override
    protected void initialize() {
        climber.engage();
        climber.jackUp();
    }

    @Override
    protected void execute() {
        drivebase.setLeft(ControlMode.PercentOutput, 1);
        drivebase.setRight(ControlMode.PercentOutput, 1);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        drivebase.setLeft(ControlMode.PercentOutput, 0);
        drivebase.setRight(ControlMode.PercentOutput, 0);
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
