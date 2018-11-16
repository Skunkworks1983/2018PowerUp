package frc.team1983.services;

public class AutoManager
{
    private static AutoManager instance;

    private AutoManager()
    {
        instance = this;
    }

    public synchronized static AutoManager getInstance()
    {
        if(instance == null)
            new AutoManager();
        return instance;
    }
}
