package frc.team1983.commands.utilities;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.Robot;
import frc.team1983.commands.CommandBase;
import frc.team1983.services.PidValuesWatcher;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/*
A command that uses smartdashboard to allow for realtime pid tuning
with settings that persist through code restarts

First it iterates through all given pids to put the values it gets from the watchers getPidValues
to smartdashboard. Then it constantly loops through all given pids to check if the smartdashboard has changed them,
and if so updates the file.
*/
public class PidTuner extends CommandBase
{
    private ArrayList<String> pids;
    private ArrayList<double[]> pidValues;
    private String[] pidf;
    private double[] newPidValues;
    private boolean newData;
    private PidValuesWatcher watcher;

    private PrintWriter outputStream;

    public PidTuner(PidValuesWatcher watcher)
    {
        this.watcher = watcher;
        pids = watcher.getPids();

        newPidValues = new double[4];
        //array of pidf values
        pidf = new String[4];
        pidf[0] = "P";
        pidf[1] = "I";
        pidf[2] = "D";
        pidf[3] = "F";

        //go through all pids and create pidValue arrays for all pids
        //from watcher.getPidValues, then put them on smartdashboard
        for(int i = 0; i < pids.size(); i++)
        {
            for(int a = 0; a < 4; a++)
            {
                SmartDashboard.putNumber(pids.get(i) + pidf[a], pidValues.get(i)[a]);
            }
            pidValues.add(watcher.getPidValues(pids.get(i)));
        }
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        //go through all pids and get the smartdashboard numbers, check to see if different
        newData = false;
        for(int i = 0; i < pids.size(); i++)
        {
            for(int a = 0; a < 4; a++)
            {
                newPidValues[a] = SmartDashboard.getNumber(pids.get(i) + pidf[a], 0.0);
            }
            if(!Arrays.equals(newPidValues, pidValues.get(i)))
            {
                pidValues.set(i, newPidValues);
                newData = true;
            }
        }
        //if different, rewrite the PidValues file with the new values
        if(newData)
        {
            try
            {
                outputStream = new PrintWriter(new FileWriter("/home/lvuser/PidValues.txt"));

                System.out.println("Writing to PidValues");
                for(int i = 0; i < pids.size(); i++)
                {
                    for(int a = 0; a < 4; a++)
                    {
                        outputStream.println(pidValues.get(i)[a]);
                    }
                    outputStream.println(pids.get(i));
                }
            }
            catch(IOException e)
            {
                //TODO log this
            }

            outputStream.close();
        }
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end()
    {
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
