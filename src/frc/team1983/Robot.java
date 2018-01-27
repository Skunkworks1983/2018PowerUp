package frc.team1983;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.debugging.DSTestFunctionality;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.elevator.ElevatorControl;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public class Robot extends IterativeRobot
{


    private OI oi;
    private Drivebase drivebase;
    private Elevator elevator;
    private Collector collector;
    private Ramps ramps;
    private static Robot instance;
    private DSTestFunctionality DSTest;
    private RunOneMotor runMotor;

    @Override
    public void robotInit()
    {
        oi = new OI(Constants.OIMap.Mode.DOUBLE_JOY, DriverStation.getInstance());
        drivebase = new Drivebase();
        collector = new Collector();
        elevator = new Elevator();
        ramps = new Ramps();

        oi.initialize(this);
    }


    @Override
    public void disabledInit()
    {
        Scheduler.getInstance().removeAll();
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
        Scheduler.getInstance().add(new ElevatorControl(elevator));
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
     //   Scheduler.getInstance().add(new RunOneMotor(oi));
        // this would run the RunOneMotor in TeleOp with the joysticks
    }

    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void testInit()
    {
        //DSTest = new DSTestFunctionality();
        //DSTest.execute();
            // these two would allow the ds test class to run within test

        runMotor = new RunOneMotor(oi);
        // this calls the RunOneMotor in testInit
        // runMotor.execute();
        // this would be called in testPeriodic
    }
    @Override
    public void testPeriodic()
    {
        runMotor.execute();
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
