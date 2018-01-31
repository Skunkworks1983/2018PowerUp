package frc.team1983.commands.collector;

import frc.team1983.commands.CommandBase;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Collector;

//Runs the collector inwards, holding cube in place
public class CollectorIntake extends CommandBase
{
    private Collector collector;
    private double intakeSpeed, rotateSpeed;
    private int switchStates;

    public CollectorIntake(Collector collector)
    {
        this.collector = collector;
        this.intakeSpeed = Constants.MotorSetpoints.COLLECTOR_INTAKE_SPEED;
        this.rotateSpeed = Constants.MotorSetpoints.COLLECTOR_ROTATE_SPEED;

        requires(collector);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        //Represent both switch states in a single integer
        switchStates = 0;
        if(collector.isLeftPressed())
        {
            switchStates = 10;
        }
        if(collector.isRightPressed())
        {
            switchStates++;
        }
        //Use a switch to determine what to do
        switch(switchStates)
        {
            case (0): //If neither switch is active, full intake
                collector.setLeft(intakeSpeed);
                collector.setRight(intakeSpeed);
                break;
            case (1): //If only right, intake left, expel right a little to try to balance it out
                collector.setLeft(intakeSpeed);
                collector.setRight(rotateSpeed);
                break;
            case (10): //If only left, intake right, expel left a little to try to balance it out
                collector.setRight(intakeSpeed);
                collector.setLeft(rotateSpeed);
                break;
            case (11): //If both, cube is in place and motors can be off
                collector.setRight(0.0);
                collector.setLeft(0.0);
                break;
        }
    }

    @Override
    public boolean isFinished() //isFinished when expel starts
    {
        return false;
    }

    @Override
    public void end()
    {
        collector.setLeft(0.0);
        collector.setRight(0.0);
    }

    @Override
    public void interrupted()
    {
        this.end();
    }
}
