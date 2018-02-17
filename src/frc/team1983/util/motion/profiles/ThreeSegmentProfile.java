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

    protected double v_start;
    protected double v_end;

    // constrained
    public ThreeSegmentProfile(double distance, double t_total, double vel_max, double acc_max, double t_a1, double t_a2, double v_start, double v_end)
    {
        super(generate(distance, t_total, vel_max, acc_max, t_a1, t_a2, v_start, v_end));

        super.distance = distance;
        super.t_total = t_total;

        super.vel_max = vel_max;
        super.acc_max = acc_max;

        this.t_a1 = t_a1;
        this.t_a2 = t_a2;

        this.v_start = v_start;
        this.v_end = v_end;
    }

    private static List<MotionSegment> generate(double distance, double t_total, double vel_max, double acc_max, double t_a1, double t_a2, double v_start, double v_end)
    {
        double v_cruise = (distance - (v_start * t_a1) - (v_end * t_a2)) / (t_total - t_a1 - t_a2);

        double acc_1 = (v_cruise - v_start) / (t_a1);
        double acc_2 = (v_end - v_cruise) / (t_a2);

        boolean exists = Math.abs(v_cruise) <= vel_max && Math.abs(acc_1) <= acc_max && Math.abs(acc_2) <= acc_max;

        if(exists)
        {
            ArrayList<MotionSegment> segments = new ArrayList<>(Arrays.asList(
                    new MotionSegment(new MotionProfilePoint(0, v_start), new MotionProfilePoint(t_a1, v_cruise)),
                    new MotionSegment(new MotionProfilePoint(t_a1, v_cruise), new MotionProfilePoint(t_total - t_a2, v_cruise)),
                    new MotionSegment(new MotionProfilePoint(t_total - t_a2, v_cruise), new MotionProfilePoint(t_total, v_end))
                                                                             ));

            return segments;
        }
        else
        {
            throw new IllegalArgumentException("motion profile does not exist with current constrains");
        }
    }

    // unconstrained
    public ThreeSegmentProfile(double distance, double t_total, double t_a1, double t_a2, double v_start, double v_end)
    {
        super(generate(distance, t_total, t_a1, t_a2, v_start, v_end));

        super.distance = distance;
        super.t_total = t_total;

        this.t_a1 = t_a1;
        this.t_a2 = t_a2;

        this.v_start = v_start;
        this.v_end = v_end;
    }

    private static List<MotionSegment> generate(double distance, double t_total, double t_a1, double t_a2, double v_start, double v_end)
    {
        double v_cruise = (distance - (v_start * t_a1) - (v_end * t_a2)) / (t_total - t_a1 - t_a2);

        ArrayList<MotionSegment> segments = new ArrayList<>(Arrays.asList(
                new MotionSegment(new MotionProfilePoint(0, v_start), new MotionProfilePoint(t_a1, v_cruise)),
                new MotionSegment(new MotionProfilePoint(t_a1, v_cruise), new MotionProfilePoint(t_total - t_a2, v_cruise)),
                new MotionSegment(new MotionProfilePoint(t_total - t_a2, v_cruise), new MotionProfilePoint(t_total, v_end))
                                                                         ));

        return segments;
    }
}
