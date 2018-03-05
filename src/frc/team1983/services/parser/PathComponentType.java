package frc.team1983.services.parser;

import frc.team1983.util.path.PathComponent;
import frc.team1983.util.path.PathTanarc;
import frc.team1983.util.path.PathTanline;

public enum PathComponentType
{
    tanarc (PathTanarc.class),
    tanline (PathTanline.class);

    private final Class<? extends PathComponent> type; // PathTanarc.class, PathTanline.class

    PathComponentType(Class<? extends PathComponent> type)
    {
        this.type = type;
    }

    public Class<? extends PathComponent> getType()
    {
        return type;
    }
}
