package frc.team1983;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.debugging.DSButtons;
import frc.team1983.commands.debugging.DSTestFunctionality;
import frc.team1983.commands.drivebase.TankDrive;
import frc.team1983.commands.elevator.ElevatorControl;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.OI;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.utilities.Motor;
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
    private static Robot instance;
    private DSTestFunctionality DSTest;
    private DSButtons ButtonTest;

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

        oi.initialize(this);
        robotLogger.info("robotInit");
    }


    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();
        dashboard.store();
    }

    @Override
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {
        Scheduler.getInstance().removeAll();
        dashboard.populate();
        Scheduler.getInstance().add(new ElevatorControl(elevator, dashboard));
        robotLogger.info("AutoInit");
    }

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        if (ButtonTest != null)
        {
            ButtonTest.end();
        }
        Scheduler.getInstance().removeAll();
        Scheduler.getInstance().add(new TankDrive(drivebase, oi));
        dashboard.populate();
        Scheduler.getInstance().add(new ElevatorControl(elevator, dashboard));
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit()
    {
        ArrayList<Motor> motors;
        motors = new ArrayList<>();

        DigitalInput motorUp;
        DigitalInput motorDown;
        AnalogInput manualSpeed;

        motorUp = new DigitalInput(5);
        motorDown = new DigitalInput(4);
        manualSpeed = new AnalogInput(2);


        if (ButtonTest == null)
        {
            ButtonTest = new DSButtons();
        }

        for(int i=0; i<16;i++)
        {
            motors.add(new Motor(i, NeutralMode.Coast, false));
            motors.get(i).setNeutralMode(NeutralMode.Coast);
            System.out.println("Initialized motor " + i);
        }

        ButtonTest.initialize(motors, motorUp, motorDown, manualSpeed);

       /* if(DSTest == null)
        {
            DSTest = new DSTestFunctionality();
        } */
            // these two would allow the ds test class to run within test

      //  runMotor = new RunOneMotor(oi);
        // this calls the RunOneMotor in testInit
        // runMotor.execute();
        // this would be called in testPeriodic
    }
    @Override
    public void testPeriodic()
    {
        //DSTest.execute();
        ButtonTest.execute();
       // runMotor.execute();
        // calling execute in here allows it to run within test on the frc ds
        // since there is no end method called, operator must manually disable ds
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
