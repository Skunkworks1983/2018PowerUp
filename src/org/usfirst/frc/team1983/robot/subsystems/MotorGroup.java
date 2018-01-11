package org.usfirst.frc.team1983.robot.subsystems;

public class MotorGroup { //Bracket on next line
    public Motor master, slave0, slave1; //Make motorgroup able to have x number of motors as part of it (hint: ArrayList[Motor])

    public MotorGroup (Motor master, Motor slave0, Motor slave1)
    {
        this.master = master;
        this.slave0 = slave0;
        this.slave1 = slave1;
        slave0.follow(master);
        slave1.follow(master);
    }

    //What do I do?
    public void set(double value)
    {
        master.set(value);
    }
}

