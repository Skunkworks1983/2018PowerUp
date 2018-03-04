package frc.team1983.util.path;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.services.parser.PathComponentType;
import frc.team1983.services.parser.SmellyDeserializer;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.settings.Constants;

@JsonDeserialize(using = SmellyDeserializer.class)
abstract public class PathComponent
{
    protected double time, delay;
    protected PathComponentType type;
    protected Action action;

    public enum Action {
        NONE,
        INTAKE,
        EXPEL,
        SWITCH,
        SCALE,
        BOTTOM
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

    public PathComponentType getType()
    {
        return type;
    }

    public void setType(PathComponentType type)
    {
        this.type = type;
    }

    public CommandBase getAction()
    {
        Robot robot = Robot.getInstance();
        if(action == Action.INTAKE)
        {
            return new CollectorIntake(robot.getCollector());
        }
        else if(action == Action.EXPEL)
        {
            //TODO: decide whether we want shoot or not
            return new CollectorExpel(robot.getCollector(), false);
        }
        else if(action == Action.SWITCH)
        {
            return new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, robot.getElevator(), robot.getOI());
        }
        else if(action == Action.SCALE)
        {
            return new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, robot.getElevator(), robot.getOI());
        }
        else if(action == Action.BOTTOM)
        {
            return new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, robot.getElevator(), robot.getOI());
        }
        else
        {
            return null;
        }
    }

    public void setAction(Action action)
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
