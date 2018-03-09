package frc.team1983.commands.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.subsystems.Drivebase;


public class ClimbActivate extends Command {
    private Drivebase drivebase;


    public ClimbActivate(Drivebase drivebase) {
        requires(drivebase);
        this.drivebase = drivebase;
    }

    @Override
    protected void initialize() {

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
