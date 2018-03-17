package frc.team1983.services.automanager;

import frc.team1983.commands.CommandBase;
import frc.team1983.commands.autonomous.AutoDoubleCubeAutoPicker;
import frc.team1983.commands.autonomous.AutoMotionProfilePicker;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;
import frc.team1983.services.logger.LoggerFactory;
import org.apache.logging.log4j.core.Logger;

public enum AutoSelection
{


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

