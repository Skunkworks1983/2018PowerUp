package frc.team1983.utility.control;

import frc.team1983.utility.motion.MotionProfile;

public class Profiler
{
    private Motor parent;

    public Profiler(Motor parent)
    {
        this.parent = parent;
    }

    public void stream(MotionProfile profile)
    {
        parent.clearMotionProfileTrajectories();
    }
}