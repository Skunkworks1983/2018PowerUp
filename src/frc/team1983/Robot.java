package frc.team1983;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.commands.autonomous.profiled.SwitchCloseScaleClose;
import frc.team1983.commands.autonomous.profiled.SwitchCloseScaleFar;
import frc.team1983.commands.autonomous.profiled.SwitchFarScaleFar;
import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;
import frc.team1983.commands.drivebase.RunTankDrive;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.automanager.AutoManager;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.services.parser.SmellyParser;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.Ramps;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.path.Path;
import javafx.geometry.Pos;
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
    private DashboardWrapper dashboardWrapper;
    private StatefulDashboard dashboard;
    private AutoManager autoManager;

    private ArrayList<ProfileController> profileControllers = new ArrayList<>();

    private static Robot instance;

    private SendableChooser autonomousSelector;
    private SmellyParser smellyParser;
    private Command autonomousCommand;

    private Path switchFarScaleFar, switchCloseScaleClose, switchCloseScaleFar;

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
        ramps = new Ramps();

        //smellyParser = new SmellyParser(dashboardWrapper, Constants.SmellyParser.SMELLY_FOLDER);

        /*
        autonomousSelector = new SendableChooser();
        autonomousSelector.addDefault("Exchange Zone", new PlaceCubeInExchangeZone(drivebase, dashboard));
        autonomousSelector.addObject("Scale", new PlaceCubeInScale(drivebase, dashboard));
        autonomousSelector.addObject("Switch", new PlaceCubeInSwitch(drivebase, dashboard));
        SmartDashboard.putData("Autonomous Mode Selector", autonomousSelector);
        */

        /*switchCloseScaleFar = new SwitchCloseScaleFar(drivebase);

        switchCloseScaleClose = new SwitchCloseScaleClose(drivebase);

        switchFarScaleFar = new SwitchFarScaleFar(drivebase);*/

        updateState(Constants.MotorMap.Mode.DISABLED);
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
        autoManager.resetGameData();
        drivebase.getGyro().initGyro();
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
    }

    @Override
    public void teleopInit()
    {
        Scheduler.getInstance().removeAll();
        updateState(Constants.MotorMap.Mode.TELEOP);
        oi.initializeBindings(this);
        drivebase.setBrakeMode(false);

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
