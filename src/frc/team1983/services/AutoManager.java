package frc.team1983.services;

public class AutoManager
{
    private static AutoManager instance;

    private AutoManager()
    {

    }

    public synchronized static AutoManager getInstance()
    {
        if(instance == null)
            instance = new AutoManager();

        return instance;
    }
}
