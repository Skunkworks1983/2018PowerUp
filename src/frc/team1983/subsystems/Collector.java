package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.DigitalInputWrapper;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;
import org.apache.logging.log4j.core.Logger;

//Subsystem that will acquire and expel the Power Cubes.
public class Collector extends Subsystem
{
    //The collector subsystem
    private MotorGroup collectorMotors;
    private Motor left, right;
    private Logger collectorLogger;
    private Motor leftMotor, rightMotor;
    private DigitalInputWrapper leftSwitch, rightSwitch;

    public Collector()
    {
        collectorLogger = LoggerFactory.createNewLogger(Collector.class);

        leftMotor = new Motor(Constants.MotorMap.CollectorPorts.LEFT_COLLECTOR_PORT, NeutralMode.Coast,
                              Constants.MotorMap.CollectorReversed.LEFT_COLLECTOR_REVERSE);
        rightMotor = new Motor(Constants.MotorMap.CollectorPorts.RIGHT_COLLECTOR_PORT, NeutralMode.Coast,
                               Constants.MotorMap.CollectorReversed.RIGHT_COLLECTOR_REVERSE);
        leftSwitch = new DigitalInputWrapper(Constants.MotorMap.CollectorPorts.LEFT_SWITCH_PORT,
                                             Constants.MotorMap.CollectorReversed.LEFT_SWITCH_REVERSE);
        rightSwitch = new DigitalInputWrapper(Constants.MotorMap.CollectorPorts.RIGHT_SWITCH_PORT,
                                              Constants.MotorMap.CollectorReversed.RIGHT_SWITCH_REVERSE);
    }

    public boolean isLeftPressed()
    {
        return leftSwitch.get();
    }

    public boolean isRightPressed()
    {
        return rightSwitch.get();
    }


    public void setLeft(double value)
    {
        leftMotor.set(value);
    }

    public void setRight(double value)
    {
        rightMotor.set(value);
    }

    public void initDefaultCommand()
    {
    }
}

