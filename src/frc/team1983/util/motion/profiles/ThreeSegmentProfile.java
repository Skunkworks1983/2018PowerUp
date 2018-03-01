package frc.team1983.util.motion.profiles;

import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.MotionProfilePoint;
import frc.team1983.util.motion.MotionSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
three segment profiles are profiles that have three segments, with
the constraint that the second (middle) segment must be parallel to the
x-axis
 */
public class ThreeSegmentProfile extends MotionProfile
{
    protected double accelTime1;
    protected double accelTime2;

    protected double initialVelocity;
    protected double finalVelocity;

    protected double v_cruise;

    public ThreeSegmentProfile(double distance, double duration, double accelTime1, double accelTime2, double initialVelocity, double finalVelocity)
    {
        super(generate(distance, duration, accelTime1, accelTime2, initialVelocity, finalVelocity));

        this.distance = distance;
        this.duration = duration;

        this.accelTime1 = accelTime1;
        this.accelTime2 = accelTime2;

        this.initialVelocity = initialVelocity;
        this.finalVelocity = finalVelocity;

        this.v_cruise = calculateCruiseVelocity(distance, duration, accelTime1, accelTime2, initialVelocity, finalVelocity);
    }

    protected static List<MotionSegment> generate(double distance, double duration, double accelTime1, double accelTime2, double initialVelocity, double finalVelocity)
    {
        // constrains
        if(accelTime1 + accelTime2 <= duration && accelTime1 != 0 && accelTime2 != 0)
        {
            double v_cruise = ((2.0 * distance) - (accelTime1 * initialVelocity) - (accelTime2 * finalVelocity)) / ((2 * duration) - accelTime1 - accelTime2);

            List<MotionSegment> segments = new ArrayList<>(Arrays.asList(
                new MotionSegment(new MotionProfilePoint(0, initialVelocity), new MotionProfilePoint(accelTime1, v_cruise)),
                new MotionSegment(new MotionProfilePoint(accelTime1, v_cruise), new MotionProfilePoint(duration - accelTime2, v_cruise)),
                new MotionSegment(new MotionProfilePoint(duration - accelTime2, v_cruise), new MotionProfilePoint(duration, finalVelocity))
                                                                        ));

            return segments;
        }
        else
        {
            throw new IllegalArgumentException("motion profile does not exist with given time constraints");
        }
    }

    protected static double calculateCruiseVelocity(double distance, double duration, double accelTime1, double accelTime2, double initialVelocity, double finalVelocity)
    {
        return ((2 * distance) - (accelTime1 * initialVelocity) - (accelTime2 * finalVelocity)) / ((2 * duration) - accelTime1 - accelTime2);
    }

    public double getAccelerationTime1()
    {
        return accelTime1;
    }

    public double getAccelerationTime2()
    {
        return accelTime2;
    }

    public double getInitialVelocity()
    {
        return initialVelocity;
    }

    public double getFinalVelocity()
    {
        return finalVelocity;
    }

    public double getCruiseVelocity()
    {
        return calculateCruiseVelocity(distance, duration, accelTime1, accelTime2, initialVelocity, finalVelocity);
    }
}
