package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.ElevatorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.ElevatorPidOutput;

import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_BOOLEAN;
import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_DOUBLE;
import static frc.team1983.settings.Constants.DashboardConstants.ELEVATOR_CONTROL_NAME;

//This command controls the elevator with a PID. It should be the only class that
// sets the speed of the elevator winches
public class ElevatorControl extends CommandBase
{
    private PidControllerWrapper controller;
    private PIDSource pidIn;
    private PIDOutput pidOut;
    private StatefulDashboard dashboard;

    private Elevator elevator;

    public ElevatorControl(Elevator elevator, StatefulDashboard dashboard)
    {
        this.elevator = elevator;
        this.dashboard = dashboard;

        dashboard.add(ELEVATOR_CONTROL_NAME, "P", DEFAULT_DOUBLE);
        dashboard.add(ELEVATOR_CONTROL_NAME, "I", DEFAULT_DOUBLE);
        dashboard.add(ELEVATOR_CONTROL_NAME, "D", DEFAULT_DOUBLE);
        dashboard.add(ELEVATOR_CONTROL_NAME, "F", DEFAULT_DOUBLE);

        pidIn = new ElevatorPidInput(elevator);
        pidOut = new ElevatorPidOutput(elevator);


    }

    @Override
    public void initialize()
    {
        controller = new PidControllerWrapper(dashboard.getDouble(ELEVATOR_CONTROL_NAME, "P"),
                                              dashboard.getDouble(ELEVATOR_CONTROL_NAME, "I"),
                                              dashboard.getDouble(ELEVATOR_CONTROL_NAME, "D"),
                                              dashboard.getDouble(ELEVATOR_CONTROL_NAME, "F"),
                                              pidIn, pidOut);
        controller.enable();
    }

    @Override
    //Get the 0-1 range of setpoint, scale it to the encoder range, and make it an integer
    public void execute()
    {
        controller.setP(dashboard.getDouble(ELEVATOR_CONTROL_NAME, "P"));
        controller.setI(dashboard.getDouble(ELEVATOR_CONTROL_NAME, "I"));
        controller.setD(dashboard.getDouble(ELEVATOR_CONTROL_NAME, "D"));
        controller.setF(dashboard.getDouble(ELEVATOR_CONTROL_NAME, "F"));

        controller.setSetpoint((int) elevator.getSetpoint());
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
        controller.disable();
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
