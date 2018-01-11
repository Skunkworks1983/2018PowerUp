package org.usfirst.frc.team1983.robot.subsystems;

import java.util.ArrayList;

public class MotorGroup
{
    private Motor master;
    private ArrayList<Motor> motorGroup;
    private boolean reverse;

    public MotorGroup (Motor master, boolean reverse)
    {
        this.master = master;
        this.reverse = reverse;
        motorGroup = new ArrayList<Motor>();
        motorGroup.add(master);
    }

    //Set speed of all motors in group
    public void set(double value)
    {
        if (reverse)
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

