package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.DigitalInputWrapper;
import frc.team1983.subsystems.utilities.Motor;
import org.apache.logging.log4j.core.Logger;

//Subsystem that will acquire and expel the Power Cubes.
public class Collector extends Subsystem
{
    private Motor left, right;
    private DigitalInputWrapper leftSwitch, rightSwitch;
    //The collector subsystem
    private Logger logger;

    public Collector()
    {
        left = new Motor(Constants.Robot.Collector.LEFT, Constants.Robot.Collector.LEFT_REVERSED);
        right = new Motor(Constants.Robot.Collector.RIGHT, Constants.Robot.Collector.RIGHT_REVERSED);

        leftSwitch = new DigitalInputWrapper(Constants.Robot.Collector.LEFT_SWITCH, Constants.Robot.Collector.LEFT_SWITCH_REVERSED);
        rightSwitch = new DigitalInputWrapper(Constants.Robot.Collector.RIGHT_SWITCH, Constants.Robot.Collector.RIGHT_SWITCH_REVERSED);

        logger = LoggerFactory.createNewLogger(Collector.class);
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

