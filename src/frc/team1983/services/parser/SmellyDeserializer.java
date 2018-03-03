package frc.team1983.services.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import frc.team1983.util.path.PathComponent;

import java.io.IOException;

public class SmellyDeserializer extends JsonDeserializer <PathComponent>
{

    @Override
    public PathComponent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException
    {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);

        PathComponentType type = mapper.readValue(root.get("type").textValue(), PathComponentType.class);

        return mapper.readValue(root.asText(), type.getType());
    }
}
