package frc.team1983.util.path;

import java.util.List;

public class Path
{
    private List<PathComponent> components;

    public Path(List<PathComponent> components)
    {
        this.components = components;
    }

    public PathComponent getComponent(int index)
    {
        if(index < components.size())
        {
            return components.get(index);
        }
        else
        {
            throw new IllegalArgumentException("index " + index + " is out of bounds");
        }
    }

    public int getComponentCount()
    {
        return components.size();
    }

    public List<PathComponent> getPath()
    {
        return components;
    }
}
