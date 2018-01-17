
package frc.team1983;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Ramps;
import frc.team1983.subsystems.Drivebase;


public class Robot extends IterativeRobot
{
	private OI oi;
	private Drivebase drivebase;
	private Ramps ramps;
	private Collector collector;

    private static Robot instance;

	Command autonomousCommand;

	@Override
	public void robotInit()
    {
		oi = new OI();
		drivebase = new Drivebase();
		collector = new Collector();
		ramps = new Ramps();
	}

	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {}


	@Override
	public void autonomousPeriodic()
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {}

	@Override
	public void teleopPeriodic()
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {}

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
