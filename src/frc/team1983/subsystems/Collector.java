package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.settings.RobotMap;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;

public class Collector extends Subsystem
{

    //The collector subsystem. IDK why you would want to run the motors
    private MotorGroup collectorMotors;
    private Motor left, right;

    public Collector()
    {
        left = new Motor(RobotMap.LEFT_COLLECTOR_PORT, NeutralMode.Coast, RobotMap.LEFT_COLLECTOR_REVERSE);
        right = new Motor(RobotMap.RIGHT_COLLECTOR_PORT, NeutralMode.Coast, RobotMap.RIGHT_COLLECTOR_REVERSE);

        collectorMotors = new MotorGroup(left, false);
        collectorMotors.addMotor(right);
    }

    public void setSpeed(double value)
    {
        collectorMotors.set(value);
    }

    public void initDefaultCommand() {}
}

