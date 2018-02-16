package frc.team1983.util.path;

public class PathSegment extends PathComponent
{
    private PathPoint start, end;

    public PathSegment(PathPoint start, PathPoint end)
    {
        this.start = start;
        this.end = end;
    }


    public PathPoint getStart()
    {
        return start;
    }

    public PathPoint getEnd()
    {
        return start;
    }
}
