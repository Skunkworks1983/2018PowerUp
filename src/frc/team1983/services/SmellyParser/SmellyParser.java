package frc.team1983.services.SmellyParser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.util.path.Path;
import frc.team1983.util.path.PathArc;
import frc.team1983.util.path.PathComponent;
import org.apache.logging.log4j.core.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmellyParser
{
    private ObjectMapper objectMapper;
    private Path path;
    private List<PathComponent> components;
    private File[] files;
    private File file;
    private Logger logger;
    //This puts directly to smartdashboard because we actually don't want this to stick around, as files may change
    private DashboardWrapper dashboard;

    public SmellyParser(DashboardWrapper dashboard)
    {
        this.dashboard = dashboard;
        logger = LoggerFactory.createNewLogger(this.getClass());
        objectMapper = new ObjectMapper();
        components = new ArrayList<>();
        FilenameFilter filter = (file, name) -> name.toLowerCase().endsWith(".json");
        SmartDashboard.putNumber(Constants.SmellyParser.AUTOPATHKEY, 0.0);

        files = Constants.SmellyParser.SMELLYFOLDER.listFiles(filter);
        if(files == null)
        {
            files = new File[1];
            files[0] = Constants.SmellyParser.DEFAULTPATH;
            logger.error("Found no json files on usb drive, using default path");
        }

        for(int i = 0; i < files.length; i++)
        {
            SmartDashboard.putString("For SmellyPath " + files[i].toString().split(".json")[0] + " use " + i, "");
        }
    }

    public void constructPath()
    {
        try
        {
            components.clear();

            file = new File("/u/" + files[
                    dashboard.getNumber(Constants.SmellyParser.AUTOPATHKEY, 0.0).intValue()] + ".json");
            logger.info("Using " + file);

            Map<String, String> fileMap = objectMapper.readValue(file, new TypeReference<Map<String,String>>(){});
            List<ControlPoint> controlPoints = objectMapper.readValue(fileMap.get("points"),
                                                                      new TypeReference<List<ControlPoint>>(){});

            for(ControlPoint controlPoint : controlPoints)
            {
                if(controlPoint.getType().equals("arc"))
                {
                    components.add(new PathArc());//components of a patharc
                }
                if(controlPoint.getType().equals("straight"))
                {
                    components.add(new PathArc());//components of a pathsegment
                }
            }
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
