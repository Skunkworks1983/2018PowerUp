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
}
