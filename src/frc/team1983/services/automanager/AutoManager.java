package frc.team1983.services.automanager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1983.Robot;
import frc.team1983.commands.autonomous.routines.left.LeftCross;
import frc.team1983.commands.autonomous.routines.left.MultiLeftScaleLeft;
import frc.team1983.commands.autonomous.routines.left.MultiLeftScaleRight;
import frc.team1983.commands.autonomous.routines.left.OneLeftScaleLeft;
import frc.team1983.commands.autonomous.routines.left.OneLeftSwitchLeft;
import frc.team1983.commands.autonomous.routines.left.TwoLeftSwitchLeft;
import frc.team1983.commands.autonomous.routines.middle.MultiMiddleSwitchLeft;
import frc.team1983.commands.autonomous.routines.middle.MultiMiddleSwitchRight;
import frc.team1983.commands.autonomous.routines.right.MultiRightScaleLeft;
import frc.team1983.commands.autonomous.routines.right.MultiRightScaleRight;
import frc.team1983.commands.autonomous.routines.right.OneRightScaleRight;
import frc.team1983.commands.autonomous.routines.right.OneRightSwitchRight;
import frc.team1983.commands.autonomous.routines.right.RightCross;
import frc.team1983.commands.autonomous.routines.right.TwoRightSwitchRight;
import frc.team1983.services.DashboardWrapper;
import frc.team1983.services.logger.LoggerFactory;
import frc.team1983.util.path.Path;
import org.apache.logging.log4j.core.Logger;

/**
 * The automanager class provides an easy interface to the 2018 Match Data
 * sent by the FMS and DS to indicate the configuration of the SWITCH
 * and SCALE.
 *
 * @author Jaci Brunning and Erik Pratt
 * Stolen from Open-RIO (Jaci Brunning), with modifications to fit into Skunkworks robot scheme
 */
public class AutoManager
{

    private String gsm;
    private boolean gameDataAlreadyPolled;
    private SendableChooser<OwnedSide> ownedSideOverride, robotPositionSelector;
    private Logger logger;
    private DashboardWrapper dashboard;

    public AutoManager(DashboardWrapper dashboard)
    {
        this.dashboard = dashboard;
        gameDataAlreadyPolled = false;
        logger = LoggerFactory.createNewLogger(this.getClass());

        AutoSelection defaultSelection = AutoSelection.NON_COMPLIANT_SCALE_PICKER;
        dashboard.addAutoChooserAutoDefault(defaultSelection);

        for(AutoSelection selection : AutoSelection.values())
        {
            if(selection != defaultSelection)
                dashboard.addAutoChooserAutoChoice(selection);
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

    // AUTOS
    // side
    public Path multiLeftScaleLeft, multiRightScaleRight;
    public Path multiLeftScaleRight, multiRightScaleLeft;
    public Path oneLeftScaleLeft, oneRightScaleRight;

    public Path oneLeftSwitchLeft, oneRightSwitchRight;
    public Path twoLeftSwitchLeft, twoRightSwitchRight;

    public Path leftCross, rightCross;

    // middle
    public Path multiMiddleSwitchLeft, multiMiddleSwitchRight;

    public void generatePaths()
    {
        multiLeftScaleLeft = new MultiLeftScaleLeft(Robot.getInstance().getDrivebase());
        multiRightScaleRight = new MultiRightScaleRight(Robot.getInstance().getDrivebase());

        multiLeftScaleRight = new MultiLeftScaleRight(Robot.getInstance().getDrivebase());
        multiRightScaleLeft = new MultiRightScaleLeft(Robot.getInstance().getDrivebase());

        oneLeftScaleLeft = new OneLeftScaleLeft(Robot.getInstance().getDrivebase());
        oneRightScaleRight = new OneRightScaleRight(Robot.getInstance().getDrivebase());


        oneLeftSwitchLeft = new OneLeftSwitchLeft(Robot.getInstance().getDrivebase());
        oneRightSwitchRight = new OneRightSwitchRight(Robot.getInstance().getDrivebase());

        twoLeftSwitchLeft = new TwoLeftSwitchLeft(Robot.getInstance().getDrivebase());
        twoRightSwitchRight = new TwoRightSwitchRight(Robot.getInstance().getDrivebase());


        leftCross = new LeftCross(Robot.getInstance().getDrivebase());
        rightCross = new RightCross(Robot.getInstance().getDrivebase());


        multiMiddleSwitchLeft = new MultiMiddleSwitchLeft(Robot.getInstance().getDrivebase());
        multiMiddleSwitchRight = new MultiMiddleSwitchRight(Robot.getInstance().getDrivebase());
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