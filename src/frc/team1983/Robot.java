package frc.team1983;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.autonomous.doublecubeautos.SwitchCloseScaleClose;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.DriveStraight;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.util.control.ProfileController;
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
    private GyroPidInput pidSource;

    private ArrayList<ProfileController> profileControllers = new ArrayList<ProfileController>();

    private RunOneMotor runOneMotor;

    private static Robot instance;

    private SendableChooser autonomousSelector;
    private GameDataPoller.OwnedSide robotPosition;

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

        robotLogger.info("robotInit");

        autonomousSelector = new SendableChooser();
        autonomousSelector.addDefault("Robot is on the left", GameDataPoller.OwnedSide.LEFT);
        autonomousSelector.addObject("Robot is on the right", GameDataPoller.OwnedSide.RIGHT);
        SmartDashboard.putData("Robot position", autonomousSelector);
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
        robotLogger.info("AutoInit");
        Scheduler.getInstance().removeAll();
        drivebase.getGyro().initGyro();
        drivebase.setBrakeMode(true);

        robotPosition = (GameDataPoller.OwnedSide) autonomousSelector.getSelected();
        CommandGroup group = new CommandGroup();
        group.addSequential(new DriveStraight(drivebase, dashboard, -3));

        // Scheduler.getInstance().add(group);
        Scheduler.getInstance().add(new SwitchCloseScaleClose(drivebase, dashboard, oi, elevator, collector, GameDataPoller.OwnedSide.LEFT ));
        //Scheduler.getInstance().add(new DoubleCubeAutoSelector(drivebase, dashboard, oi, elevator, collector, robotPosition));

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
        SmartDashboard.updateValues();
        SmartDashboard.putBoolean("Left collector limit switch", collector.isLeftSwitchDown());
        SmartDashboard.putBoolean("Right collector limit switch", collector.isRightSwitchDown());
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
