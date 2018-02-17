package frc.team1983.commands.collector;

import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.utilities.PidControllerWrapper;
import frc.team1983.subsystems.utilities.inputwrappers.CollectorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.CollectorPidOutput;

public class CollectorRotate extends CommandBase
{
    //If not up, assume down. Later, may implement a 45 degree position.
    private boolean up;
    private Collector collector;

    private PidControllerWrapper controller;
    private CollectorPidInput input;
    private CollectorPidOutput output;

    private double setpoint;

    public CollectorRotate(Collector collector, boolean up)
    {
        this.up = up;
        this.collector = collector;

        controller = new PidControllerWrapper(Constants.PidConstants.CollectorRotate.P,
                                              Constants.PidConstants.CollectorRotate.I,
                                              Constants.PidConstants.CollectorRotate.D,
                                              Constants.PidConstants.CollectorRotate.F,
                                              input, output);
        controller.disable();

        if(up)
        {
            setpoint = Constants.PidConstants.CollectorRotate.UP_TICKS;
        }
        else
        {
            setpoint = Constants.PidConstants.CollectorRotate.DOWN_TICKS;
        }
    }

    @Override
    public void initialize()
    {
        controller.setSetpoint(setpoint);
        controller.enable();
    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
    }

    @Override
    public void interrupted()
    {
    }


}
