package frc.team1983.services.automanager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.team1983.Robot;
import frc.team1983.commands.autonomous.PlaceCubeInExchangeZone;
import frc.team1983.commands.autonomous.PlaceCubeInScale;
import frc.team1983.commands.autonomous.PlaceCubeInSwitch;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.StatefulDashboard;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.subsystems.Drivebase;
import org.apache.logging.log4j.core.Logger;

import java.io.File;

/**
 * The automanager class provides an easy interface to the 2018 Match Data
 * sent by the FMS and DS to indicate the configuration of the SWITCH
 * and SCALE.
 *
 * @author Jaci Brunning
 * <p>
 * Stolen from Open-RIO, with modifications to fit into Skunkworks robot scheme
 */
public class AutoManager
{
    private String gsm;
    private boolean gameDataAlreadyPolled;
    private SendableChooser autonomousSelector, ownedSideOveride, robotPositionSelector;
    private Logger logger;

    public AutoManager(DashboardWrapper dashboard)
    {
        logger = LoggerFactory.createNewLogger(this.getClass());

        autonomousSelector = new SendableChooser();
        autonomousSelector.addDefault("Exchange Zone", PlaceCubeInExchangeZone.class);
        autonomousSelector.addObject("Scale", PlaceCubeInScale.class);
        autonomousSelector.addObject("Switch", PlaceCubeInSwitch.class);
        autonomousSelector.addObject("AutoEnum SmellyDrive", null);
        dashboard.putChooser("Autonomous Mode Selector", autonomousSelector);

        robotPositionSelector = new SendableChooser();
        robotPositionSelector.addDefault("Left", StartSide.LEFT);
        robotPositionSelector.addObject("Middle", StartSide.MIDDLE);
        robotPositionSelector.addObject("Right", StartSide.RIGHT);
        dashboard.putChooser("Robot Position Selector", robotPositionSelector);

        ownedSideOveride = new SendableChooser();
        ownedSideOveride.addDefault("Use game data", OwnedSide.UNKNOWN);
        ownedSideOveride.addObject("Left", OwnedSide.LEFT);
        ownedSideOveride.addObject("Right", OwnedSide.RIGHT);
        dashboard.putChooser("Owned Side Overide", ownedSideOveride);
    }

    public enum GameFeature
    {
        SWITCH_NEAR, SCALE, SWITCH_FAR //Near is our alliances switch
    }

    public enum OwnedSide
    {
        UNKNOWN, LEFT, RIGHT //Which side is our alliance's color. Unknown indicates the fms has not given us game data
    }

    public enum StartSide
    {
        LEFT, MIDDLE, RIGHT
    }

    /**
     * Determine the OwnedSide of any given GameFeature. Use this method to
     * determine which PLATE of each feature (SCALE or SWITCH) is OWNED by your
     * ALLIANCE. Perspectives are referenced relative to your ALLIANCE STATION.
     *
     * @param feature The feature to get the owned side for. See GameFeature.
     * @return The Owned Side (PLATE) of the feature. See OwnedSide. Make sure to
     * check for UNKNOWN.
     */
    public OwnedSide getOwnedSide(GameFeature feature)
    {
        if(gsm == null || gsm.equals(""))
        {
            return OwnedSide.UNKNOWN;
        }

        // If the length is not 3, it is not game data
        if(gsm.length() != 3)
        {
            return OwnedSide.UNKNOWN;
        }

        char gd;
        switch(feature)
        {
            case SWITCH_NEAR:
                gd = gsm.charAt(0);
                break;
            case SCALE:
                gd = gsm.charAt(1);
                break;
            case SWITCH_FAR:
                gd = gsm.charAt(2);
                break;
            default: //Should never happen, but it's better to be safe
                return OwnedSide.UNKNOWN;
        }
        switch(gd)
        {
            case 'L':
            case 'l':
                return OwnedSide.LEFT;
            case 'R':
            case 'r':
                return OwnedSide.RIGHT;
            default: //Again should never happen, but it's safe
                return OwnedSide.UNKNOWN;
        }
    }

    public void resetGameData()
    {
        gsm = "";
        gameDataAlreadyPolled = false;
    }

    public void execute()
    {
        if(!gameDataAlreadyPolled)
        {
            gsm = DriverStation.getInstance().getGameSpecificMessage();
            gameDataAlreadyPolled = true;

            Object selected = autonomousSelector.getSelected();
            if (selected instanceof Class)
            {
                try
                {
                    Scheduler.getInstance().add((CommandGroup)
                        ((Class) selected).getConstructor(Drivebase.class, StatefulDashboard.class)
                            .newInstance(Robot.getInstance().getDrivebase(), Robot.getInstance().getDashboard()));
                }
                catch(Exception e)
                {
                    logger.error("Exception when creating auto command", e);
                }
            }
            else
            {

            }
        }
    }

    public Class getSelectedIfClass(SendableChooser chooser)
    {

    }
}