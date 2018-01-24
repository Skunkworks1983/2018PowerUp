package frc.team1983.services;

import frc.team1983.settings.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;

/*
A service that will watch the PidValues.txt file to alert the robot if any changes occur, and can
also get pidf values for any registered pid. Pids are registered in the PidTuner command.
 */
public class PidValuesWatcher
{
    private Path dir;
    private WatchService watcher;
    private WatchKey key;
    private double[] defaultValues;
    private ArrayList<String> pids;
    private boolean fileModified;

    public PidValuesWatcher()
    {
        //Initialize this to something so the robot doesn't choke if a command tries to
        //isFileModified before the robot checkFiles
        fileModified = false;

        //Set default values, if the actual values have not been initialized
        dir = FileSystems.getDefault().getPath("/home/lvuser", "PidValues.txt");
        defaultValues = new double[4];
        for(int i = 0; i <= 3; i++)
        {
            defaultValues[i] = Constants.PidConstants.PLACEHOLDER_VALUE;
        }

        //Make the watcher and key
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

    public void checkFile()
    {
        try
        {
            // get all events
            key = watcher.poll();
            // Normally at this point one would iterate through all the events and check for overflows or the event type
            // was, but we only care if anything at all changed, so we can just return whether or not anything happened.
            fileModified = (key != null);
        }
        catch(NullPointerException x)
        {
            // if there is an error, lets just not touch the pidvalues file
            fileModified = false;
            //TODO log this
        }

    }

    public boolean isFileModified()
    {
        return fileModified;
    }

    public double[] getPidValues(String pidName)
    {
        BufferedReader inputStream;
        double[] pidValues;

        try
        {
            inputStream = new BufferedReader(new FileReader("/home/lvuser/PidValues.txt"));

            //set to default values in case values are not initialized
            pidValues = defaultValues;
            String l;

            int i = 0;
            while(true)
            {
                l = inputStream.readLine(); //Get the next line
                if(l != null && !(l).equals(pidName)) //If not the end of the file or the deliminator
                {
                    try
                    {
                        pidValues[i] = Double.parseDouble(l);
                        i++;
                    }
                    catch(NumberFormatException e)
                    {
                        //If it is the deliminator make it a placeholder and reset the counter
                        pidValues[i] = Constants.PidConstants.PLACEHOLDER_VALUE;
                        i = 0;
                    }
                }
                else
                {
                    inputStream.close();
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

    public ArrayList<String> getPids()
    {
        return pids;
    }

    public void addPid(String pid)
    {
        pids.add(pid);
    }

    public void resetPids()
    {
        pids.clear();
    }
}
