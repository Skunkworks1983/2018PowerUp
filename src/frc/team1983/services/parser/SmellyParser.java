package frc.team1983.services.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.wpilibj.command.CommandGroup;
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
    private List<PathComponent> components;
    private File[] files;
    private File file;
    private Logger logger;
    private FilenameFilter filter;
    //This puts directly to smartdashboard because we actually don't want this to stick around, as files may change
    private DashboardWrapper dashboard;
    private ObjectMapper mapper = new ObjectMapper();

    public SmellyParser(DashboardWrapper dashboard, File dir)
    {
        this.dashboard = dashboard;
        logger = LoggerFactory.createNewLogger(this.getClass());
        components = new ArrayList<>();
        filter = (file, name) -> name.toLowerCase().endsWith(".json");
        dashboard.putNumber(Constants.SmellyParser.AUTO_PATH_KEY, 0.0);

        files = dir.listFiles(filter);
        if(files == null)
        {
            files = new File[1];
            files[0] = Constants.SmellyParser.DEFAULT_PATH;
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

            if(dashboard.getNumber(Constants.SmellyParser.AUTO_PATH_KEY, 0.0) < files.length) //validate input
            {
                file = files[dashboard.getNumber(Constants.SmellyParser.AUTO_PATH_KEY, 0.0).intValue()]; //get from dashboard
            }
            else
            {
                file = Constants.SmellyParser.DEFAULT_PATH; //If not valid, use default
            }
            logger.info("Using " + file);

            path = mapper.readValue(file, Path.class);
        }
        catch(IOException e)
        {
            logger.error("IOException {} when constructing path.", e);
        }
    }

    public Path getPath()
    {
        return path;
    }
}
