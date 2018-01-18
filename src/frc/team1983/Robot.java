package frc.team1983;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.Drivebase;

public class Robot extends IterativeRobot
{
	private static Robot instance;
	private Ramps ramps;
	private OI oi;
	private Collector collector;
	private Drivebase drivebase;
	private Ramps ramps;

	@Override
	public void robotInit()
	{
		drivebase = new Drivebase();
		ramps = new Ramps();
		collector = new Collector();
		oi = new OI(Constants.OIInputType.DOUBLEJOY, DriverStation.getInstance());

		oi.initialize();
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
	}

	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic()
	{

	}

	public Drivebase getDrivebase()
	{
		return drivebase;
	}

	public OI getOI()
	{
		return oi;
	}

	public Ramps getRamps()
	{
		return ramps;
	}

	public Collector getCollector()
    {
	    return collector;
    }

	public static Robot getInstance()
    {
		if (instance == null)
		{
			instance = new Robot();
		}

		return instance;
	}
}
