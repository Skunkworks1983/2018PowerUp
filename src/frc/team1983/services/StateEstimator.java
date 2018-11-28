package frc.team1983.services;

import frc.team1983.Robot;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.utility.math.Vector2;
import frc.team1983.utility.sensors.Pigeon;

public class StateEstimator implements Runnable
{
    private Drivebase drivebase;
    private Pigeon pigeon;
    private Thread thread;

    private double lastLeft, lastRight;
    private Vector2 position;

    public StateEstimator(Drivebase drivebase, Pigeon pigeon)
    {
        this.drivebase = drivebase;
        this.pigeon = pigeon;

        thread = new Thread(this);
        //thread.start();
    }

    public StateEstimator()
    {
        this(Robot.getInstance().getDrivebase(), Robot.getInstance().getPigeon());
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void reset()
    {
        position = new Vector2(0, 0);
    }

    @Override
    public void run()
    {
        double dLeft = drivebase.getLeftPosition() - lastLeft;
        double dRight = drivebase.getRightPosition() - lastRight;

        double displacement = (dLeft + dRight) / 2;
        double heading = pigeon.getFusedHeading() * Math.PI / 180;

        position.add(Vector2.scale(new Vector2(Math.sin(heading), Math.cos(heading)), displacement));

        lastLeft = drivebase.getLeftPosition();
        lastRight = drivebase.getRightPosition();
    }
}
