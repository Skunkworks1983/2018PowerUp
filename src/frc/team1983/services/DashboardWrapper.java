package frc.team1983.services;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardWrapper
{
    public DashboardWrapper()
    {
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

    public void delete(String key)
    {
        SmartDashboard.delete(key);
    }
}
