package frc.team1983.services;

public class DashType
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

    String getAsString()
    {
        if(doub != null)
        {
            return doub.toString();
        }
        if(bool != null)
        {
            return bool.toString();
        }
        else
        {
            return str;
        }
    }
}
