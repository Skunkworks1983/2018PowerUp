package frc.team1983;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.sun.org.apache.bcel.internal.classfile.ConstantDouble;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.drivebase.DriveFeet;
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
import frc.team1983.util.control.ClosedLoopGains;
import frc.team1983.util.control.ProfileController;
import frc.team1983.util.path.Path;
import org.apache.logging.log4j.core.Logger;

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

    private ClosedLoopGains leftGains;
    private ClosedLoopGains rightGains;

    private ClosedLoopGains straightGains;
    private ClosedLoopGains turnGains;

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
        //dashboard.populate();

        //lemme channel my inner Nathan: vomits
        /*dashboard.add(this, "LEFT_P", Constants.PidConstants.Drivebase.Left.MAIN.get_kP());
        dashboard.add(this, "LEFT_I", Constants.PidConstants.Drivebase.Left.MAIN.get_kI());
        dashboard.add(this, "LEFT_D", Constants.PidConstants.Drivebase.Left.MAIN.get_kD());
        dashboard.add(this, "LEFT_F", Constants.PidConstants.Drivebase.Left.MAIN.get_kF());
        dashboard.add(this, "LEFT_KV", Constants.PidConstants.Drivebase.Left.MAIN.get_kV());

        dashboard.add(this, "RIGHT_P", Constants.PidConstants.Drivebase.Right.MAIN.get_kP());
        dashboard.add(this, "RIGHT_I", Constants.PidConstants.Drivebase.Right.MAIN.get_kI());
        dashboard.add(this, "RIGHT_D", Constants.PidConstants.Drivebase.Right.MAIN.get_kD());
        dashboard.add(this, "RIGHT_F", Constants.PidConstants.Drivebase.Right.MAIN.get_kF());
        dashboard.add(this, "LEFT_KV", Constants.PidConstants.Drivebase.Right.MAIN.get_kV());

        dashboard.add(this, "STRAIGHT_P", Constants.PidConstants.Drivebase.AUX_STRAIGHT.get_kP());
        dashboard.add(this, "STRAIGHT_I", Constants.PidConstants.Drivebase.AUX_STRAIGHT.get_kI());
        dashboard.add(this, "STRAIGHT_D", Constants.PidConstants.Drivebase.AUX_STRAIGHT.get_kD());
        dashboard.add(this, "STRAIGHT_F", Constants.PidConstants.Drivebase.AUX_STRAIGHT.get_kF());

        dashboard.add(this, "TURN_P", Constants.PidConstants.Drivebase.AUX_TURN.get_kP());
        dashboard.add(this, "TURN_I", Constants.PidConstants.Drivebase.AUX_TURN.get_kI());
        dashboard.add(this, "TURN_D", Constants.PidConstants.Drivebase.AUX_TURN.get_kD());
        dashboard.add(this, "TURN_F", Constants.PidConstants.Drivebase.AUX_TURN.get_kF());*/

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

        //dashboard.store();

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

        //Scheduler.getInstance().add(new DriveFeet(drivebase, 4, 0.75, 0));
        //Scheduler.getInstance().add(new TurnDegree(drivebase, 45, 0.75));

        /*Scheduler.getInstance().add(new Path(new ArrayList<>(Arrays.asList(
                new DriveFeet(drivebase, 4, 1, 0),
                new DriveFeet(drivebase, -4, 1, 0),
                new DriveFeet(drivebase, 4, 1, 0)
                new TurnDegree(drivebase, 15, 1),
                new DriveFeet(drivebase, 3, 1, 0)
        ))));*/

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

    //Synchronized so that we hopefully don't end up with more than one instance
    public synchronized static Robot getInstance()
    {
        if(instance == null)
        {
            instance = new Robot();
        }

        return instance;
    }

    public ClosedLoopGains getLeftGains()
    {
        //return new ClosedLoopGains(dashboard.getDouble(this, "LEFT_P"), dashboard.getDouble(this, "LEFT_I"), dashboard.getDouble(this, "LEFT_D"), dashboard.getDouble(this, "LEFT_F"));
        return Constants.PidConstants.Drivebase.Left.MAIN;
    }

    public ClosedLoopGains getRightGains()
    {
        //return new ClosedLoopGains(dashboard.getDouble(this, "RIGHT_P"), dashboard.getDouble(this, "RIGHT_I"), dashboard.getDouble(this, "RIGHT_D"), dashboard.getDouble(this, "RIGHT_F"));
        return Constants.PidConstants.Drivebase.Left.MAIN;

    }

    public ClosedLoopGains getStraightGains()
    {
        //return new ClosedLoopGains(dashboard.getDouble(this, "TURN_P"), dashboard.getDouble(this, "TURN_I"), dashboard.getDouble(this, "TURN_D"), dashboard.getDouble(this, "TURN_F"));
        return Constants.PidConstants.Drivebase.AUX_STRAIGHT;
    }

    public ClosedLoopGains getTurnGains()
    {
        //return new ClosedLoopGains(dashboard.getDouble(this, "STRAIGHT_P"), dashboard.getDouble(this, "STRAIGHT_I"), dashboard.getDouble(this, "STRAIGHT_D"), dashboard.getDouble(this, "STRAIGHT_F"));
        return Constants.PidConstants.Drivebase.AUX_TURN;
    }

    public double getLeftKs()
    {
        return dashboard.getDouble(this,"LEFT_KS");
    }

    public double getRightKs()
    {
        return dashboard.getDouble(this, "RIGHT_KS");
    }
}
