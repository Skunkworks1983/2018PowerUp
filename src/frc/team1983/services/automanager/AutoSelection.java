package frc.team1983.services.automanager;

import frc.team1983.commands.autonomous.AutoDoubleCubeAutoPicker;
import frc.team1983.commands.autonomous.AutoMotionProfilePicker;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;

public enum AutoSelection
{
    EXCHANGE_ZONE("Exchange Zone", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInExchangeZone(drivebase, statefulDashboard);
    }),
    SCALE("Scale", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInScale(drivebase, statefulDashboard, autoManager);
    }),
    SWITCH("Switch", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInSwitch(drivebase, statefulDashboard, autoManager);
    }),
    AUTO_SMELLY("AutoSelection SmellyDrive", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new AutoMotionProfilePicker(autoManager);
    }),
    AUTO_DOUBLE_CUBE("AutoSelection DoubleCubeAuto", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new AutoDoubleCubeAutoPicker(autoManager);
    });

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

