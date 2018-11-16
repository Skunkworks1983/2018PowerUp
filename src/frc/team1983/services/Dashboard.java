package frc.team1983.services;

public class Dashboard
{
    private static Dashboard instance;

    private Dashboard()
    {
        instance = this;
    }

    public synchronized static Dashboard getInstance()
    {
        if(instance == null)
            new Dashboard();
        return instance;
    }
}
