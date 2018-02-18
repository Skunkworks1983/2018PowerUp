package frc.team1983.util.motion.profiles;

import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.MotionProfilePoint;
import frc.team1983.util.motion.MotionSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSegmentProfile extends MotionProfile
{
    protected double t_a1;
    protected double t_a2;

    protected double v_i;
    protected double v_f;

    protected double v_cruise;

    public ThreeSegmentProfile(double distance, double t_total, double t_a1, double t_a2, double v_i, double v_f)
    {
        super(generate(distance, t_total, t_a1, t_a2, v_i, v_f));

        this.distance = distance;
        this.t_total = t_total;

        this.t_a1 = t_a1;
        this.t_a2 = t_a2;

        this.v_i = v_i;
        this.v_f = v_f;
    }

    protected static List<MotionSegment> generate(double distance, double t_total, double t_a1, double t_a2, double v_i, double v_f)
    {
        // constrains
        if(t_a1 + t_a2 <= t_total && t_a1 != 0 && t_a2 != 0)
        {
            double v_cruise = ((2 * distance) - (t_a1 * v_i) - (t_a2 * v_f)) / ((2 * t_total) - t_a1 - t_a2);

            List<MotionSegment> segments = new ArrayList<>(Arrays.asList(
                new MotionSegment(new MotionProfilePoint(0, v_i), new MotionProfilePoint(t_a1, v_cruise)),
                new MotionSegment(new MotionProfilePoint(t_a1, v_cruise), new MotionProfilePoint(t_total - t_a2, v_cruise)),
                new MotionSegment(new MotionProfilePoint(t_total - t_a2, v_cruise), new MotionProfilePoint(t_total, v_f))
                                                                        ));

            return segments;
        }
        else
        {
            throw new IllegalArgumentException("motion profile does not exist with given time constraints");
        }
    }

    public double getAccelerationTime1()
    {
        return t_a1;
    }

    public double getAccelerationTime2()
    {
        return t_a2;
    }

    public double getInitialVelocity()
    {
        return v_i;
    }

    public double getFinalVelocity()
    {
        return v_f;
    }

    public double getCruiseVelocity()
    {
        return ((2 * distance) - (t_a1 * v_i) - (t_a2 * v_f)) / ((2 * t_total) - t_a1 - t_a2);
    }
}
