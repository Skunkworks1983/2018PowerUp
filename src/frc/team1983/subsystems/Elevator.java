package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
import frc.team1983.utility.control.Motor;

public class Elevator extends Subsystem
{
    private Motor right1, right2;
    private Motor left1, left2;

    public Elevator()
    {
        left1 = new Motor(Constants.MotorMap.Elevator.LEFT1, Constants.MotorMap.Elevator.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Elevator.LEFT2, Constants.MotorMap.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.MotorMap.Elevator.RIGHT1, Constants.MotorMap.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Elevator.RIGHT2, Constants.MotorMap.Elevator.RIGHT2_REVERSED);

        right1.config_kP(0, Constants.Gains.Elevator.P, 0);
        right1.config_kP(0, Constants.Gains.Elevator.I, 0);
        right1.config_kP(0, Constants.Gains.Elevator.D, 0);
        right1.config_kP(0, Constants.Gains.Elevator.F, 0);
        right1.config_IntegralZone(0, Constants.Gains.Elevator.I_ZONE, 0);

        right1.setSensorPhase(true);
        right1.configClosedloopRamp(0.5, 0);
        right1.setSelectedSensorPosition(0, 0, 0);

        right2.follow(right1);
        left1.follow(right1);
        left2.follow(right1);
    }

    @Override
    public void initDefaultCommand()
    {

    }

    @Override
    public void periodic()
    {

    }

    public static double toInches(double ticks)
    {
        return 0;
    }

    public static double toTicks(double inches)
    {
        return 0;
    }

    public double getHeight()
    {
        return toInches(right1.getSelectedSensorPosition());
    }

    public void set(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }

    public void setNeutralMode(boolean coast)
    {
        Motor[] motors = {left1, left2, right1, right2};
        for(Motor motor : motors)
            motor.setNeutralMode(coast ? NeutralMode.Coast : NeutralMode.Brake);
    }
}
