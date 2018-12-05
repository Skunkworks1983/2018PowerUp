package frc.team1983.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.team1983.Constants;
import frc.team1983.Robot;
import frc.team1983.services.OI;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Elevator;

public class Manual extends CommandBase
{
    private Elevator elevator;
    private Collector collector;
    private OI oi;

    public Manual(Elevator elevator, Collector collector, OI oi)
    {
        requires(elevator);
        requires(collector);

        this.elevator = elevator;
        this.collector = collector;
        this.oi = oi;
    }

    public Manual()
    {
        this(Robot.getInstance().getElevator(), Robot.getInstance().getCollector(), Robot.getInstance().getOI());
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        if(oi.getButton(Constants.OIMap.PANEL, Constants.OIMap.MANUAL_ELEVATOR_UP).get())
            elevator.set(ControlMode.PercentOutput, Constants.MANUAL_ELEVATOR_UP_THROTTLE);
        else if(oi.getButton(Constants.OIMap.PANEL, Constants.OIMap.MANUAL_ELEVATOR_DOWN).get())
            elevator.set(ControlMode.PercentOutput, Constants.MANUAL_ELEVATOR_DOWN_THROTTLE);
        else
            elevator.set(ControlMode.PercentOutput, 0);

        if(oi.getButton(Constants.OIMap.PANEL, Constants.OIMap.MANUAL_COLLECTOR_UP).get())
            collector.setWrist(ControlMode.PercentOutput, Constants.MANUAL_COLLECTOR_DOWN_THROTTLE);
        else if(oi.getButton(Constants.OIMap.PANEL, Constants.OIMap.MANUAL_COLLECTOR_DOWN).get())
            collector.setWrist(ControlMode.PercentOutput, Constants.MANUAL_COLLECTOR_UP_THROTTLE);
        else
            collector.setWrist(ControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void interrupted()
    {
        end();
    }

    @Override
    public void end()
    {
        elevator.set(ControlMode.PercentOutput, 0);
        collector.setWrist(ControlMode.PercentOutput, 0);
    }
}
