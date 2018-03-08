package frc.team1983.services.automanager;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.subsystems.Collector;
import frc.team1983.subsystems.Drivebase;
import frc.team1983.subsystems.Elevator;

public enum AutoSelection
{
    EXCHANGE_ZONE("Exchange Zone", (drivebase, collector, elevator, dashboardWrapper, dashboard) -> {
        return new PlaceCubeInExchangeZone(drivebase, dashboard);
    }),
    SCALE("Scale", (drivebase, collector, elevator, dashboardWrapper, dashboard) -> {
        return new PlaceCubeInScale(drivebase, dashboard);
    }),
    SWITCH("Switch", (drivebase, collector, elevator, dashboardWrapper, dashboard) -> {
        return new PlaceCubeInSwitch(drivebase, dashboard);
    }),
    AUTO_SMELLY("AutoSelection SmellyDrive", null);

    private String readableName;
    private SelectableAuto selectableAuto;

    AutoSelection(String readableName, SelectableAuto selectableAuto)
    {
        this.readableName = readableName;
        this.selectableAuto = selectableAuto;
    }

    public String getReadableName()
    {
        return readableName;
    }

    public SelectableAuto getSelectableAuto()
    {
        return selectableAuto;
    }
}

interface SelectableAuto
{
    Command createCommand(Drivebase drivebase, Collector collector, Elevator elevator,
                          DashboardWrapper dashboardWrapper, StatefulDashboard dashboard);
}