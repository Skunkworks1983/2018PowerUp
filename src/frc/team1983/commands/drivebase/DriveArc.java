package frc.team1983.commands.drivebase;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;
import frc.team1983.util.path.PathTanarc;

@JsonDeserialize(as = DriveArc.class)
public class DriveArc extends DriveProfile
{
    private boolean isRight;

    @JsonCreator
    public DriveArc(@JacksonInject Drivebase drivebase, @JsonProperty("radius") double radius,
                    @JsonProperty("angle") double angle, @JsonProperty("time") double time,
                    @JsonProperty("isRight") boolean isRight)
    {
        super(drivebase, isRight ? generateLeftProfile(drivebase, radius, angle, time) : generateRightProfile(drivebase, radius, angle, time),
                         isRight ? generateRightProfile(drivebase, radius, angle, time) : generateLeftProfile(drivebase, radius, angle, time),
                         isRight ? angle : -angle);
        this.time = time;
        this.isRight = isRight;
    }

    public DriveArc(Drivebase drivebase, double radius, double angle, double time)
    {
        this(drivebase, radius, angle, time, true);
    }

    private static CruiseProfile generateLeftProfile(Drivebase drivebase, double radius, double angle, double time)
    {
        double leftCircumference = 2 * (radius + Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double leftDistance = (angle / 360) * leftCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(leftDistance), time);
    }

    private static CruiseProfile generateRightProfile(Drivebase drivebase, double radius, double angle, double time)
    {
        double rightCircumference = 2 * (radius - Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double rightDistance = (angle / 360) * rightCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(rightDistance), time);
    }
}
