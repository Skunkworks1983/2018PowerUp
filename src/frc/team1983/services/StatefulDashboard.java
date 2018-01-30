package frc.team1983.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_BOOLEAN;
import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_STRING;
import static frc.team1983.settings.Constants.DashboardConstants.DEFAULT_DOUBLE;
import static frc.team1983.settings.Constants.DashboardConstants.KEY_SEPARATOR;
import static frc.team1983.settings.Constants.DashboardConstants.VALUE_SEPARATOR;


//A wrapper for the smartdashboard that persists values through robot restarts
public class StatefulDashboard
{
    private BufferedReader inputStream;
    private String line, str, valueType, value;
    private File dir;
    private Double doub;
    private Boolean bool;
    private String[] splitLine;
    private Set<String> keySet = new HashSet<>();
    private DashboardWrapper dashboardWrapper;

    private PrintWriter outputStream;

    public StatefulDashboard(DashboardWrapper dashboardWrapper, File dir)
    {
        this.dir = dir;
        this.dashboardWrapper = dashboardWrapper;
    }

    //For unit testing
    public Set<String> getKeySet()
    {
        return keySet;
    }

    private String constructKey(String command, String key)
    {
        return command + KEY_SEPARATOR + key;
    }
    
    //The add functions are for adding a variable to the dashboard, the value given is only a default, and will
    //not be used if the variable already exists (like if the variable is already in the file)
    public void add(String command, String key, Double defaultValue)
    {
        key = constructKey(command, key);
        if(keySet.add(key))
        {
            dashboardWrapper.putNumber(key, defaultValue);
        }
    }

    public void add(String command, String key, Boolean defaultValue)
    {
        key = constructKey(command, key);
        if(keySet.add(key))
        {
            dashboardWrapper.putBoolean(key, defaultValue);
        }
    }

    public void add(String command, String key, String defaultValue)
    {
        key = constructKey(command, key);
        if(keySet.add(key))
        {
            dashboardWrapper.putString(key, defaultValue);
        }
    }

    //The set functions will replace the value at the given key with a new provided value
    public void set(String command, String key, Double value)
    {
        key = constructKey(command, key);
        keySet.add(key);
        dashboardWrapper.putNumber(key, value);
    }

    public void set(String command, String key, Boolean value)
    {
        key = constructKey(command, key);
        keySet.add(key);
        dashboardWrapper.putBoolean(key, value);
    }

    public void set(String command, String key, String value)
    {
        key = constructKey(command, key);
        keySet.add(key);
        dashboardWrapper.putString(key, value);
    }

    public double getDouble(String command, String key)
    {
        key = constructKey(command, key);
        if(keySet.contains(key))
        {
            return dashboardWrapper.getNumber(key, DEFAULT_DOUBLE);
        }
        else
        {
            System.out.println("Something requested a double at " + key
                                       + "but it has not been added or set yet, so a default value was returned");
            return DEFAULT_DOUBLE;
        }
    }

    public boolean getBoolean(String command, String key)
    {
        key = constructKey(command, key);
        if(keySet.contains(key))
        {
            bool = dashboardWrapper.getBoolean(key, DEFAULT_BOOLEAN);
        }
        else
        {
            System.out.println("Something requested a boolean at " + key
                                       + "but it has not been added or set yet, so a default value was returned");
            return DEFAULT_BOOLEAN;
        }
        return bool;
    }

    public String getString(String command, String key)
    {
        key = constructKey(command, key);
        if(keySet.contains(key))
        {
            return dashboardWrapper.getString(key, DEFAULT_STRING);
        }
        else
        {
            System.out.println("Something requested a double at " + key
                                       + "but it has not been added or set yet, so a default value was returned");
            return DEFAULT_STRING;
        }
    }

    public void populate()
    {
        try
        {
            inputStream = new BufferedReader(new FileReader(dir));
            System.out.println("Opened inputsteam");
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
                System.out.println("Populating with " + line);
                splitLine = line.split(VALUE_SEPARATOR); //Split into command/key and value

                valueType = getTypeFromString(splitLine[1]); //get the valueType


                if(valueType.equals("boolean"))
                {
                    bool = Boolean.valueOf(splitLine[1]); //set bool to parsed boolean
                    dashboardWrapper.putBoolean(splitLine[0], bool);
                    splitLine = splitLine[0].split(KEY_SEPARATOR); //Split command/key into command and key
                    add(splitLine[0], splitLine[1], bool);
                    continue;
                }
                if(valueType.equals("double"))
                {
                    doub = Double.parseDouble(splitLine[1]); //set doub to parsed double
                    dashboardWrapper.putNumber(splitLine[0], doub);
                    splitLine = splitLine[0].split(KEY_SEPARATOR); //Split command/key into command and key
                    add(splitLine[0], splitLine[1], doub);
                    continue;
                }
                if(valueType.equals("string"))
                {
                    str = splitLine[1]; //set str to string
                    dashboardWrapper.putString(splitLine[0], str);
                    splitLine = splitLine[0].split(KEY_SEPARATOR);
                    add(splitLine[0], splitLine[1], str);
                }
            }
            inputStream.close();
        }
        catch(IOException e)
        {
            //TODO log this
            System.out.println("IOException when reading from file in StatefulDashboard");
        }
    }

    public void remove(String command, String key)
    {
        keySet.remove(command + KEY_SEPARATOR + key);
    }

    public void store()
    {
        try
        {
            outputStream = new PrintWriter(new FileWriter(dir));

            for(String key : keySet)
            {
                valueType = dashboardWrapper.getType(key);

                if(valueType == null)
                {
                    valueType = "unknown";
                    System.out.println("unknown data type");
                }
                if(valueType.equals("boolean"))
                {
                    value = dashboardWrapper.getBoolean(key, DEFAULT_BOOLEAN).toString();
                }
                if(valueType.equals("double"))
                {
                    value = dashboardWrapper.getNumber(key, DEFAULT_DOUBLE).toString();
                }
                if(valueType.equals("string"))
                {
                    value = dashboardWrapper.getString(key, DEFAULT_STRING);
                }
                System.out.println("Stored " + key + VALUE_SEPARATOR + value);
                outputStream.println(key + VALUE_SEPARATOR + value);
            }

            outputStream.close();
            System.out.println("Closed outputstream");
        }
        catch(IOException e)
        {
            System.out.println("IOException when writing to file");
            //TODO log this
        }
    }

    //protected so unit test can access
    protected String getTypeFromString(String value)
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
            catch(NumberFormatException e)
            {
                return "string";
            }
        }

    }
}