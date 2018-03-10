package frc.team1983.commands.drivebase;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

@JsonDeserialize(as = DriveFeet.class)
public class DriveFeet extends DriveProfile
{
    @JsonCreator
    public DriveFeet(@JacksonInject @JsonProperty("drivebase") Drivebase drivebase, @JsonProperty("distance") double distance,
                     @JsonProperty("time") double time, @JsonProperty("action") ActionsEnum action)
    {
        super(drivebase, generateProfile(distance, time), generateProfile(distance, time), time, 0, action);
    }

    public DriveFeet(Drivebase drivebase, double distance, double time)
    {
        this(drivebase, distance, time, ActionsEnum.NONE);
    }

    private static CruiseProfile generateProfile(double distance, double time)
    {
        return new TrapezoidalProfile(Drivebase.getTicks(distance), time);
    }
}
