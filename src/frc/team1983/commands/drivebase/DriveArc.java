package frc.team1983.commands.drivebase;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.util.motion.profiles.CruiseProfile;
import frc.team1983.util.motion.profiles.TrapezoidalProfile;

@JsonDeserialize(as = DriveArc.class)
public class DriveArc extends DriveProfile
{
    @JsonCreator
    public DriveArc(@JacksonInject @JsonProperty("drivebase") Drivebase drivebase,
                    @JsonProperty("radius") double radius, @JsonProperty("angle") double angle,
                    @JsonProperty("time") double time, @JsonProperty("action") ActionsEnum[] actions)
    {
        super(drivebase, generateLeftProfile(radius, angle, time), generateRightProfile(radius, angle, time), time, angle, actions);
    }

    public DriveArc(Drivebase drivebase, double radius, double angle, double time)
    {
        this(drivebase, radius, angle, time, new ActionsEnum[]{ActionsEnum.NONE});
    }

    private static CruiseProfile generateLeftProfile(double radius, double angle, double time)
    {
        double leftCircumference = 2 * (radius + Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double leftDistance = (angle / 360) * leftCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(leftDistance), time);
    }

    private static CruiseProfile generateRightProfile(double radius, double angle, double time)
    {
        double rightCircumference = 2 * (radius - Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double rightDistance = (angle / 360) * rightCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(rightDistance), time);
    }
}
