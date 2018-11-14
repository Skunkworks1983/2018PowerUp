package frc.team1983;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.sensors.PSoC;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.utility.control.ClosedLoopGains;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class Robot extends IterativeRobot
{
    private static Logger robotLogger;

    private OI oi;
    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private DashboardWrapper dashboardWrapper;
    private StatefulDashboard dashboard;
    private AutoManager autoManager;

    private RunOneMotor runOneMotor;

    private PSoC psoc;

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
        dashboard.populate();

        oi = new OI();

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();

        psoc = new PSoC();
        PSoC.initSPISensor(PSoC.SensorDaq);

        oi.initializeBindings(this);

        autoManager = new AutoManager(dashboardWrapper);
        autoManager.generatePaths();
    }

    @Override
    public void robotPeriodic()
    {

    }

    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();

        drivebase.stopProfiles();
        elevator.stopProfile();

        dashboard.store();

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

        drivebase.setBrakeMode(true);
        drivebase.getGyro().reset();
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
        autoManager.execute();
    }


    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();

        Scheduler.getInstance().add(new RunTankDrive(drivebase, oi));
        drivebase.setBrakeMode(false);
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();

        byte[] in = new byte[8];
        byte[] out = new byte[8];
        psoc.getSensorValue(out, in);
    }

    @Override
    public void testInit()
    {
        Scheduler.getInstance().removeAll();

        ArrayList<Motor> motors;
        motors = new ArrayList<>();

        DigitalInput motorUp;
        DigitalInput motorDown;
        AnalogInput manualSpeed;

        motorUp = new DigitalInput(5);
        motorDown = new DigitalInput(4);
        manualSpeed = new AnalogInput(2);

        if(runOneMotor == null)
        {
            runOneMotor = new RunOneMotor();
        }

        for(int i = 0; i < 16; i++)
        {
            motors.add(new Motor(i, false));
            motors.get(i).setNeutralMode(NeutralMode.Coast);
            //robotLogger.info("Initialized motor " + i);
        }

        runOneMotor.initialize(motors, motorUp, motorDown, manualSpeed);
    }

    @Override
    public void testPeriodic()
    {
        Scheduler.getInstance().run();
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

    public StatefulDashboard getStatefulDashboard()
    {
        return dashboard;
    }

    public DashboardWrapper getDashboardWrapper()
    {
        return dashboardWrapper;
    }

    public AutoManager getAutoManager()
    {
        return autoManager;
    }

    //Synchronized so that we hopefully don't end up with more than one instance
    public synchronized static Robot getInstance()
    {
        if(instance == null)
            instance = new Robot();

        return instance;
    }

    public ClosedLoopGains getLeftGains()
    {
        return Constants.PidConstants.Drivebase.Left.MAIN;
    }

    public ClosedLoopGains getRightGains()
    {
        return Constants.PidConstants.Drivebase.Left.MAIN;
    }

    public ClosedLoopGains getStraightGains()
    {
        return Constants.PidConstants.Drivebase.AUX_STRAIGHT;
    }

    public ClosedLoopGains getTurnGains()
    {
        return Constants.PidConstants.Drivebase.AUX_TURN;
    }
}
