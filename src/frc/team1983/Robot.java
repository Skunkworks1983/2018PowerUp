package frc.team1983;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.utilities.PidTuner;
import frc.team1983.services.OI;
import frc.team1983.services.PidValuesWatcher;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.Drivebase;

import java.util.ArrayList;

public class Robot extends IterativeRobot
{
    private static Robot instance;
    private OI oi;
    private Collector collector;
    private Drivebase drivebase;
    private Ramps ramps;
    private PidValuesWatcher watcher;

    @Override
    public void robotInit()
    {
        drivebase = new Drivebase();
        oi = new OI(Constants.OIMap.Mode.DOUBLE_JOY, DriverStation.getInstance());
        ramps = new Ramps();
        collector = new Collector();
        watcher = new PidValuesWatcher();

        oi.initialize();
    }

    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();
    }

    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {
        Scheduler.getInstance().removeAll();
        watcher.resetPids();

        //PidControllers need to be added before pidtuner, or default values will be used
        Scheduler.getInstance().add(new PidTuner(watcher));
    }

    @Override
    public void autonomousPeriodic()
    {
        watcher.checkFile();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();
        watcher.resetPids();

        //PidControllerWrappers need to be added before pidtuner, or default values will be used
        Scheduler.getInstance().add(new PidTuner(watcher));
    }

    @Override
    public void teleopPeriodic()
    {
        watcher.checkFile();
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic()
    {

    }

    public Drivebase getDrivebase()
    {
        return drivebase;
    }

    public OI getOI()
    {
        return oi;
    }

    public Ramps getRamps()
    {
        return ramps;
    }

    public Collector getCollector()
    {
        return collector;
    }

    public PidValuesWatcher getWatcher()
    {
        return watcher;
    }

    public static Robot getInstance()
    {
        if(instance == null)
        {
            instance = new Robot();
        }

        return instance;
    }
}
