package frc.team1983.util.motion;

import frc.team1983.settings.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/*
all motion profiles are currently trapezoidal velocity functions of time
profiles are generated given the assumption that half of the profile is
dedicated to acceleration. documentation to be written later :/

vel_max and acc_max are constants for each subsystem, to be determined
by math and testing or whatever

all units are in either ms, u/ms, or u/ms/ms

*/
public class MotionProfile
{
    private ArrayList<MotionSegment> segments;

    private int pointDuration = 100; // ms

    private double distance;
    private double totalTime;

    private double vel_max;
    private double acc_max;

    private double vel_c;
    private double t_a;
    private double acc;

    public MotionProfile(double distance, double totalTime, double vel_max, double acc_max) throws RuntimeException
    {
        double t_a = (totalTime * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2;
        double vel_c = distance / (totalTime - t_a);
        double acc = vel_c / t_a;

        // profile is undefined if we need to travel/accelerate faster than is physically possible
        if(Math.abs(vel_c) <= Math.abs(vel_max) && Math.abs(acc) <= Math.abs(acc_max))
        {
            this.distance = distance;
            this.totalTime = totalTime;

            this.vel_max = vel_max;
            this.acc_max = acc_max;

            this.t_a = t_a;
            this.vel_c = vel_c;
            this.acc = acc;

            // make segments (trapezoidal)
            segments = new ArrayList<>(Arrays.asList(
                new MotionSegment(new MotionProfilePoint(0, 0), new MotionProfilePoint(t_a, vel_c)),
                new MotionSegment(new MotionProfilePoint(t_a, vel_c), new MotionProfilePoint(totalTime - t_a, vel_c)),
                new MotionSegment(new MotionProfilePoint(totalTime - t_a, vel_c), new MotionProfilePoint(totalTime, 0))
            ));
        }
        else
        {
            throw new RuntimeException("Motion Profile undefined (vel_max: " + vel_max + ", vel_c: " + vel_c + ")");
        }
    }

    public MotionProfile(double distance, double totalTime)
    {
        double t_a = (totalTime * Constants.Motion.DEFAULT_MOTIONPROFILE_ACCEL_TIME) / 2;
        double vel_c = distance / (totalTime - t_a);
        double acc = vel_c / t_a;

        this.distance = distance;
        this.totalTime = totalTime;

        this.vel_max = vel_max;
        this.acc_max = acc_max;

        this.t_a = t_a;
        this.vel_c = vel_c;
        this.acc = acc;

        // make segments (trapezoidal)
        segments = new ArrayList<>(Arrays.asList(
                new MotionSegment(new MotionProfilePoint(0, 0), new MotionProfilePoint(t_a, vel_c)),
                new MotionSegment(new MotionProfilePoint(t_a, vel_c), new MotionProfilePoint(totalTime - t_a, vel_c)),
                new MotionSegment(new MotionProfilePoint(totalTime - t_a, vel_c), new MotionProfilePoint(totalTime, 0))
        ));
    }

    // evaluates the desired velocity of the profile at a time
    public double evaluateVelocity(double time) throws RuntimeException
    {
        if(0 <= time && time <= totalTime)
        {
            // can probably do some fancy math here to find which segment we need to access but i'll keep it simple
            for(MotionSegment segment : segments)
            {
                // segments overlap at one point so we can use bounds of domain
                if(segment.getStart().getTime() <= time && time <= segment.getEnd().getTime())
                    return segment.evaluate(time);
            }

            // guaranteed that code above returns a point but java sucks so we need this
            return 0;
        }
        else
        {
            throw new RuntimeException("Time " + time + " is not in the domain of motion profile");
        }
    }

    // evaluates the riemann sum up to a time
    public double evaluatePosition(double time) throws RuntimeException
    {
        if(0 <= time && time <= totalTime)
        {
            double A = 0;

            for(MotionSegment segment : segments)
            {
                double a = segment.getStart().getVelocity(), b = 0, dt = 0;

                if(segment.getEnd().getTime() <= time)
                {
                    b = segment.getEnd().getVelocity();
                    dt = segment.getEnd().getTime() - segment.getStart().getTime();
                }
                else if(segment.getStart().getTime() < time && time < segment.getEnd().getTime())
                {
                    b = segment.evaluate(time);
                    dt = time - segment.getStart().getTime();
                }

                A += ((a + b) / 2) * dt;
            }

            return A;
        }
        else
        {
            throw new RuntimeException("Time " + time + " is not in the domain of motion profile");
        }
    }

    public ArrayList<MotionSegment> getSegments()
    {
        return segments;
    }

    public double getTotalTime()
    {
        return totalTime;
    }

    public int getPointDuration()
    {
        return pointDuration;
    }

    public double getCruiseVelocity()
    {
        return vel_c;
    }
}
