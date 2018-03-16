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
    EXCHANGE_ZONE("Exchange Zone", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInExchangeZone(drivebase, statefulDashboard);
    }),
    SCALE("Scale", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInScale(drivebase, statefulDashboard);
    }),
    SWITCH("Switch", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new PlaceCubeInSwitch(drivebase, statefulDashboard, elevator, collector);
    }),
    AUTO_SMELLY("AutoSelection SmellyDrive", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new AutoMotionProfilePicker(autoManager);
    }),
    AUTO_DOUBLE_CUBE("AutoSelection DoubleCubeAuto", (drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new AutoDoubleCubeAutoPicker(autoManager);
    }),
    TEST("Automanger testing", ((drivebase, collector, elevator, dashboardWrapper, statefulDashboard, autoManager) -> {
        return new CommandBase()
        {
            Logger logger;
            @Override
            public void initialize()
            {
                logger = LoggerFactory.createNewLogger(this.getClass());
                logger.info("Automanager testing initialized");
            }

            @Override
            public void execute()
            {
                logger.info("Automanger testing execute");
            }

            @Override
            public boolean isFinished()
            {
                return false;
            }

            @Override
            public void end()
            {
                logger.info("Automanager testing end");
            }

            @Override
            public void interrupted()
            {

            }
        };
    }));

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

