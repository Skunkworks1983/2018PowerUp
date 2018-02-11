package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.ElevatorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.ElevatorPidOutput;
import org.apache.logging.log4j.core.Logger;

//This command controls the elevator with a PID. It should be the only class that
// sets the speed of the elevator winches
public class ElevatorControl extends CommandBase
{
    private PidControllerWrapper controller;
    private PIDSource pidIn;
    private PIDOutput pidOut;
    private StatefulDashboard dashboard;
    private String NAME;

    private Elevator elevator;
    private Logger logger;

    public ElevatorControl(Elevator elevator, StatefulDashboard dashboard)
    {
        this.elevator = elevator;
        this.dashboard = dashboard;
        logger = LoggerFactory.createNewLogger(ElevatorControl.class);

        NAME = Constants.DashboardConstants.ELEVATOR_CONTROL_NAME;

        dashboard.add(NAME, "P", Constants.DashboardConstants.DEFAULT_DOUBLE);
        dashboard.add(NAME, "I", Constants.DashboardConstants.DEFAULT_DOUBLE);
        dashboard.add(NAME, "D", Constants.DashboardConstants.DEFAULT_DOUBLE);
        dashboard.add(NAME, "F", Constants.DashboardConstants.DEFAULT_DOUBLE);

        pidIn = new ElevatorPidInput(elevator);
        pidOut = new ElevatorPidOutput(elevator);


    }

    @Override
    public void initialize()
    {
        controller = new PidControllerWrapper(dashboard.getDouble(NAME, "P"),
                                              dashboard.getDouble(NAME, "I"),
                                              dashboard.getDouble(NAME, "D"),
                                              dashboard.getDouble(NAME, "F"),
                                              pidIn, pidOut);
        controller.enable();
    }

    @Override
    //Get the 0-1 range of setpoint, scale it to the encoder range, and make it an integer
    public void execute()
    {
        controller.setP(dashboard.getDouble(NAME, "P"));
        controller.setI(dashboard.getDouble(NAME, "I"));
        controller.setD(dashboard.getDouble(NAME, "D"));
        controller.setF(dashboard.getDouble(NAME, "F"));

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
