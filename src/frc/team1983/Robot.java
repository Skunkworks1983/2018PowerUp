package frc.team1983;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.services.OI;
import frc.team1983.services.LoggerFactory;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.utility.control.Motor;
import frc.team1983.utility.sensors.PSoC;
import frc.team1983.utility.sensors.Pigeon;
import org.apache.logging.log4j.core.Logger;

public class Robot extends IterativeRobot
{
    private static Logger robotLogger;

    private OI oi;

    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;

    private PSoC psoc;
    private Pigeon pigeon;

    private static Robot instance;

    private Robot()
    {

    }

    @Override
    public void robotInit()
    {
        robotLogger = LoggerFactory.createNewLogger(Robot.class);

        oi = new OI();

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();

        psoc = new PSoC();
        PSoC.initSPISensor(PSoC.SensorDaq);

        pigeon = new Pigeon(Motor.getByID(Constants.MotorMap.Drivebase.LEFT_3));

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

        drivebase.setBrakeMode(true);
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

        drivebase.setBrakeMode(false);
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
            instance = new Robot();

        return instance;
    }
}
