package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;

//Subsystem that will aqcuire and expel the Power Cubes.
public class Collector extends Subsystem
{
    //The collector subsystem
    private MotorGroup collectorMotors;
    private Motor left, right;

    public Collector()
    {
        left = new Motor(Constants.MotorMap.CollectorPorts.LEFT_COLLECTOR_PORT, NeutralMode.Coast, Constants.MotorMap.CollectorReversed.LEFT_COLLECTOR_REVERSE);
        right = new Motor(Constants.MotorMap.CollectorPorts.RIGHT_COLLECTOR_PORT, NeutralMode.Coast, Constants.MotorMap.CollectorReversed.RIGHT_COLLECTOR_REVERSE);

        collectorMotors = new MotorGroup(left, false);
        collectorMotors.addMotor(right);
    }

    public void setSpeed(double value)
    {
        collectorMotors.set(value);
    }

    public void initDefaultCommand()
    {
    }
}

