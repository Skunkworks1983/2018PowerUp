package frc.team1983.services.SmellyParser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.util.path.Path;
import frc.team1983.util.path.PathArc;
import frc.team1983.util.path.PathComponent;

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
    //This puts directly to smartdashboard because we actually don't want this to stick around, as files may change
    private DashboardWrapper dashboard;

    //Things to put in constants
    public static final File SMELLYFOLDER = new File("/u/");
    public static final String AUTOPATHKEY = "SmellyPath";
    public static final File DEFAULTPATH = new File("/lvuser/DefaultSmellyPath.json");

    public SmellyParser(DashboardWrapper dashboard)
    {
        this.dashboard = dashboard;
        objectMapper = new ObjectMapper();
        components = new ArrayList<>();
        FilenameFilter filter = (file, name) -> name.toLowerCase().endsWith(".json");
        SmartDashboard.putNumber(AUTOPATHKEY, 0.0);

        files = SMELLYFOLDER.listFiles(filter);
        if(files == null)
        {
            files = new File[1];
            files[0] = DEFAULTPATH;
        }

        for(int i = 0; i < files.length; i++)
        {
            SmartDashboard.putString("For SmellyPath " + files[i].toString().split(".json")[0] + " use " + i, "");
        }
    }

    public void constructPath()
    {
        components.clear();
        try
        {
            file = new File("/u/" + files[dashboard.getNumber(AUTOPATHKEY, 0.0).intValue()] + ".json");

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
            //todo log this
        }
    }

    public Path getPath()
    {
        return path;
    }
}
