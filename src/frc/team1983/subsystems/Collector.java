package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.DigitalInputWrapper;
import frc.team1983.subsystems.utilities.Motor;

//Subsystem that will acquire and expel the Power Cubes.
public class Collector extends Subsystem
{
    private Motor left, right;
    private DigitalInputWrapper leftSwitch, rightSwitch;

    public Collector()
    {
        left = new Motor(Constants.MotorMap.Collector.LEFT, Constants.MotorMap.Collector.LEFT_REVERSED);
        right = new Motor(Constants.MotorMap.Collector.RIGHT, Constants.MotorMap.Collector.RIGHT_REVERSED);

        leftSwitch = new DigitalInputWrapper(Constants.MotorMap.Collector.LEFT_SWITCH, Constants.MotorMap.Collector.LEFT_SWITCH_REVERSED);
        rightSwitch = new DigitalInputWrapper(Constants.MotorMap.Collector.RIGHT_SWITCH, Constants.MotorMap.Collector.RIGHT_SWITCH_REVERSED);
    }

    public void initDefaultCommand()
    {

    }

    public void setLeft(ControlMode mode, double value)
    {
        left.set(mode, value);
    }

    public void setRight(ControlMode mode, double value)
    {
        right.set(mode, value);
    }

    public boolean isLeftSwitchDown()
    {
        return leftSwitch.get();
    }

    public boolean isRightSwitchDown()
    {
        return rightSwitch.get();
    }
}

