package frc.team1983.subsystems.utilities;

import java.util.ArrayList;

//A collection of Motors, all part of the same subsystem that run simultaneously.
public class MotorGroup
{
    private Motor master;
    private ArrayList<Motor> motorGroup;
    private boolean reverse;

    public MotorGroup(Motor master, boolean reverse)
    {
        this.master = master;
        this.reverse = reverse;
        motorGroup = new ArrayList<>();
        motorGroup.add(master);
    }

    //Set speed of all motors in group
    public void set(double value)
    {
        if(reverse)
        {
            master.set(-value);
        }
        else
        {
            master.set(value);
        }
    }

    //Add a motor to the group
    public void addMotor(Motor motor)
    {
        motorGroup.add(motor);
        motor.follow(master);
    }
}

