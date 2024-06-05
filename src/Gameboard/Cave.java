package Gameboard;

import Game.Location;
import Game.Printable;

import java.io.Serializable;

/**
 * A class that represents the cave object in the game fiery dragon spread across the board
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */
public class Cave implements Printable, Serializable {

    /**
     * Location of the tile directly outside the cave
     */
    private Location caveEntrance;

    /**
     * constructor for cave
     */
    public Cave() {
    }

    /**
     * getter for cave entrance
     *
     * @return Location of the tile directly outside the cave
     */
    public Location getCaveEntrance() {
        return caveEntrance;
    }

    /**
     * setter for cave entrance
     *
     * @param caveEntrance Location of the tile directly outside the cave
     */
    public void setCaveEntrance(Location caveEntrance) {
        this.caveEntrance = caveEntrance;
    }

    /**
     * gets the character that represents cave
     *
     * @return the character that represents cave
     * @return
     */
    @Override
    public char getDisplayChar() {
        return 'Ω';
    }
}
