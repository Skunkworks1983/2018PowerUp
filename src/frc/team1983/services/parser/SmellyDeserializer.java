package frc.team1983.services.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import frc.team1983.Robot;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.subsystems.Drivebase;

import java.io.IOException;

public class SmellyDeserializer extends JsonDeserializer <DriveProfile>
{
    @Override
    public DriveProfile deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException
    {
        Robot robot = Robot.getInstance();

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);

        InjectableValues inject = new InjectableValues.Std().addValue(Drivebase.class, robot.getDrivebase());

        PathComponentType type = PathComponentType.valueOf(root.get("type").textValue());

        //mapper.setInjectableValues(inject);

        return mapper.reader(inject).forType(type.getType()).readValue(root.toString());
    }
}
