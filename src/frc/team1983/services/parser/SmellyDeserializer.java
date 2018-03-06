package frc.team1983.services.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.util.path.PathComponent;

import java.io.IOException;

public class SmellyDeserializer extends JsonDeserializer <DriveProfile>
{
    @Override
    public DriveProfile deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException
    {
        //Robot robot = Robot.getInstance();
        //ActionFactory actionFactory = new ActionFactory(robot.getCollector(), robot.getElevator(), robot.getOI());

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);

        InjectableValues inject = new InjectableValues.Std().addValue(Drivebase.class, Robot.getInstance().getDrivebase());

        PathComponentType type = PathComponentType.valueOf(root.get("type").textValue());

        mapper.setInjectableValues(inject);

        return mapper.reader(inject).forType(type.getType()).readValue(root.toString());
    }

    public class ActionFactory
    {
        Collector collector;
        Elevator elevator;
        OI oi;

        ActionFactory(Collector collector, Elevator elevator, OI oi)
        {
            this.collector = collector;
            this.elevator = elevator;
            this.oi = oi;
        }

        public CommandBase getAction(PathComponent.Action action)
        {
            switch(action)
            {
                case INTAKE:
                    return new CollectorIntake(collector, 0);
                case EXPEL:
                    //TODO: Decide whether shoot should be an option
                    return new CollectorExpel(collector, false, 0);
                case BOTTOM:
                    return new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator);
                case SWITCH:
                    return new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator);
                case SCALE:
                    return new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator);
                default:
                    return null;
            }
        }
    }
}
