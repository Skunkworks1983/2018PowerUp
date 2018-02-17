package frc.team1983.services.SmellyParser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.util.path.Path;
import frc.team1983.util.path.PathTanarc;
import frc.team1983.util.path.PathComponent;
import org.apache.logging.log4j.core.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SmellyParser
{
    private ObjectMapper objectMapper;
    private Path path;
    private List<PathComponent> components;
    private File[] files;
    private File file, dir;
    private Logger logger;
    //This puts directly to smartdashboard because we actually don't want this to stick around, as files may change
    private DashboardWrapper dashboard;

    public SmellyParser(DashboardWrapper dashboard, File dir)
    {
        this.dashboard = dashboard;
        this.dir = dir;
        logger = LoggerFactory.createNewLogger(this.getClass());
        objectMapper = new ObjectMapper();
        components = new ArrayList<>();
        FilenameFilter filter = (file, name) -> name.toLowerCase().endsWith(".json");
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
            dashboard.putString("For SmellyPath " + files[i].toString().split(".json")[0] + " use " + i, "");
        }
    }

    public void constructPath()
    {
        try
        {
            components.clear();

            file = new File(files[
                    dashboard.getNumber(Constants.SmellyParser.AUTOPATHKEY, 0.0).intValue()].toString());
            logger.info("Using " + file);

            JsonFactory jsonFactory = new JsonFactory();
            JsonParser parser = jsonFactory.createParser(file);

            while(!parser.isClosed())
            {
                JsonToken token = parser.nextToken();
                if(JsonToken.FIELD_NAME.equals(token))
                {
                    String fieldName = token.asString();

                    token = parser.nextToken();

                    if(!JsonToken.START_ARRAY.equals(token)) //is meta-data
                    {
                    }
                    else if(JsonToken.START_ARRAY.equals(token))//is control points
                    {

                    }
                }
            }
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
