package frc.team1983.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.util.path.Path;
import frc.team1983.util.path.PathComponent;
import frc.team1983.util.path.PathTanarc;
import frc.team1983.util.path.PathTanline;
import org.apache.logging.log4j.core.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SmellyParser
{
    private Path path;
    private PathComponent newComponent;
    private List<PathComponent> components;
    private HashMap<String, Object> jsonComponent;
    private File[] files;
    private File file;
    private Logger logger;
    private String fieldName;
    private FilenameFilter filter;
    private JsonFactory jsonFactory;
    private JsonParser parser;
    //This puts directly to smartdashboard because we actually don't want this to stick around, as files may change
    private DashboardWrapper dashboard;

    public SmellyParser(DashboardWrapper dashboard, File dir)
    {
        this.dashboard = dashboard;
        logger = LoggerFactory.createNewLogger(this.getClass());
        components = new ArrayList<>();
        filter = (file, name) -> name.toLowerCase().endsWith(".json");
        dashboard.putNumber(Constants.SmellyParser.AUTOPATHKEY, 0.0);

        files = dir.listFiles(filter);
        if(files == null)
        {
            files = new File[1];
            files[0] = Constants.SmellyParser.DEFAULTPATH;
            logger.error("Found no json files on usb drive, using default path");
        }

        for(int i = 0; i < files.length; i++)
        {
            dashboard.putNumber(files[i].getName() + " use ", (double) i);
            logger.info("Found " + files[i]);
        }
    }

    public void constructPath()
    {
        try
        {
            components.clear(); //clear so we are recording a new path

            if(dashboard.getNumber(Constants.SmellyParser.AUTOPATHKEY, 0.0) < files.length) //validate input
            {
                file = files[dashboard.getNumber(Constants.SmellyParser.AUTOPATHKEY,0.0).intValue()]; //get from dashboard
            }
            else
            {
                file = Constants.SmellyParser.DEFAULTPATH; //If not valid, use default
            }
            logger.info("Using " + file);

            jsonFactory = new JsonFactory();
            parser = jsonFactory.createParser(file);

            parser.nextToken(); //Get to first field, which is start of object
            parser.nextToken(); //Get to first key
            logger.trace("first key is: " + parser.getValueAsString());

            if(!parser.currentName().equals("points")) //is meta-data
            {
            }
            else
            {
                parser.nextToken(); //Get to start of value, which is start_array
                JsonToken token = parser.nextToken(); //get to start of next object
                while(!JsonToken.END_ARRAY.equals(token)) //Is start_object, not end_array
                {
                    logger.trace("Start of object, hopefully: " + JsonToken.START_OBJECT.equals(token));
                    parser.nextToken(); //get to first key, which we know is "type"
                    logger.trace("First key: " + parser.getValueAsString());
                    parser.nextToken(); //get to the type value
                    logger.trace("First value: " +parser.getValueAsString());

                    jsonComponent = new HashMap<>();
                    jsonComponent.put("type", parser.getValueAsString());

                    token = parser.nextToken();

                    while(!JsonToken.END_OBJECT.equals(token))
                    {
                        logger.trace("Next key is: " + parser.getValueAsString());
                        fieldName = parser.getCurrentName(); //save key
                        token = parser.nextToken(); //get to value

                        Object value;
                        switch(token)
                        {
                            case VALUE_NUMBER_FLOAT:
                            case VALUE_NUMBER_INT:
                                value = parser.getValueAsDouble();
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                                value = parser.getValueAsBoolean();
                                break;
                            case VALUE_STRING:
                                value = parser.getValueAsString();
                                break;
                            default:
                                value = "could not get object type";
                        }

                        jsonComponent.put(fieldName, value);
                        logger.trace("object includes " + fieldName + ":" + value);

                        token = parser.nextToken(); //get to next key
                    }
                    logger.trace("end of object");

                    switch((String) jsonComponent.get("type"))
                    {
                        case "tanline":
                            newComponent = new PathTanline();
                            ((PathTanline) newComponent).setDistance((double) jsonComponent.get("distance"));
                            break;
                        case "tanarc":
                            newComponent = new PathTanarc();
                            ((PathTanarc) newComponent).setAngle((double) jsonComponent.get("angle"));
                            ((PathTanarc) newComponent).setRadius((double) jsonComponent.get("radius"));
                            ((PathTanarc) newComponent).setRight((boolean) jsonComponent.get("isRight"));
                            break;
                        default:
                            logger.error("Type {} cannot be parsed yet!", jsonComponent.get("type"));
                            break;
                    }

                    newComponent.setType((String) jsonComponent.get("type"));
                    newComponent.setAction((String) jsonComponent.get("action"));
                    newComponent.setTime((double) jsonComponent.get("time"));
                    newComponent.setDelay((double) jsonComponent.get("delay"));

                    components.add(newComponent);

                    token = parser.nextToken();
                }
            }
            parser.close();
            path = new Path(components);
        }
        catch(IOException e)
        {
            logger.error("Exception when constructing path");
            logger.error(e);
        }
    }

    public Path getPath()
    {
        return path;
    }
}
