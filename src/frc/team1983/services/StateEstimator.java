package frc.team1983.services;

import frc.team1983.Constants;
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
    private double lastTimestamp;
    private Vector2 position;

    public StateEstimator(Drivebase drivebase, Pigeon pigeon)
    {
        this.drivebase = drivebase;
        this.pigeon = pigeon;

        lastLeft = 0;
        lastRight = 0;
        position = new Vector2(0, 0);

        thread = new Thread(this);
    }

    public StateEstimator()
    {
        this(Robot.getInstance().getDrivebase(), Robot.getInstance().getPigeon());
    }

    public synchronized Vector2 getPosition()
    {
        return position;
    }

    public synchronized void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public synchronized void start()
    {
        thread.start();
    }

    public synchronized void reset()
    {
        position = new Vector2(0, 0);
    }

    protected synchronized void execute()
    {
        double left = drivebase.getLeftPosition();
        double right = drivebase.getRightPosition();

        double dLeft = left - lastLeft;
        double dRight = right - lastRight;

        double displacement = (dLeft + dRight) / 2;
        double heading = pigeon.getFusedHeading() * Math.PI / 180;

        position = Vector2.add(position, (Vector2.scale(new Vector2(Math.sin(heading), Math.cos(heading)), displacement)));

        lastLeft = left;
        lastRight = right;
        lastTimestamp = System.currentTimeMillis();
    }

    @Override
    public void run()
    {
        while(true)
        {
            execute();

            try
            {
                Thread.sleep((long) 1000.0 / Constants.Estimator.UPDATE_RATE);
            }
            catch(InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
