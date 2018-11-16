package frc.team1983;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.services.OI;
import frc.team1983.services.StateEstimator;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.utility.control.Motor;
import frc.team1983.utility.sensors.PSoC;
import frc.team1983.utility.sensors.Pigeon;

public class Robot extends IterativeRobot
{
    private OI oi;

    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;

    private PSoC psoc;
    private Pigeon pigeon;

    private StateEstimator estimator;

    private static Robot instance;

    public Robot()
    {
        instance = this;
    }

    @Override
    public void robotInit()
    {
        oi = new OI();

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();

        psoc = new PSoC();
        pigeon = new Pigeon(Motor.getByID(Constants.MotorMap.Drivebase.LEFT_3));

        estimator = new StateEstimator();

        PSoC.initSPISensor(PSoC.SensorDaq);
        oi.initializeBindings();
    }

    @Override
    public void robotPeriodic()
    {

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

        pigeon.reset();
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
        Scheduler.getInstance().add(new RunTankDrive());
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

    public Collector getCollector()
    {
        return collector;
    }

    public OI getOI()
    {
        return oi;
    }

    public synchronized static Robot getInstance()
    {
        if(instance == null)
            new Robot();
        return instance;
    }
}
