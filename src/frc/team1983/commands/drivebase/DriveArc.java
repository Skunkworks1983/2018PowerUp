package frc.team1983.commands.drivebase;

//import com.fasterxml.jackson.annotation.JacksonInject;
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.settings.Constants;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.utility.motion.profiles.CruiseProfile;
import frc.team1983.utility.motion.profiles.TrapezoidalProfile;

public class DriveArc extends DriveProfile
{
    public double radius, angle;

    public DriveArc(Drivebase drivebase, double radius, double angle, double time, ActionsEnum[] actions)
    {
        super(drivebase, generateLeftProfile(radius, angle, time), generateRightProfile(radius, angle, time), time, angle, actions);
        this.radius = radius;
        this.angle = angle;

        setHeadingLoopGains(Constants.PidConstants.Drivebase.AUX_ARC);
    }

    public DriveArc(Drivebase drivebase, double radius, double angle, double time)
    {
        this(drivebase, radius, angle, time, new ActionsEnum[]{ActionsEnum.NONE});
    }

    public static CruiseProfile generateLeftProfile(double radius, double angle, double time)
    {
        double leftCircumference = 2 * (radius + Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double leftDistance = (angle / 360) * leftCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(leftDistance), time, 0.8);
    }

    public static CruiseProfile generateRightProfile(double radius, double angle, double time)
    {
        double rightCircumference = 2 * (radius - Constants.AutoValues.WHEELBASE_RADIUS) * Math.PI;
        double rightDistance = (angle / 360) * rightCircumference;
        return new TrapezoidalProfile(Drivebase.getTicks(rightDistance), time, 0.8);
    }
}
