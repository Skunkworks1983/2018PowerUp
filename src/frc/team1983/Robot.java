package frc.team1983;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.commands.drivebase.TurnDegree;
import frc.team1983.commands.drivebase.deadreckoning.DifferentialTurnAngle;
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
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import frc.team1983.util.path.Path;
import org.apache.logging.log4j.core.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Robot extends IterativeRobot
{
    private static Logger robotLogger;
    private ArrayList<ProfileController> profileControllers = new ArrayList<>();

    private OI oi;
    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private Climber climber;
    private DashboardWrapper dashboardWrapper;
    private StatefulDashboard dashboard;
    private AutoManager autoManager;
    private RunOneMotor runOneMotor;

    private static Robot instance;

    private boolean autoRan = false;

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
        climber = new Climber();

        // god damn it this has to come after everything else do not touch
        autoManager = new AutoManager(dashboardWrapper);

        robotLogger.info("robotInit");

        oi.initializeBindings(this);

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
        updateState(Constants.MotorMap.Mode.AUTO);

        drivebase.setBrakeMode(true);

        drivebase.getGyro().reset();

        autoRan = true; // blargh
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
        updateState(Constants.MotorMap.Mode.TELEOP);

        climber.disengageDogGear();
        climber.lockForks();

        Scheduler.getInstance().add(new RunTankDrive(drivebase, oi));

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
            //robotLogger.info("Initialized motor " + i);
        }

        runOneMotor.initialize(motors, motorUp, motorDown, manualSpeed);


        Scheduler.getInstance().add(new DifferentialTurnAngle(drivebase, dashboard, 90));
    }

    @Override
    public void testPeriodic()
    {
        Scheduler.getInstance().run();
    }

    private void updateState(Constants.MotorMap.Mode mode)
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

    public AutoManager getAutoManager()
    {
        return autoManager;
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
