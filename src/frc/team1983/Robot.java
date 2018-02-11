package frc.team1983;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.commands.debugging.DSButtons;
import frc.team1983.commands.debugging.DSTestFunctionality;
import frc.team1983.commands.debugging.RunOneMotor;
import frc.team1983.commands.elevator.ElevatorControl;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;
import frc.team1983.subsystems.utilities.Motor;

import java.awt.*;
import java.util.ArrayList;

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
    private DSButtons ButtonTest;

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
        if (ButtonTest != null)
        {
            ButtonTest.end();
        }

       /* if (DSTest != null)
        {
            DSTest.end();
        } */
       // Scheduler.getInstance().removeAll();
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
        ArrayList<Motor> motors;
        motors = new ArrayList<>();
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

        ButtonTest.initialize(motors);

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
