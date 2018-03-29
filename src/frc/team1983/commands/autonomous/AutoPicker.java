package frc.team1983.commands.autonomous;

import frc.team1983.util.path.Path;

public class AutoPicker
{
    // naming conventions are robot side, switch side, scale side (all relative)
    public Path SideNearNear,
                SideNearFar,
                SideFarNear,
                SideFarFar;
    public Path Middle;

    public AutoPicker(Path SideNearNear, Path SideNearFar, Path SideFarNear, Path SideFarFar, Path Middle)
    {
        this.SideNearNear = SideNearNear;
        this.SideNearFar = SideNearFar;
        this.SideFarNear = SideFarNear;
        this.SideFarFar = SideFarFar;
        this.Middle = Middle;
    }

    public AutoPicker()
    {
        this(new Path(), new Path(), new Path(), new Path(), new Path());
    }
}
