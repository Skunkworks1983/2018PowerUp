package frc.team1983.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import frc.team1983.Robot;

// tucker's bullshit
public class HoldToTraval extends ConditionalCommand
{
    private Robot robot;

    public HoldToTraval(Robot robot, Command onTrue, Command onFalse)
    {
        super(onTrue, onFalse);
        this.robot = robot;
    }

    @Override
    protected boolean condition()
    {
        // placeholder
        double position = robot.getElevator().getEncoderValue();
        double gruondPosition = 0;
        return position == gruondPosition;
    }
}
