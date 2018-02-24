package frc.team1983.util.motion.profiles;

import frc.team1983.util.motion.MotionProfile;
import frc.team1983.util.motion.MotionProfilePoint;
import frc.team1983.util.motion.MotionSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSegmentProfile extends MotionProfile
{
    protected double t_acc1;
    protected double t_acc2;

    protected double vel_i;
    protected double vel_f;

    protected double v_cruise;

    public ThreeSegmentProfile(double distance, double duration, double t_acc1, double t_a2, double v_i, double v_f)
    {
        super(generate(distance, duration, t_acc1, t_a2, v_i, v_f));

        this.distance = distance;
        this.duration = duration;

        this.t_acc1 = t_acc1;
        this.t_acc2 = t_a2;

        this.vel_i = v_i;
        this.vel_f = v_f;
    }

    protected static List<MotionSegment> generate(double distance, double duration, double t_a1, double t_a2, double v_i, double v_f)
    {
        // constrains
        if(t_a1 + t_a2 <= duration && t_a1 != 0 && t_a2 != 0)
        {
            double v_cruise = ((2 * distance) - (t_a1 * v_i) - (t_a2 * v_f)) / ((2 * duration) - t_a1 - t_a2);

            List<MotionSegment> segments = new ArrayList<>(Arrays.asList(
                new MotionSegment(new MotionProfilePoint(0, v_i), new MotionProfilePoint(t_a1, v_cruise)),
                new MotionSegment(new MotionProfilePoint(t_a1, v_cruise), new MotionProfilePoint(duration - t_a2, v_cruise)),
                new MotionSegment(new MotionProfilePoint(duration - t_a2, v_cruise), new MotionProfilePoint(duration, v_f))
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
        return t_acc1;
    }

    public double getAccelerationTime2()
    {
        return t_acc2;
    }

    public double getInitialVelocity()
    {
        return vel_i;
    }

    public double getFinalVelocity()
    {
        return vel_f;
    }

    public double getCruiseVelocity()
    {
        return ((2 * distance) - (t_acc1 * vel_i) - (t_acc2 * vel_f)) / ((2 * duration) - t_acc1 - t_acc2);
    }
}
