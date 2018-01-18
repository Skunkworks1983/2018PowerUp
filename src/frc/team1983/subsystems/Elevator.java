package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.settings.RobotMap;
import frc.team1983.subsystems.sensors.DioEncoder;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;

public class Elevator extends Subsystem
{
    private Motor leftWinch, rightWinch;
    private MotorGroup winch;

    private DioEncoder rail;

    private static NeutralMode ELEVATOR_NEUTRAL_MODE = NeutralMode.Brake;

    private double setpoint;

    //The elevator mechanism
    public Elevator()
    {
        //The winches that actuate the collector
        leftWinch = new Motor(RobotMap.LEFT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE, RobotMap.LEFT_WINCH_REVERSE);
        rightWinch = new Motor(RobotMap.RIGHT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE, RobotMap.RIGHT_WINCH_REVERSE);

        winch = new MotorGroup(leftWinch, false);
        winch.addMotor(rightWinch);

        //The encoders on the rail
        rail = new DioEncoder(RobotMap.RAIL_ENCODER_A_PORT, RobotMap.RAIL_ENCODER_B_PORT, RobotMap.RAIL_ENCODER_REVERSE);

        //Initialize the elevator to its resting position
        setSetpoint(Setpoint.RESTING);
    }

    //Used by commands to set the speed of the winch. We never want the winch motors running opposite of each other.
    public void setWinchSpeed(double speed)
    {
        winch.set(speed);
    }

    public int getPosition()
    {
        return rail.get();
    }

    public void initDefaultCommand()
    {
        new SetElevatorSetpoint(Setpoint.RESTING);
    }

    public double getSetpoint()
    {
        return setpoint;
    }

    public void setSetpoint(Setpoint newSetpoint)
    {
        //The constants for each preset of elevator height
        switch (newSetpoint)
        {
            case SCALE:
                setpoint = 1;
            case SWITCH:
                setpoint = .3;
            case RESTING:
                setpoint = .05;
            case BOTTOM:
                setpoint = 0;
        }
    }

    //For setting the elevator to a custom setpoint
    public void setSetpoint(double newSetpoint)
    {
        setpoint = newSetpoint;
    }

    //Enums for presets
    public enum Setpoint {
        SCALE,
        SWITCH,
        BOTTOM,
        RESTING
    }
}
