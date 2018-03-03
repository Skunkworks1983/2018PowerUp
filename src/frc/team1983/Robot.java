package frc.team1983;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.collector.CollectorRotate;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.commands.debugging.DisplayButtonPresses;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.OI;
import frc.team1983.services.SmellyParser;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.control.ProfileController;
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
    private SmellyParser smellyParser;
    private DashboardWrapper dashboardWrapper;
    private Subsystem subsystem;
    private GyroPidInput pidSource;

    private ArrayList<ProfileController> profileControllers = new ArrayList<>();
    private static Robot instance;
    private double startTime;

    private RunOneMotor runOneMotor;

    @Override
    public void robotInit()
    {
        robotLogger = LoggerFactory.createNewLogger(Robot.class);
        dashboardWrapper = new DashboardWrapper();
        dashboard = new StatefulDashboard(dashboardWrapper, Constants.DashboardConstants.FILE);
        dashboard.populate();

        oi = new OI(DriverStation.getInstance());

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();
        ramps = new Ramps();
        smellyParser = new SmellyParser(dashboardWrapper, Constants.SmellyParser.SMELLY_FOLDER);
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
        updateState(Constants.MotorMap.Mode.DISABLED);

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
        smellyParser.constructPath(); //Needs to happen before SmellyDrive

        robotLogger.info("AutoInit");
        Scheduler.getInstance().removeAll();
        drivebase.getGyro().initGyro();
        drivebase.setBrakeMode(true);

        Scheduler.getInstance().add(new PlaceCubeInExchangeZone(dashboard, drivebase));
    }

    @Override
    public void autonomousPeriodic()
    {
        GameDataPoller.pollGameData();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();
        oi.initializeBindings(this);

        if(runOneMotor != null)
        {
            runOneMotor.end();
        }

        Scheduler.getInstance().add(new RunTankDrive(drivebase, oi));

        drivebase.setBrakeMode(false);
        Scheduler.getInstance().add(new RunTankDrive(drivebase, oi));
        //Scheduler.getInstance().add(new CollectorRotate(collector, true));
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
        updateState(Constants.MotorMap.Mode.TEST);

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

    public void updateState(Constants.MotorMap.Mode mode)
    {
        for(ProfileController controller : profileControllers)
        {
            controller.updateRobotState(mode);
        }
    }

    public void addProfileController(ProfileController controller)
    {
        profileControllers.add(controller);
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
