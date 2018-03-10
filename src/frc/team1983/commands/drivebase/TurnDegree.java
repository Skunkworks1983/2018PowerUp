package frc.team1983.commands.drivebase;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.team1983.commands.autonomous.actions.ActionsEnum;
import frc.team1983.subsystems.Drivebase;

@JsonDeserialize(as = TurnDegree.class)
public class TurnDegree extends DriveArc
{
    @JsonCreator
    public TurnDegree(@JacksonInject @JsonProperty("drivebase") Drivebase drivebase,
                      @JsonProperty("angle") double angle, @JsonProperty("time") double time, @JsonProperty("action") ActionsEnum action)
    {
        super(drivebase, 0, angle, time, action);
    }

    public TurnDegree(Drivebase drivebase, double angle, double time)
    {
        this(drivebase, angle, time, ActionsEnum.NONE);
    }
}
