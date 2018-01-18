package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1983.commands.SetElevatorSetpoint;
import frc.team1983.settings.Misc;
import frc.team1983.settings.PidValues;
import frc.team1983.settings.RobotMap;
import frc.team1983.subsystems.sensors.DioEncoder;
import frc.team1983.subsystems.utilities.Motor;
import frc.team1983.subsystems.utilities.MotorGroup;
import frc.team1983.subsystems.utilities.inputwrappers.ElevatorPidInput;
import frc.team1983.subsystems.utilities.outputwrappers.ElevatorControlPidOutput;

public class Elevator extends Subsystem
{
    private Motor leftWinch, rightWinch;
    private MotorGroup winch;

    private DioEncoder rail;

    private PIDController controller;
    private PIDSource pidIn;
    private PIDOutput pidOut;

    private static NeutralMode ELEVATOR_NEUTRAL_MODE = NeutralMode.Brake;

    public Elevator()
    {
        leftWinch = new Motor(RobotMap.LEFT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE, RobotMap.LEFT_WINCH_REVERSE);
        rightWinch = new Motor(RobotMap.RIGHT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE, RobotMap.RIGHT_WINCH_REVERSE);

        winch = new MotorGroup(leftWinch, false);
        winch.addMotor(rightWinch);

        rail = new DioEncoder(RobotMap.RAIL_ENCODER_A_PORT, RobotMap.RAIL_ENCODER_B_PORT, RobotMap.RAIL_ENCODER_REVERSE);

        pidIn = new ElevatorPidInput(this);
        pidOut = new ElevatorControlPidOutput(this);
        controller = new PIDController(PidValues.ELEVATOR_CONTROL_P, PidValues.ELEVATOR_CONTROL_I, PidValues.ELEVATOR_CONTROL_D,
                                       PidValues.ELEVATOR_CONTROL_F, pidIn, pidOut);
        controller.setSetpoint(0);

        controller.enable();
    }

    //Used by commands to set the speed of the winch. We never want the winch motors running opposite of each other.
    public void setWinchSpeed(double speed)
    {
        winch.set(speed);
    }

    public void setPIDSetpoint(double setpoint)
    {
        controller.setSetpoint((int) setpoint * Misc.ELEVATOR_ENCODER_SCALAR);
    }

    public int getPIDSetpoint()
    {
        return (int) controller.getSetpoint();
    }

    public int getPosition()
    {
        return rail.get();
    }

    public void initDefaultCommand()
    {
        new SetElevatorSetpoint(0);
    }
}
