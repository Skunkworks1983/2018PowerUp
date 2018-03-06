package frc.team1983.commands.drivebase;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

@JsonDeserialize(as = DriveFeet.class)
public class DriveFeet extends DriveProfile
{
    @JsonCreator
    public DriveFeet(@JacksonInject Drivebase drivebase, @JsonProperty("distance") double distance,
                     @JsonProperty("time") double time)
    {
        super(drivebase, generateProfile(distance, time), generateProfile(distance, time));
    }

    private static CruiseProfile generateProfile(double distance, double time)
    {
        CruiseProfile profile = new TrapezoidalProfile(Drivebase.getTicks(distance), time);
        return profile;
    }
}
