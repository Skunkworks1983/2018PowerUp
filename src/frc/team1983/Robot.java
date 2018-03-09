package frc.team1983;

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.GameDataPoller;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.services.parser.SmellyParser;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.util.control.ProfileController;
import frc.team1983.subsystems.utilities.inputwrappers.GyroPidInput;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.path.Path;
import frc.team1983.util.path.PathComponent;
import frc.team1983.util.path.PathTanarc;
import frc.team1983.util.path.PathTanline;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Robot extends IterativeRobot
{
    private static Logger robotLogger;
    private OI oi;
    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private Ramps ramps;
    private StatefulDashboard dashboard;
    private DashboardWrapper dashboardWrapper;
    private Subsystem subsystem;
    private GyroPidInput pidSource;

    private ArrayList<ProfileController> profileControllers = new ArrayList<>();
    private double startTime;

    private RunOneMotor runOneMotor;

    private static Robot instance;

    private SendableChooser autonomousSelector;
    private SmellyParser smellyParser;
    private Command autonomousCommand;

    @Override
    public void robotInit()
    {
        robotLogger = LoggerFactory.createNewLogger(Robot.class);
        //dashboardWrapper = new DashboardWrapper();
        //dashboard = new StatefulDashboard(dashboardWrapper, Constants.DashboardConstants.FILE);
        //dashboard.populate();

        oi = new OI(DriverStation.getInstance());

        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();
        ramps = new Ramps();

        //smellyParser = new SmellyParser(dashboardWrapper, Constants.SmellyParser.SMELLY_FOLDER);

        robotLogger.info("robotInit");

        /*autonomousSelector = new SendableChooser();
        autonomousSelector.addDefault("Exchange Zone", new PlaceCubeInExchangeZone(drivebase, dashboard));
        autonomousSelector.addObject("Scale", new PlaceCubeInScale(drivebase, dashboard));
        autonomousSelector.addObject("Switch", new PlaceCubeInSwitch(drivebase, dashboard));
        SmartDashboard.putData("Autonomous Mode Selector", autonomousSelector);
        */
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

        //dashboard.store();

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
        updateState(Constants.MotorMap.Mode.DISABLED);
        Scheduler.getInstance().removeAll();
        drivebase.getGyro().initGyro();

        CommandGroup cmds = new CommandGroup();

        cmds.addSequential(new DriveFeet(drivebase, 6, 2));

        Scheduler.getInstance().add(cmds);

        /*
        CommandGroup auto = new CommandGroup();

        auto.addSequential(new DriveFeet(drivebase, 16, 2.5));
        auto.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.SWITCH, elevator));
        auto.addSequential(new DriveArc(drivebase, 1, 180, 1, true));

        auto.addSequential(new CollectorExpel(collector, true, 0.3));
        auto.addSequential(new DriveFeet(drivebase, -1.5, 0.5));
        auto.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.BOTTOM, elevator));

        auto.addSequential(new CollectorIntake(collector, 2));
        auto.addParallel(new DriveFeet(drivebase, 2, 0.5));
        auto.addSequential(new DriveArc(drivebase, 1.5, -160, 1, true));
        auto.addSequential(new SetElevatorSetpoint(Constants.OIMap.Setpoint.TOP, elevator));

        auto.addSequential(new DriveFeet(drivebase, 3, 1.5));
        auto.addSequential(new CollectorExpel(collector, true, 1));

        //cmds.addSequential(new DriveArc(drivebase, 6, 90, 2, false));
        //cmds.addSequential(new DriveArc(drivebase, 6, -90, 2, true));

        Scheduler.getInstance().add(auto);
        */
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
        updateState(Constants.MotorMap.Mode.DISABLED);
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
