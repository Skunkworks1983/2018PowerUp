package frc.team1983;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Climber;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import org.apache.logging.log4j.core.Logger;

public class Robot extends IterativeRobot
{
    private static Logger robotLogger;

    private OI oi;

    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private Climber climber;

    private DashboardWrapper dashboardWrapper;
    private StatefulDashboard dashboard;
    private AutoManager autoManager;

    private static Robot instance;

    public Robot()
    {
        instance = this;
    }

    @Override
    public void robotInit()
    {
        robotLogger = LoggerFactory.createNewLogger(Robot.class);
        dashboardWrapper = new DashboardWrapper();
        dashboard = new StatefulDashboard(dashboardWrapper, Constants.DashboardConstants.FILE);
        autoManager = new AutoManager(dashboardWrapper);

        oi = new OI();

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();
        climber = new Climber();

        oi.initializeBindings(this);
    }

    @Override
    public void robotPeriodic()
    {

    }

    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();

        dashboard.populate();
        dashboard.removeAll();

        drivebase.stopProfiles();
        elevator.stopProfile();

        drivebase.setBrakeMode(true);

        autoManager.resetGameData();
    }

    @Override
    public void disabledPeriodic()
    {

    }

    @Override
    public void autonomousInit()
    {
        Scheduler.getInstance().removeAll();

        //Scheduler.getInstance().add(new DriveArc(drivebase, -6, -90, 2));
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();

        //autoManager.execute();
    }

    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();

        drivebase.setBrakeMode(false);

        Scheduler.getInstance().add(new RunTankDrive(drivebase, oi));
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();

    }

    @Override
    public void testInit()
    {
        Scheduler.getInstance().removeAll();
    }

    @Override
    public void testPeriodic()
    {

    }

    public Drivebase getDrivebase()
    {
        return drivebase;
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

    public Climber getClimber() {
        return climber;
    }

    public StatefulDashboard getStatefulDashboard()
    {
        return dashboard;
    }

    public DashboardWrapper getDashboardWrapper()
    {
        return dashboardWrapper;
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
