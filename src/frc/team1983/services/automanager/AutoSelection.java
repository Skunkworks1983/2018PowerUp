package frc.team1983.services.automanager;

import frc.team1983.commands.autonomous.AutoPicker;
import frc.team1983.commands.autonomous.BackupAutoPicker;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;
import frc.team1983.commands.autonomous.profiled.MidLeft;
import frc.team1983.commands.autonomous.profiled.MidRight;
import frc.team1983.commands.autonomous.profiled.SwitchCloseScaleClose;
import frc.team1983.commands.autonomous.profiled.SwitchFarScaleFar;

public enum AutoSelection
{

    MP_SWITCH_FAR_SCALE_FAR("Scale Far Switch Far", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new SwitchFarScaleFar(drivebase);
    }),

    MP_SWITCH_CLOSE_SCALE_CLOSE("Scale Close Switch Close", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new SwitchCloseScaleClose(drivebase);
    }),

    MP_MID_SWITCH_LEFT("Start Middle Switch Left", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new MidLeft(drivebase);
    }),

    MP_MID_SWITCH_RIGHT("Start Middle Switch Right", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new MidRight(drivebase);
    }),
    /*
    EXCHANGE_ZONE("Exchange Zone", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInExchangeZone(drivebase, statefulDashboard);
    }),
    SCALE("Scale", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInScale(drivebase, statefulDashboard);
    }),
    SWITCH("Switch", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInSwitch(drivebase, statefulDashboard);
    }),
    */

    MP_AUTO_PICKER("GAMEDATA MP AUTO PICKER", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new AutoPicker(autoManager);
    }),

    BACKUP_AUTO_PICKER("GAMEDATA BACKUP AUTO PICKER", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new BackupAutoPicker(autoManager);
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
