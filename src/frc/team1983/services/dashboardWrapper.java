package frc.team1983.services;

import frc.team1983.settings.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class dashboardWrapper
{
    private Map<String, DashType> map = new HashMap<>();

    private final String keySeparator = "-";
    private final String valueSeparator = ":";

    public dashboardWrapper()
    {
        populate();
    }

    //The two puts are for double and boolean, but are otherwise the same.
    //They will add key value pairs to the
    public void put(String command, String key, Double value)
    {
        map.putIfAbsent(command + keySeparator + key, new DashType(value));
    }

    public void put(String command, String key, Boolean value)
    {
        //If value is not already gotten from populate, put
        map.putIfAbsent(command + keySeparator + key, new DashType(value));
    }

    public double get(String command, String key)
    {
        return map.get(command + keySeparator + key).getDouble();
    }

    public boolean getBoolean(String command, String key)
    {
        return map.get(command + keySeparator + key).getBoolean();
    }

    private void populate()
    {
        BufferedReader inputStream;
        String line;
        String command;
        String[] splitLine;
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
                    splitLine = line.split(keySeparator);
                    command = splitLine[0];
                    splitLine = splitLine[1].split(valueSeparator);
                    put(command, splitLine[0], Double.parseDouble(splitLine[1]));
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

    public void store()
    {
        
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
