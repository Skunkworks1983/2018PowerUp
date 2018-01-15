
package frc.team1983;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public class Robot extends IterativeRobot {

	private OI oi;
	private Drivebase drivebase;
	private Elevator elevator;
	private static Robot instance;

	Command autonomousCommand;

	@Override
	public void robotInit() {
		oi = new OI();
		drivebase = new Drivebase();
		elevator = new Elevator();
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {}

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

	public static Robot getInstance() {
		if (instance == null) {
			instance = new Robot();
		}
		return instance;
	}

}
