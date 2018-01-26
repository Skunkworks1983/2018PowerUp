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
import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_STRING;
import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_VALUE;
import static frc.team1983.settings.Constants.DashboardConstants.KEY_SEPARATOR;
import static frc.team1983.settings.Constants.DashboardConstants.VALUE_SEPARATOR;


//A wrapper for the smartdashboard that persists values through robot restarts
public class DashboardWrapper
{
    private BufferedReader inputStream;
    private String line, key, str, valueType, value;
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

    public void put(String command, String key, String value)
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

    public String getString(String command, String key)
    {
        str = SmartDashboard.getString(command + KEY_SEPARATOR + key, DEFAULT_STRING);
        map.put(command + KEY_SEPARATOR + key, new DashType(str));
        return str;
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

        try
        {
            while((line = inputStream.readLine()) != null)
            {
                splitLine = line.split(VALUE_SEPARATOR); //Split into command/key and doub

                valueType = getTypeFromString(splitLine[1]); //get the valueType


                if(str.equals("boolean"))
                {
                    bool = Boolean.valueOf(splitLine[1]); //set bool to parsed boolean
                    SmartDashboard.putBoolean(splitLine[0], bool);
                    splitLine = splitLine[0].split(KEY_SEPARATOR); //Split command/key into command and key
                    put(splitLine[0], splitLine[1], bool);
                    continue;
                }
                if(str.equals("double"))
                {
                    doub = Double.parseDouble(splitLine[1]); //set doub to parsed double
                    SmartDashboard.putNumber(splitLine[0], doub);
                    splitLine = splitLine[0].split(KEY_SEPARATOR); //Split command/key into command and key
                    put(splitLine[0], splitLine[1], doub);
                    continue;
                }
                if(str.equals("string"))
                {
                    str = splitLine[1]; //set str to string
                    SmartDashboard.putString(splitLine[0], str);
                    splitLine = splitLine[0].split(KEY_SEPARATOR);
                    put(splitLine[0], splitLine[1], str);
                }
            }
        }
        catch(IOException e)
        {
            //TODO log this
            System.out.println("IOException when reading from file in DashboardWrapper");
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
                outputStream.println(pair.getKey() + VALUE_SEPARATOR + pair.getValue().toString());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        catch(IOException e)
        {
            //TODO log this
            System.out.println("IOException when writing to file in DashboardWrapper");
        }
    }

    private String getTypeFromString(String value)
    {
        if(value.equals("true") || value.equals("false"))
        {
            return "boolean";
        }
        else
        {
            try
            {
                Double.parseDouble(value);
                return "double";
            }
            //If the value is not true, false, or parsable by Double.parseDouble(), then it must be a string
            catch (NumberFormatException e)
            {
                return "string";
            }
        }

    }
}

class DashType
{
    private Double doub;
    private Boolean bool;
    private String str;

    DashType(Boolean bool)
    {
        this.bool = bool;
    }

    DashType(Double doub)
    {
        this.doub = doub;
    }

    DashType(String str)
    {
        this.str = str;
    }

    String getString()
    {
        return str;
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
