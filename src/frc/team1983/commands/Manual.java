package frc.team1983.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.commands.collector.SetRotateSpeed;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Elevator;


public class Manual extends Command
{
    private OI oi;
    private Collector collector;
    private Elevator elevator;

    public Manual(OI oi, Collector collector, Elevator elevator)
    {

        this.oi = oi;
        this.collector = collector;
        this.elevator = elevator;

        requires(collector);
        requires(elevator);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_INTAKE))
        {
            collector.setLeft(ControlMode.PercentOutput, 0.5);
            collector.setRight(ControlMode.PercentOutput, 0.5);
        }
        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_EXPEL))
        {
            collector.setLeft(ControlMode.PercentOutput, -0.5);
            collector.setRight(ControlMode.PercentOutput, -0.5);
        }
        if(!oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_INTAKE) &&
                !oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_EXPEL))
        {
            collector.setLeft(ControlMode.PercentOutput, 0);
            collector.setRight(ControlMode.PercentOutput, 0);
        }

        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_UP))
        {
            collector.setRotate(ControlMode.PercentOutput, -0.6);
        }
        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_DOWN))
        {
            collector.setRotate(ControlMode.PercentOutput, .3);
        }
        if(!oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_DOWN) &&
                        !oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.CollectorButtons.M_UP))
        {
            collector.setRotate(ControlMode.PercentOutput, 0.0);
        }

        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.M_UP))
        {
            elevator.set(ControlMode.PercentOutput, 0.6);
        }
        if(oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.M_DOWN))
        {
            elevator.set(ControlMode.PercentOutput, -0.4);
        }
        if(!oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.M_UP) &&
                !oi.isDown(Constants.OIMap.Joystick.PANEL, Constants.OIMap.ElevatorButtons.M_DOWN))
        {
            elevator.set(ControlMode.PercentOutput, 0.0);
        }
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        elevator.set(ControlMode.PercentOutput, 0.0);
        collector.setRotate(ControlMode.PercentOutput, 0.0);
        collector.setRight(ControlMode.PercentOutput, 0.0);
        collector.setLeft(ControlMode.PercentOutput, 0.0);
    }

    @Override
    protected void interrupted()
    {
        super.interrupted();
    }
}
