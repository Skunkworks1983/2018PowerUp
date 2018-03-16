package frc.team1983.services;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.services.automanager.AutoSelection;

import java.util.HashMap;

//A bare bones wrapper for smartdashboard to facilitate unit testing
public class DashboardWrapper
{
    private SendableChooser<AutoSelection> autoChooser;
    private String autoChooserName;
    private HashMap<String, SendableChooser> chooserMap;

    public DashboardWrapper()
    {
        chooserMap = new HashMap<>();
        autoChooser = new SendableChooser<>();
        autoChooserName = "Auto Selector";
    }

    public void putString(String key, String value)
    {
        SmartDashboard.putString(key, value);
    }

    public void putNumber(String key, Double value)
    {
        SmartDashboard.putNumber(key, value);
    }

    public void putBoolean(String key, Boolean value)
    {
        SmartDashboard.putBoolean(key, value);
    }

    public void addAutoChooserAutoChoice(AutoSelection autoSelection)
    {
        autoChooser.addObject(autoSelection.getReadableName(), autoSelection);
        SmartDashboard.putData(autoChooserName, autoChooser);
    }

    public void addAutoChooserAutoDefault(AutoSelection autoSelection)
    {
        autoChooser.addDefault(autoSelection.getReadableName(), autoSelection);
        SmartDashboard.putData(autoChooserName, autoChooser);
    }

    public AutoSelection getSelectedAutoChoice()
    {
        return autoChooser.getSelected();
    }

    public String getString(String key, String defaultValue)
    {
        return SmartDashboard.getString(key, defaultValue);
    }

    public Boolean getBoolean(String key, Boolean defaultValue)
    {
        return SmartDashboard.getBoolean(key, defaultValue);
    }

    public Double getNumber(String key, Double defaultValue)
    {
        return SmartDashboard.getNumber(key, defaultValue);
    }
    
    public void delete(String key)
    {
        SmartDashboard.delete(key);
    }

    public String getType(String key)
    {
        try
        {
            SmartDashboard.getNumber(key, 0.0);
            return "double";
        }
        catch(Exception e)
        {
        }
        try
        {
            SmartDashboard.getString(key, "");
            return "string";
        }
        catch(Exception e)
        {
            return "boolean";
        }
    }
}
