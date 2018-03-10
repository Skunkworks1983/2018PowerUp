package frc.team1983;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.collector.CollectorExpel;
import frc.team1983.commands.collector.CollectorIntake;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.TurnDegree;
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
import frc.team1983.util.path.Path;
import org.apache.logging.log4j.core.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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

    public Robot()
    {
        Robot.instance = this;
    }

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

        robotLogger.info("Smelly parser is null before construction: {}", smellyParser == null);
        smellyParser = new SmellyParser(dashboardWrapper, Constants.SmellyParser.SMELLY_FOLDER);

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
        Scheduler.getInstance().removeAll();
        updateState(Constants.MotorMap.Mode.AUTO);

        drivebase.getGyro().initGyro();


        Path testpath = new Path(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 0, 0, ActionsEnum.SET_COLLECTOR_POSITION_DOWN),
                new DriveFeet(drivebase, 6, 2, ActionsEnum.SET_ELEVATOR_SETPOINT_MID),
                new DriveArc(drivebase, 3, 90, 2, ActionsEnum.SET_ELEVATOR_SETPOINT_TOP)
                                                              )));

        Scheduler.getInstance().add(testpath.getCommands());
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
        updateState(Constants.MotorMap.Mode.TELEOP);

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
        updateState(Constants.MotorMap.Mode.TEST);
    }

    @Override
    public void testPeriodic()
    {

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
            robotLogger.info("Constructing new robot");
            instance = new Robot();
        }

        return instance;
    }
}
