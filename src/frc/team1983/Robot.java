package frc.team1983;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.commands.drivebase.RunTankDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.TurnDegree;
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
        Scheduler.getInstance().removeAll();
        updateState(Constants.MotorMap.Mode.AUTO);

        drivebase.getGyro().initGyro();

        CommandGroup cmds = new CommandGroup();

        DriveArc a1 = new DriveArc(drivebase, 4, 90, 2);
        //DriveFeet d1 = new DriveFeet(drivebase, 4, 2);

        //DriveProfile.stitch(a1, d1);

        cmds.addSequential(a1);
        //cmds.addSequential(d1);

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
            instance = new Robot();
        }
        return instance;
    }
}
