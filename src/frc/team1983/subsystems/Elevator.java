package frc.team1983.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
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
    public Elevator()
    {
        //TODO: Check directionality
        leftWinch = new Motor(RobotMap.LEFT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE, false);
        rightWinch = new Motor(RobotMap.RIGHT_WINCH_PORT, ELEVATOR_NEUTRAL_MODE, true);

        winch = new MotorGroup(leftWinch, false);
        winch.addMotor(rightWinch);

        rail = new DioEncoder(RobotMap.RAIL_ENCODER_A_PORT, RobotMap.RAIL_ENCODER_B_PORT, false);
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

    public void initDefaultCommand() {}
}
