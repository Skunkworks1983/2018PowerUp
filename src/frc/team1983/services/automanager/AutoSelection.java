package frc.team1983.services.automanager;

import frc.team1983.commands.autonomous.OneCubePicker;
import frc.team1983.commands.autonomous.ProfiledAutoPicker;
import frc.team1983.commands.autonomous.BackupAutoPicker;

public enum AutoSelection
{
    /*
    MP_SWITCH_FAR_SCALE_FAR("Scale Far Switch Far", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new SwitchFarScaleFar(drivebase);
    }),

    MP_SWITCH_CLOSE_SCALE_CLOSE("Scale Close Switch Close", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new SwitchCloseScaleClose(drivebase);
    }),

    MP_MID_SWITCH_LEFT("Start Middle Switch Left", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new MidSwitchLeft(drivebase);
    }),

    MP_MID_SWITCH_RIGHT("Start Middle Switch Right", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new MidSwitchRight(drivebase);
    }),

    MP_LEFT_SCALE_LEFT("Start Left Scale Left", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new LeftScaleLeft(drivebase);
    }),

    MP_LEFT_SCALE_RIGHT("Start Left Scale Right", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new LeftScaleRight(drivebase);
    }),
    */
    /*
    EXCHANGE_ZONE("Exchange Zone", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInExchangeZone(drivebase, statefulDashboard);
    }),
    SCALE("Scale", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInScale(drivebase, statefulDashboard);
    }),
    SWITCH("Switch", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInSwitch(drivebase, statefulDashboard, elevator, collector);
    }),
    */

    //Two cube selection (2 cube switch/scale), motionprofiled
    MP_TWOCUBE_AUTO_PICKER("GAMEDATA TWO CUBE MP AUTO PICKER", ProfiledAutoPicker::new),

    //One cube selection (1 cube switch/scale), motionprofiled
    MP_ONECUBE_AUTO_PICKER("GAMEDATA ONE CUBE MP AUTO PICKER", OneCubePicker::new),

    //Backup selection (1 cube switch, cross baseline), dead reckoned
    BACKUP_AUTO_PICKER("GAMEDATA BACKUP AUTO PICKER", BackupAutoPicker::new);

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
