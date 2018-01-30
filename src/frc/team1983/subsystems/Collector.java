package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;

//Subsystem that will acquire and expel the Power Cubes.
public class Collector extends Subsystem
{
    private Motor left, right;
    private DigitalInput leftSwitch, rightSwitch;

    public Collector()
    {
        left = new Motor(Constants.MotorMap.CollectorPorts.LEFT_COLLECTOR_PORT, NeutralMode.Coast,
                         Constants.MotorMap.CollectorReversed.LEFT_COLLECTOR_REVERSE);
        right = new Motor(Constants.MotorMap.CollectorPorts.RIGHT_COLLECTOR_PORT, NeutralMode.Coast,
                          Constants.MotorMap.CollectorReversed.RIGHT_COLLECTOR_REVERSE);
        leftSwitch = new DigitalInput(Constants.MotorMap.CollectorPorts.LEFT_SWITCH_PORT);
        rightSwitch = new DigitalInput(Constants.MotorMap.CollectorPorts.RIGHT_SWITCH_PORT);
    }

    public boolean getLeft()
    {
        return leftSwitch.get();
    }

    public boolean getRight()
    {
        return rightSwitch.get();
    }

    public void setLeft(double value)
    {
        left.set(value);
    }

    public void setRight(double value)
    {
        right.set(value);
    }

    public void initDefaultCommand()
    {
    }
}

