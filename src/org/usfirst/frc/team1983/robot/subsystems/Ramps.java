package org.usfirst.frc.team1983.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1983.robot.RobotMap;
import org.usfirst.frc.team1983.robot.subsystems.utilities.DigitalOutput;
import org.usfirst.frc.team1983.robot.subsystems.utilities.MotorGroup;

public class Ramps extends Subsystem
{
    private DigitalOutput beep, boop;

    public Ramps()
    {
        beep = new DigitalOutput(RobotMap.RAMP_LEFT_PORT);
        boop = new DigitalOutput(RobotMap.RAMP_RIGHT_PORT);
    }

    public void activateLeft() {
        beep.set(true);
    }
    public void activateRight() {
        boop.set(true);
    }
    public void disableRamps() {
        beep.set(false);
        beep.set(false);
    }


/

    public void initDefaultCommand()
    {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

