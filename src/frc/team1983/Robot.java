package frc.team1983;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.collector.CollectorRotate;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.SimpleTurnAngle;
import frc.team1983.commands.drivebase.TankDrive;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.OI;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class Robot extends IterativeRobot
{
    private static Logger robotLogger;
    private OI oi;
    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private Ramps ramps;
    private StatefulDashboard dashboard;
    private Subsystem subsystem;
    //private EncoderTurnAnglePidInput pidSource;
    private GyroPidInput pidSource;

    private static Robot instance;

    private RunOneMotor runOneMotor;

    @Override
    public void robotInit()
    {
        robotLogger = LoggerFactory.createNewLogger(Robot.class);
        dashboard = new StatefulDashboard(new DashboardWrapper(), Constants.DashboardConstants.FILE);
        dashboard.populate();

        oi = new OI(DriverStation.getInstance());

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();
        ramps = new Ramps();
        //pidSource = new EncoderTurnAnglePidInput(drivebase);
        pidSource = new GyroPidInput(drivebase.getGyro());

        oi.initializeBindings(this);
        robotLogger.info("robotInit");
    }

    @Override
    public void robotPeriodic()
    {

    }

    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();

        dashboard.store();

        GameDataPoller.resetGameData();
    }

    @Override
    public void disabledPeriodic()
    {
    }

    @Override
    public void autonomousInit()
    {
        robotLogger.info("AutoInit");
        Scheduler.getInstance().removeAll();

        drivebase.getGyro().initGyro();

        drivebase.setBrakeMode(true);
        //Scheduler.getInstance().add(new DriveStraight(dashboard, 5, drivebase, .6, 1000));
        Scheduler.getInstance().add(new SimpleTurnAngle(dashboard, 90, drivebase));

        //testing autos
        //Scheduler.getInstance().add(new PlaceCubeInExchangeZone(dashboard, drivebase));
        //Scheduler.getInstance().add(new PlaceCubeInScale(dashboard));
        //Scheduler.getInstance().add(new PlaceCubeInSwitch(dashboard));

    }

    @Override
    public void autonomousPeriodic()
    {
        GameDataPoller.pollGameData();

        Scheduler.getInstance().run();
        //robotLogger.info("yaw {}\t roll{}\t pitch{}", drivebase.getGyro().getYaw(), drivebase.getGyro().getRoll(), drivebase.getGyro().getPitch());

    }

    @Override
    public void teleopInit()
    {
        if(runOneMotor != null)
        {
            runOneMotor.end();
        }
        Scheduler.getInstance().removeAll();
        Scheduler.getInstance().add(new TankDrive(drivebase, oi));

        drivebase.setBrakeMode(false);
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();

        robotLogger.info(oi.getAxis(Constants.OIMap.Joystick.MANUAL, 1));
        elevator.set(ControlMode.PercentOutput, oi.getAxis(Constants.OIMap.Joystick.MANUAL, 1)/2);
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
            robotLogger.info("Initialized motor " + i);
        }

        runOneMotor.initialize(motors, motorUp, motorDown, manualSpeed);

    }

    @Override
    public void testPeriodic()
    {
        runOneMotor.execute();
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
