package frc.team1983.services;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * The GameDataPoller class provides an easy interface to the 2018 Match Data
 * sent by the FMS and DS to indicate the configuration of the SWITCH
 * and SCALE.
 *
 * @author Jaci Brunning
 * <p>
 * Stolen from Open-RIO, with modifications to fit into Skunkworks robot scheme
 */
public class GameDataPoller
{
    private static String gsm;
    private static boolean gameDataAlreadyPolled;

    /**
     * Game features including the SWITCH and SCALE. Near denotes close
     * to the alliance wall and FAR denotes furthest from the alliance
     * wall
     */
    public static enum GameFeature
    {
        SWITCH_NEAR, SCALE, SWITCH_FAR
    }

    /**
     * OwnedSide defines which side (from the perspective of the alliance
     * station) is owned by the alliance during the match. This is one of
     * LEFT, RIGHT or UNKNOWN (the latter in the case where game data
     * is not yet made available by the FMS or DS)
     */
    public static enum OwnedSide
    {
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
    public static OwnedSide getOwnedSide(GameFeature feature)
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

    /**
     *
     */
    public static void resetGameData()
    {
        gsm = "";
        gameDataAlreadyPolled = false;
    }

    public static void pollGameData()
    {
            gsm = DriverStation.getInstance().getGameSpecificMessage();
            gameDataAlreadyPolled = true;
    }
}