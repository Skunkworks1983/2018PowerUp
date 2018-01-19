package frc.team1983;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public class Robot extends IterativeRobot
{

    private OI oi;
    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private Ramps ramps;
    private static Robot instance;

    Command autonomousCommand;

    @Override
    public void robotInit()
    {
        oi = new OI(Constants.OIMap.Mode.DOUBLE_JOY, DriverStation.getInstance(), elevator);
        oi.initialize();
        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator(oi);
        ramps = new Ramps();
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
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();
    }

    @Override
    public void teleopPeriodic()
    {
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

    public Ramps getRamps()
    {
        return ramps;
    }


    public Elevator getElevator()
    {
        return elevator;
    }

    public OI getOI()
    {
        return oi;
    }

    public Collector getCollector()
    {
        return collector;
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
