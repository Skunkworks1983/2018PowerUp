package frc.team1983.services;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import org.apache.logging.log4j.core.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;


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
    private Logger logger;
    private PrintWriter outputStream;

    public StatefulDashboard(DashboardWrapper dashboardWrapper, File dir)
    {
        this.dir = dir;
        this.dashboardWrapper = dashboardWrapper;

        logger = LoggerFactory.createNewLogger(this.getClass());
    }

    //For unit testing
    public Set<String> getKeySet()
    {
        return keySet;
    }
    
    //The add functions are for adding a variable to the dashboard, the value given is only a default, and will
    //not be used if the variable already exists (like if the variable is already in the file)
    public void add(Object object, String key, Double defaultValue)
    {
        key = constructKey(object, key);
        if(keySet.add(key))
        {
            dashboardWrapper.putNumber(key, defaultValue);
        }
    }

    public void add(Object object, String key, Boolean defaultValue)
    {
        key = constructKey(object, key);
        if(keySet.add(key))
        {
            dashboardWrapper.putBoolean(key, defaultValue);
        }
    }

    public void add(Object object, String key, String defaultValue)
    {
        key = constructKey(object, key);
        if(keySet.add(key))
        {
            dashboardWrapper.putString(key, defaultValue);
        }
    }

    //The set functions will replace the value at the given key with a new provided value
    public void set(Object object, String key, Double value)
    {
        key = constructKey(object, key);
        keySet.add(key);
        dashboardWrapper.putNumber(key, value);
    }

    public void set(Object object, String key, Boolean value)
    {
        key = constructKey(object, key);
        keySet.add(key);
        dashboardWrapper.putBoolean(key, value);
    }

    public void set(Object object, String key, String value)
    {
        key = constructKey(object, key);
        keySet.add(key);
        dashboardWrapper.putString(key, value);
    }

    public void removeAll()
    {
        for(String key : keySet)
        {
            remove(key);
        }
    }

    //The get functions get the value from Smartdashboard, and if it does not exist returns a default value
    public double getDouble(Object object, String key)
    {
        key = constructKey(object, key);
        if(keySet.contains(key))
        {
            return dashboardWrapper.getNumber(key, Constants.DashboardConstants.DEFAULT_DOUBLE);
        }
        else
        {
            System.out.println("Something requested a double at " + key
                                       + "but it has not been added or set yet, so a default value was returned");
            return Constants.DashboardConstants.DEFAULT_DOUBLE;
        }
    }

    public boolean getBoolean(Object object, String key)
    {
        key = constructKey(object, key);
        if(keySet.contains(key))
        {
            bool = dashboardWrapper.getBoolean(key, Constants.DashboardConstants.DEFAULT_BOOLEAN);
        }
        else
        {
            System.out.println("Something requested a boolean at " + key
                                       + "but it has not been added or set yet, so a default value was returned");
            return Constants.DashboardConstants.DEFAULT_BOOLEAN;
        }
        return bool;
    }

    public String getString(Object object, String key)
    {
        key = constructKey(object, key);
        if(keySet.contains(key))
        {
            return dashboardWrapper.getString(key, Constants.DashboardConstants.DEFAULT_STRING);
        }
        else
        {
            System.out.println("Something requested a string at " + key
                                       + "but it has not been added or set yet, so a default value was returned");
            return Constants.DashboardConstants.DEFAULT_STRING;
        }
    }

    //Populate gets all the values from the file and adds them to smartdashboard
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
                splitLine = line.split(Constants.DashboardConstants.VALUE_SEPARATOR); //Split into object/key and value

                valueType = getTypeFromString(splitLine[1]); //get the valueType


                if(valueType.equals("boolean"))
                {
                    bool = Boolean.valueOf(splitLine[1]); //set bool to parsed boolean
                    dashboardWrapper.putBoolean(splitLine[0], bool);
                    splitLine = splitLine[0].split(Constants.DashboardConstants.KEY_SEPARATOR); //Split object/key into object and key
                    add(splitLine[0], splitLine[1], bool);
                    continue;
                }
                if(valueType.equals("double"))
                {
                    doub = Double.parseDouble(splitLine[1]); //set doub to parsed double
                    dashboardWrapper.putNumber(splitLine[0], doub);
                    splitLine = splitLine[0].split(Constants.DashboardConstants.KEY_SEPARATOR); //Split object/key into object and key
                    add(splitLine[0], splitLine[1], doub);
                    continue;
                }
                if(valueType.equals("string"))
                {
                    str = splitLine[1]; //set str to string
                    dashboardWrapper.putString(splitLine[0], str);
                    splitLine = splitLine[0].split(Constants.DashboardConstants.KEY_SEPARATOR);
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

    //Removes a value from the keyset and smartdashboard
    public void remove(String key)
    {
        dashboardWrapper.delete(key);
        keySet.remove(key);
    }

    //Gets all values from smartdashboard and writes them to the file
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
                    logger.warn("unknown data type");
                }
                if(valueType.equals("boolean"))
                {
                    value = dashboardWrapper.getBoolean(key, Constants.DashboardConstants.DEFAULT_BOOLEAN).toString();
                }
                if(valueType.equals("double"))
                {
                    value = dashboardWrapper.getNumber(key, Constants.DashboardConstants.DEFAULT_DOUBLE).toString();
                }
                if(valueType.equals("string"))
                {
                    value = dashboardWrapper.getString(key, Constants.DashboardConstants.DEFAULT_STRING);
                }
                System.out.println("Stored " + key + Constants.DashboardConstants.VALUE_SEPARATOR + value);
                outputStream.println(key + Constants.DashboardConstants.VALUE_SEPARATOR + value);
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

    //Construct the key that smartdashboard will use
    private String constructKey(Object object, String key)
    {
        return object + Constants.DashboardConstants.KEY_SEPARATOR + key;
    }
}