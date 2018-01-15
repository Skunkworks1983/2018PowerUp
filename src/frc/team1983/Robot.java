
package frc.team1983;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.services.Logger;
import frc.team1983.services.OI;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;

import java.sql.Driver;

public class Robot extends IterativeRobot
{
	private static Robot instance;
	private OI oi;
	private Logger logger;
	private Drivebase drivebase;

	@Override
	public void robotInit()
	{
		drivebase = new Drivebase();
		oi = new OI(Constants.OIInputType.DOUBLEJOY, DriverStation.getInstance());
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
		oi.update();
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

	public Logger getLogger()
	{
		return logger;
	}

	public static Robot getInstance() {
		if(instance == null) {
			instance = new Robot();
		}

		return instance;
	}

}
