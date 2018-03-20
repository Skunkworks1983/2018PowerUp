package frc.team1983;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.autonomous.deadreckoningautos.SwitchCloseScaleClose;
import frc.team1983.commands.climber.MonitorCams;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
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
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.util.control.ProfileController;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

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
    private Subsystem subsystem;
    private GyroPidInput pidSource;
    private AutoManager autoManager;
    private SendableChooser autonomousSelector;
    private AutoManager.OwnedSide robotPosition;

    private RunOneMotor runOneMotor;

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
        autoManager = new AutoManager(dashboardWrapper);

        oi = new OI();

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();
        climber = new Climber();

        robotLogger.info("robotInit");

        //autonomousSelector = new SendableChooser();
        //autonomousSelector.addDefault("Robot is on the left", AutoManager.OwnedSide.LEFT);
        //autonomousSelector.addObject("Robot is on the right", AutoManager.OwnedSide.RIGHT);
        //SmartDashboard.putData("Robot position", autonomousSelector);

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
        updateState(Constants.MotorMap.Mode.DISABLED);

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
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
        autoManager.execute();

        robotLogger.info("Left: {} Right: {} Gyro: {}", drivebase.getLeftDistance(), drivebase.getRightDistance(), drivebase.getGyro().getAngle());
    }

    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();
        updateState(Constants.MotorMap.Mode.TELEOP);
        //oi.initializeBindings(this);

        climber.disengageDogGear();

        Scheduler.getInstance().add(new RunTankDrive(drivebase, oi));

        drivebase.setBrakeMode(false);
        //Scheduler.getInstance().add(new RunTankDrive(drivebase, oi));
        //Scheduler.getInstance().add(new CollectorRotate(collector, true));
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();

        SmartDashboard.updateValues();
        SmartDashboard.putBoolean("Left collector limit switch", collector.isLeftSwitchDown());
        SmartDashboard.putBoolean("Right collector limit switch", collector.isRightSwitchDown());

        //robotLogger.info("gyro{}", drivebase.getGyro().getAngle());
        //robotLogger.info("Left drivebase encoder is {}", drivebase.getLeftEncoderValue());
        //robotLogger.info("Right drivebase encoder is {}", drivebase.getRightEncoderValue());

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

    }

    @Override
    public void testPeriodic()
    {

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

    public boolean isCollectorUp()
    {
        return collector.isUp();
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
