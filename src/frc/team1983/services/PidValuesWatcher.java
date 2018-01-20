package frc.team1983.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;

public class PidValuesWatcher
{
    private Path dir;
    private WatchService watcher;
    private WatchKey key;
    private double[] defaultValues;

    public PidValuesWatcher()
    {
        dir = FileSystems.getDefault().getPath("/home/lvuser", "PidValues.txt");
        defaultValues = new double[4];
        for(int i = 0; i <= 3; i++)
        {
            defaultValues[i] = 0.0;
        }

        try
        {
            watcher = FileSystems.getDefault().newWatchService();
            key = dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
        }
        catch(IOException x)
        {
            //TODO log this
        }
    }

    public boolean isFileModified()
    {
        WatchKey key;

        try
        {
            // get all events
            key = watcher.poll();
            // Normally at this point one would iterate through all the events and check for overflows or the event type
            // was, but we only care if anything at all changed, so we can just return whether or not anything happened.
            return key != null;
        }
        catch(NullPointerException x)
        {
            // if there is an error, lets just not touch the pidvalues file
            return false;
            //TODO log this
        }
    }

    public double[] getMyPidValues(String subsystem)
    {
        BufferedReader inputStream = null;
        int i = 0;
        double[] pidValues = new double[4];

        try
        {
            inputStream = new BufferedReader(new FileReader("/home/lvuser/PidValues.txt"));

            String l;
            while(true)
            {
                if(!(l = inputStream.readLine()).equals(subsystem))
                {
                    try
                    {
                        pidValues[i] = Double.parseDouble(l);
                    }
                    catch(NumberFormatException e)
                    {
                        pidValues[i] = 0.0;
                    }
                    i++;
                    if(i > 3)
                    {
                        i = 0;
                    }
                }
                else
                {
                    return pidValues;
                }
            }
        }
        catch(IOException e)
        {
            //TODO log this
            return defaultValues;
        }
    }
}
