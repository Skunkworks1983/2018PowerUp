package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Constants;
import frc.team1983.utility.control.Motor;

public class Elevator extends Subsystem
{
    public Motor right1, right2;
    public Motor left1, left2;

    public double setpoint;

    public Elevator()
    {
        left1 = new Motor(Constants.MotorMap.Elevator.LEFT1, Constants.MotorMap.Elevator.LEFT1_REVERSED, true);
        left2 = new Motor(Constants.MotorMap.Elevator.LEFT2, Constants.MotorMap.Elevator.LEFT2_REVERSED);

        right1 = new Motor(Constants.MotorMap.Elevator.RIGHT1, Constants.MotorMap.Elevator.RIGHT1_REVERSED, true);
        right2 = new Motor(Constants.MotorMap.Elevator.RIGHT2, Constants.MotorMap.Elevator.RIGHT2_REVERSED);

        right1.configPIDF(0, Constants.PidConstants.ElevatorControlPid.Slot0.P, Constants.PidConstants.ElevatorControlPid.Slot0.I, Constants.PidConstants.ElevatorControlPid.Slot0.D, Constants.PidConstants.ElevatorControlPid.Slot0.F);
        right1.config_IntegralZone(0, Constants.PidConstants.ElevatorControlPid.Slot0.I_ZONE, 0);
        right1.configAllowableClosedloopError(0, Constants.PidConstants.ElevatorControlPid.Slot0.ALLOWABLE_CLOSED_LOOP_ERROR, 1);

        right1.configPIDF(1, Constants.PidConstants.ElevatorControlPid.Slot1.P, Constants.PidConstants.ElevatorControlPid.Slot1.I, Constants.PidConstants.ElevatorControlPid.Slot1.D, Constants.PidConstants.ElevatorControlPid.Slot1.F);
        right1.config_IntegralZone(1, Constants.PidConstants.ElevatorControlPid.Slot1.I_ZONE, 0);
        right1.configAllowableClosedloopError(1, Constants.PidConstants.ElevatorControlPid.Slot1.ALLOWABLE_CLOSED_LOOP_ERROR, 1);

        right1.setSensorPhase(true);
        right1.configClosedloopRamp(0.5, 10);
        right1.setSelectedSensorPosition(0, 0, 100);

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

    public void set(ControlMode mode, double value)
    {
        right1.set(mode, value);
    }
}
