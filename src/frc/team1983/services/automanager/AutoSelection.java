package frc.team1983.services.automanager;

import frc.team1983.commands.autonomous.pickers.CompliantScalePicker;
import frc.team1983.commands.autonomous.pickers.NoncompliantScalePicker;
import frc.team1983.commands.autonomous.pickers.SwitchPicker;

public enum AutoSelection
{
    COMPLIANT_SCALE_PICKER("COMPLIANT SCALE PICKER", CompliantScalePicker::new),
    NON_COMPLIANT_SCALE_PICKER("NON COMPLIANT SCALE PICKER", NoncompliantScalePicker::new),
    SWITCH_PICKER("SWITCH PICKER", SwitchPicker::new);

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
