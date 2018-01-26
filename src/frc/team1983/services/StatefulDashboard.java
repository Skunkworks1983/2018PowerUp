package frc.team1983.services;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.settings.Constants;

import java.io.BufferedReader;
import java.io.File;
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
public class StatefulDashboard
{
    private BufferedReader inputStream;
    private String line, key, str, valueType, value;
    private File dir;
    private Double doub;
    private Boolean bool;
    private String[] splitLine;
    private HashMap<String, DashType> map = new HashMap<>();
    private DashboardWrapper dashboardWrapper;

    private PrintWriter outputStream;

    public StatefulDashboard(DashboardWrapper dashboardWrapper, File dir)
    {
        this.dir = dir;
        this.dashboardWrapper = dashboardWrapper;
        populate();
    }

    //for unit testing
    private HashMap getMap()
    {
        return map;
    }

    //The add functions are for adding a variable to the dashboard, the value given is only a default, and will
    //not be used if the variable already exists (like if the variable is already in the file)
    public void add(String command, String key, Double value)
    {
        key = command + KEY_SEPARATOR + key;
        if(map.putIfAbsent(key, new DashType(value)) == null)
        {
            dashboardWrapper.putNumber(key, value);
        }
    }

    public void add(String command, String key, Boolean value)
    {
        key = command + KEY_SEPARATOR + key;
        if(map.putIfAbsent(key, new DashType(value)) == null)
        {
            dashboardWrapper.putBoolean(key, value);
        }
    }

    public void add(String command, String key, String value)
    {
        key = command + KEY_SEPARATOR + key;
        if(map.putIfAbsent(key, new DashType(value)) == null)
        {
            dashboardWrapper.putString(key, value);
        }
    }

    //The set functions will
    public void set(String command, String key, Double value)
    {
        key = command + KEY_SEPARATOR + key;
        map.put(key, new DashType(value));
        dashboardWrapper.putNumber(key, value);
    }

    public void set(String command, String key, Boolean value)
    {
        key = command + KEY_SEPARATOR + key;
        map.put(key, new DashType(value));
        dashboardWrapper.putBoolean(key, value);
    }

    public void set(String command, String key, String value)
    {
        key = command + KEY_SEPARATOR + key;
        map.put(key, new DashType(value));
        dashboardWrapper.putString(key, value);
    }

    public double getDouble(String command, String key)
    {
        key = command + KEY_SEPARATOR + key;
        doub = SmartDashboard.getNumber(command + KEY_SEPARATOR + key, DEFAULT_VALUE);
        map.put(key, new DashType(doub));
        return doub;
    }

    public boolean getBoolean(String command, String key)
    {
        key = command + KEY_SEPARATOR + key;
        bool = SmartDashboard.getBoolean(key, DEFAULT_BOOLEAN);
        map.put(key, new DashType(bool));
        return bool;
    }

    public String getString(String command, String key)
    {
        key = command + KEY_SEPARATOR + key;
        str = SmartDashboard.getString(key, DEFAULT_STRING);
        map.put(key, new DashType(str));
        return str;
    }

    private void populate()
    {
        try
        {
            inputStream = new BufferedReader(new FileReader(dir));
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
                    add(splitLine[0], splitLine[1], bool);
                    continue;
                }
                if(str.equals("double"))
                {
                    doub = Double.parseDouble(splitLine[1]); //set doub to parsed double
                    SmartDashboard.putNumber(splitLine[0], doub);
                    splitLine = splitLine[0].split(KEY_SEPARATOR); //Split command/key into command and key
                    add(splitLine[0], splitLine[1], doub);
                    continue;
                }
                if(str.equals("string"))
                {
                    str = splitLine[1]; //set str to string
                    SmartDashboard.putString(splitLine[0], str);
                    splitLine = splitLine[0].split(KEY_SEPARATOR);
                    add(splitLine[0], splitLine[1], str);
                }
            }
        }
        catch(IOException e)
        {
            //TODO log this
            System.out.println("IOException when reading from file in StatefulDashboard");
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
            outputStream = new PrintWriter(new FileWriter(dir));

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
            System.out.println("IOException when writing to file in StatefulDashboard");
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
