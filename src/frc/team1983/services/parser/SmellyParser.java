package frc.team1983.services.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.utility.path.Path;
import org.apache.logging.log4j.core.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SmellyParser
{
    private Path path;
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
        filter = (file, name) -> name.toLowerCase().endsWith(".json");
        //dashboard.putNumber(Constants.SmellyParser.AUTO_PATH_KEY, 0.0);

        files = dir.listFiles(filter);
        if(files == null)
        {
            files = new File[1];
            //files[0] = Constants.SmellyParser.DEFAULT_PATH;
            logger.error("Found no json files on usb drive, using default path");
        }

        for(int i = 0; i < files.length; i++)
        {
            dashboard.putNumber(files[i].getName() + " use ", (double) i);
            logger.info("Found " + files[i]);
        }
    }

    public Path constructPath(File file)
    {
        logger.info("Using " + file);

        try
        {
            logger.info(mapper.readTree(file).get("points").toString());
            DriveProfile[] points = mapper.readValue(mapper.readTree(file).get("points").toString(),
                                                           new TypeReference<DriveProfile[]>()
                                                           {
                                                           });

            logger.info("Json deserialized");
            return new Path(new ArrayList<>(Arrays.asList(points)));
        }
        catch(IOException e)
        {
            logger.error("uhoh", e);
        }
        return new Path(new ArrayList<>());
    }
}
