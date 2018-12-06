package frc.team1983.services;

import frc.team1983.autonomous.AutoRoutine;
import frc.team1983.autonomous.routines.TestRoutine;

public class AutoManager
{
    private static AutoManager instance;

    private AutoManager()
    {
        instance = this;
    }

    public AutoRoutine getRoutine()
    {
        return new TestRoutine();
    }

    public synchronized static AutoManager getInstance()
    {
        if(instance == null)
            new AutoManager();
        return instance;
    }
}
