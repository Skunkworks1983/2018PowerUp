package frc.team1983.services.parser;

import frc.team1983.commands.drivebase.DriveArc;
import frc.team1983.commands.drivebase.DriveFeet;
import frc.team1983.commands.drivebase.DriveProfile;

public enum PathComponentType
{
    tanarc (DriveArc.class),
    tanline (DriveFeet.class);

    private final Class<? extends DriveProfile> type; // DriveArc.class, DriveFeet.class

    PathComponentType(Class<? extends DriveProfile> type)
    {
        this.type = type;
    }

    public Class<? extends DriveProfile> getType()
    {
        return type;
    }
}
