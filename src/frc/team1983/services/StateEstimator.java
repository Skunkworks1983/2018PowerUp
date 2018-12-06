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

    private double lastLeftPosition = 0, lastRightPosition = 0;
    private double lastTimestamp = 0;
    private Vector2 position = new Vector2(0, 0);

    public StateEstimator(Drivebase drivebase, Pigeon pigeon)
    {
        this.drivebase = drivebase;
        this.pigeon = pigeon;

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
        lastTimestamp = System.currentTimeMillis();
        thread.start();
    }

    public synchronized void reset()
    {
        position = new Vector2(0, 0);
    }

    protected synchronized void execute()
    {
        double leftPosition = drivebase.getLeftPosition();
        double rightPosition = drivebase.getRightPosition();

        double leftVelocity = drivebase.getLeftVelocity();
        double rightVelocity = drivebase.getRightVelocity();

        double angle = pigeon.getHeading() * Math.PI / 180.0;

        if(true)
        {
            double displacement = ((leftPosition - lastLeftPosition) + (rightPosition - lastRightPosition)) / 2;
            position.add(Vector2.scale(new Vector2(Math.sin(angle), Math.cos(angle)), displacement));
        }
        else
        {
            Vector2 direction = new Vector2(Math.sin(angle), Math.cos(angle));

            double radius = (Constants.Estimator.TRACK_WIDTH * (leftVelocity + rightVelocity)) / (2 * (leftVelocity - rightVelocity));
            Vector2 center = Vector2.scale(new Vector2(-direction.getY(), direction.getX()), radius);

            double omega = Constants.Estimator.TRACK_WIDTH / (leftVelocity - rightVelocity);
            double dtheta = omega * ((System.currentTimeMillis() - lastTimestamp) / 1000.0);

            position.twist(center, dtheta);
        }

        lastLeftPosition = leftPosition;
        lastRightPosition = rightPosition;
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
