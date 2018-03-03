package frc.team1983.util.path;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.settings.Constants;

abstract public class PathComponent
{
    protected double time, delay;
    protected String type;

    public enum Action {
        @JsonProperty("action")
        INTAKE,
        EXPEL,
        SWITCH,
        SCALE
    }

    public PathComponent()
    {
    }

    public double getTime()
    {
        return time;
    }

    public void setTime(double time)
    {
        this.time = time;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public CommandBase getAction()
    {
        Robot robot = Robot.getInstance();
        if(action == "intake")
        {
            return new CollectorIntake(robot.getCollector());
        }
        else if(action == "expel")
        {
            //TODO: decide whether we want shoot or not
            return new CollectorExpel(robot.getCollector(), false);
        }
        else if(action == "switch")
        {
            return new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, robot.getElevator(), robot.getOI());
        }
        else if(action == "scale")
        {
            return new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, robot.getElevator(), robot.getOI());
        }
        else
        {
            return null;
        }
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public double getDelay()
    {
        return delay;
    }

    public void setDelay(double delay)
    {
        this.delay = delay;
    }

    abstract public Command getCommand();
}
