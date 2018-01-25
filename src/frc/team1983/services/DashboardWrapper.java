package frc.team1983.services;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.settings.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_BOOLEAN;
import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_VALUE;
import static frc.team1983.settings.Constants.DashboardConstants.KEY_SEPARATOR;
import static frc.team1983.settings.Constants.DashboardConstants.VALUE_SEPARATOR;


//A wrapper for the smartdashboard that
public class DashboardWrapper
{
    private BufferedReader inputStream;
    private String line;
    private Double doub;
    private Boolean bool;
    private String[] splitLine;
    private Map<String, DashType> map = new HashMap<>();

    private PrintWriter outputStream;

    public DashboardWrapper()
    {
        populate();
    }

    //The two puts are for double and boolean, but are otherwise the same.
    //They will add key doub pairs to the
    public void put(String command, String key, Double value)
    {
        map.putIfAbsent(command + KEY_SEPARATOR + key, new DashType(value));
    }

    public void put(String command, String key, Boolean value)
    {
        //If doub is not already gotten from populate, put
        map.putIfAbsent(command + KEY_SEPARATOR + key, new DashType(value));
    }

    public double getDouble(String command, String key)
    {
        doub = SmartDashboard.getNumber(command + KEY_SEPARATOR + key, DEFAULT_VALUE);
        map.put(command + KEY_SEPARATOR + key, new DashType(doub));
        return doub;
    }

    public boolean getBoolean(String command, String key)
    {
        bool = SmartDashboard.getBoolean(command + KEY_SEPARATOR + key, DEFAULT_BOOLEAN);
        map.put(command + KEY_SEPARATOR + key, new DashType(bool));
        return bool;
    }

    private void populate()
    {
        try
        {
            inputStream = new BufferedReader(new FileReader(Constants.DashboardConstants.dir));
        }
        catch(FileNotFoundException e)
        {
            //TODO log this
            System.out.println("File has not been created");
            return;
        }

        while(true)
        {
            try
            {
                line = inputStream.readLine();
                if(line != null)
                {
                    splitLine = line.split(VALUE_SEPARATOR); //Split into command/key and doub

                    //If the value string has an e, then it is either fals'e' or tru'e', but not a double
                    if(splitLine[1].contains("e"))
                    {
                        bool = Boolean.getBoolean(splitLine[1]); //set bool to bool
                        SmartDashboard.putBoolean(splitLine[0], bool);
                        splitLine = splitLine[0].split(KEY_SEPARATOR); //Split command/key into command and key
                        put(splitLine[0], splitLine[1], bool);
                    }
                    else
                    {
                        doub = Double.parseDouble(splitLine[1]); //set doub to doub
                        SmartDashboard.putNumber(splitLine[0], doub);
                        splitLine = splitLine[0].split(KEY_SEPARATOR); //Split command/key into command and key
                        put(splitLine[0], splitLine[1], doub);
                    }

                }
                else
                {
                    return;
                }
            }
            catch(IOException e)
            {
                //TODO log this
                System.out.println("IOException when reading from dashboard file");
                return;
            }
        }
    }

    public void remove(String command, String key)
    {
        map.remove(command + KEY_SEPARATOR + key);
    }

    public void store()
    {
        try
        {
            outputStream = new PrintWriter(new FileWriter(Constants.DashboardConstants.dir));

            Iterator it = map.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry pair = (Map.Entry) it.next();
                outputStream.println(pair.getKey() + VALUE_SEPARATOR + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        catch(IOException e)
        {
            //TODO log this
            System.out.println("IOException when writing to file in DashboardWrapper");
        }
    }
}

class DashType
{
    private Double doub;
    private Boolean bool;

    DashType(Boolean bool)
    {
        this.bool = bool;
    }

    DashType(Double doub)
    {
        this.doub = doub;
    }

    String getString()
    {
        if(bool != null)
        {
            return bool.toString();
        }
        else
        {
            return doub.toString();
        }
    }

    Double getDouble()
    {
        return doub;
    }

    Boolean getBoolean()
    {
        return bool;
    }
}
