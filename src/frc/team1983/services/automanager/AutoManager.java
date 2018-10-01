package frc.team1983.services.automanager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.Robot;
import frc.team1983.commands.autonomous.profiled.onecubers.OneLeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.onecubers.OneLeftScaleRight;
import frc.team1983.commands.autonomous.profiled.onecubers.OneMidSwitchLeft;
import frc.team1983.commands.autonomous.profiled.onecubers.OneMidSwitchRight;
import frc.team1983.commands.autonomous.profiled.onecubers.OneRightScaleLeft;
import frc.team1983.commands.autonomous.profiled.onecubers.OneRightScaleRight;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoLeftScaleLeft;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoLeftScaleRight;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoMidSwitchLeft;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoMidSwitchRight;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoRightScaleLeft;
import frc.team1983.commands.autonomous.profiled.twocubers.TwoRightScaleRight;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.util.path.Path;
import org.apache.logging.log4j.core.Logger;

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
    private SendableChooser<OwnedSide> ownedSideOverride, robotPositionSelector;
    private Logger logger;
    private DashboardWrapper dashboard;

    public Path onecube_ml, onecube_mr, onecube_rr, onecube_rl, onecube_ll, onecube_lr;
    public Path twocube_ml, twocube_mr, twocube_rr, twocube_rl, twocube_ll, twocube_lr;

    public AutoManager(DashboardWrapper dashboard)
    {
        this.dashboard = dashboard;
        gameDataAlreadyPolled = false;
        logger = LoggerFactory.createNewLogger(this.getClass());

        AutoSelection defaultSelection = AutoSelection.MP_ONECUBE_AUTO_PICKER;
        dashboard.addAutoChooserAutoDefault(defaultSelection);

        for(AutoSelection selection : AutoSelection.values())
        {
            if(selection != defaultSelection)
            {
                dashboard.addAutoChooserAutoChoice(selection);
            }
        }

        ownedSideOverride = new SendableChooser<>();
        ownedSideOverride.addDefault("Use game data", OwnedSide.UNKNOWN);
        /*ownedSideOverride.addObject("Left", OwnedSide.LEFT);
        ownedSideOverride.addObject("Right", OwnedSide.RIGHT);*/
        SmartDashboard.putData("Owned Side Override", ownedSideOverride); //TODO implement in dashboard wrapper

        robotPositionSelector = new SendableChooser<>();
        robotPositionSelector.addDefault("Middle", OwnedSide.UNKNOWN);
        robotPositionSelector.addObject("Right", OwnedSide.RIGHT);
        robotPositionSelector.addObject("Left", OwnedSide.LEFT);
        SmartDashboard.putData("Robot Position Selector", robotPositionSelector);
    }

    // barf
    public void generatePaths()
    {
        onecube_ml = new OneMidSwitchLeft(Robot.getInstance().getDrivebase());
        onecube_mr = new OneMidSwitchRight(Robot.getInstance().getDrivebase());
        onecube_rr = new OneRightScaleRight(Robot.getInstance().getDrivebase());
        onecube_rl = new OneRightScaleLeft(Robot.getInstance().getDrivebase());
        onecube_ll = new OneLeftScaleLeft(Robot.getInstance().getDrivebase());
        onecube_lr = new OneLeftScaleRight(Robot.getInstance().getDrivebase());

        twocube_ml = new TwoMidSwitchLeft(Robot.getInstance().getDrivebase());
        twocube_mr = new TwoMidSwitchRight(Robot.getInstance().getDrivebase());
        twocube_rr = new TwoRightScaleRight(Robot.getInstance().getDrivebase());
        twocube_rl = new TwoRightScaleLeft(Robot.getInstance().getDrivebase());
        twocube_ll = new TwoLeftScaleLeft(Robot.getInstance().getDrivebase());
        twocube_lr = new TwoLeftScaleRight(Robot.getInstance().getDrivebase());
    }

    public enum GameFeature
    {
        SWITCH_NEAR, SCALE, SWITCH_FAR //Near is our alliances switch
    }

    public enum OwnedSide
    {
        //Which side is our alliance's color.
        //Unknown indicates the fms has not given us game data, or used as a placeholder for middle
        UNKNOWN, LEFT, RIGHT
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
        switch(ownedSideOverride.getSelected())
        {
            case UNKNOWN:
                break;
            case LEFT:
                return OwnedSide.LEFT;
            case RIGHT:
                return OwnedSide.RIGHT;
        }

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

    public OwnedSide getRobotPosition()
    {
        return robotPositionSelector.getSelected();
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
            if(gsm.length() == 3 || ownedSideOverride.getSelected() != OwnedSide.UNKNOWN)
            {
                gameDataAlreadyPolled = true;

                Scheduler.getInstance().add(dashboard.getSelectedAutoChoice().getSelectableAuto().createCommand(
                        Robot.getInstance().getDrivebase(), Robot.getInstance().getCollector(),
                        Robot.getInstance().getElevator(), Robot.getInstance().getDashboardWrapper(),
                        Robot.getInstance().getStatefulDashboard(), this));
            }
        }
    }
}