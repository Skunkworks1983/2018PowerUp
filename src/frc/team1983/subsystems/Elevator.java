package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.Robot;
import frc.team1983.commands.elevator.SetElevatorSetpoint;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.sensors.DioEncoder;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;

//The elevator subsystem
public class Elevator extends Subsystem
{
    private Motor leftWinch, rightWinch;
    private MotorGroup winch;

    private DioEncoder rail;

    private static NeutralMode ELEVATOR_NEUTRAL_MODE = NeutralMode.Brake;

    private double setpoint;

    public Elevator()
    {

        //The winches that actuate the collector
        leftWinch = new Motor(Constants.MotorMap.ElevatorWinchPorts.LEFT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE,
                              Constants.MotorMap.ElevatorWinchReversed.LEFT_WINCH_REVERSE);
        rightWinch = new Motor(Constants.MotorMap.ElevatorWinchPorts.RIGHT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE,
                               Constants.MotorMap.ElevatorWinchReversed.RIGHT_WINCH_REVERSE);

        winch = new MotorGroup(leftWinch, false);
        winch.addMotor(rightWinch);

        //The encoders on the rail
        rail = new DioEncoder(Constants.MotorMap.RailEncoders.RAIL_ENCODER_A_PORT,
                              Constants.MotorMap.RailEncoders.RAIL_ENCODER_B_PORT,
                              Constants.MotorMap.RailEncoders.RAIL_ENCODER_REVERSE);

        //Initialize the elevator to its resting position
        setSetpoint(Constants.OIMap.Setpoint.RESTING);
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
        new SetElevatorSetpoint(Constants.OIMap.Setpoint.RESTING, this, Robot.getInstance().getOI());
    }

    public double getSetpoint()
    {
        return setpoint * Constants.MotorMap.RailEncoders.ELEVATOR_ENCODER_SCALAR;
    }

    public void setSetpoint(Constants.OIMap.Setpoint newSetpoint)
    {
        //The constants for each preset of elevator height
        switch(newSetpoint)
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
}
