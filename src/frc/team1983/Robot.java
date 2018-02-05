package frc.team1983;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.elevator.ElevatorControl;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.Ramps;
import org.apache.log4j.Logger;

public class Robot extends IterativeRobot
{
    private static Logger logger ;
    //Properties loggerProperties;
    private OI oi;
    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private Ramps ramps;
    private StatefulDashboard dashboard;
    private static Robot instance;

    @Override
    public void robotInit()
    {
        System.setProperty("log4j.configurationFile", "2018PowerUp/log4j.xml");
        logger = Logger.getLogger(Robot.class);
        dashboard = new StatefulDashboard(new DashboardWrapper(), Constants.DashboardConstants.FILE);
        dashboard.populate();
        oi = new OI(DriverStation.getInstance());
        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();
        ramps = new Ramps();

        oi.initialize(this);
        logger.fatal("robotInit");
    }


    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();
        dashboard.store();
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
        dashboard.populate();
        Scheduler.getInstance().add(new ElevatorControl(elevator, dashboard));
        logger.debug("AutoInit");
        getRamps().drop();
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
        logger.debug("AutoPeriodic");
    }

    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();
        dashboard.populate();
        Scheduler.getInstance().add(new ElevatorControl(elevator, dashboard));
        logger.info("You know I had to do it to em");
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
        logger.info("Second test");
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

    public Logger getLogger() { return logger; }

    public static Robot getInstance()
    {
        if(instance == null)
        {
            instance = new Robot();
        }
        return instance;
    }
}
