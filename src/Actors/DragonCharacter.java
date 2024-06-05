package Actors;

import Game.Location;
import Gameboard.Cave;

import java.util.ArrayList;

/**
 * An entity that acts as the token for player which starts in a cave and moves around the board
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */

public class DragonCharacter extends Actor {

    /**
     * The cave that the dragon character will start in when the game first starts
     */
    private Cave startingCave;
    /**
     * The number of the player in charge of this token
     */
    private int playerNumber;

    /**
     * The original set of locations the dragon walks upon as soon as it loads into the game
     */
    private ArrayList<Location> originalLocationSet;

    /**
     * The current active set of locations the dragon walks upon, this is different to the original set when the
     * dragon falls behind its starting cave
     */
    private ArrayList<Location> locationSet;

    /**
     * The index of the dragon's current location within its dragon set
     */
    private int locationIndex;

    /**
     * Constructor for dragon character
     *
     * @param name         the name of the dragon character
     * @param displayChar  the display character of the dragon token
     * @param playerNumber the number of the player which is is in charge of this token
     */
    public DragonCharacter(String name, char displayChar, int playerNumber) {
        super(name, displayChar);
        this.playerNumber = playerNumber;
    }

    /**
     * getter method for player number
     *
     * @return the number of the player which is in charge of this token
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * getter method for the starting cave
     *
     * @return The cave that the dragon character will start in when the game first starts
     */
    public Cave getStartingCave() {
        return startingCave;
    }

    /**
     * setter of the starting cave
     *
     * @param startingCave The cave that the dragon character will start in when the game first starts
     */
    public void setStartingCave(Cave startingCave) {
        this.startingCave = startingCave;
    }

    /**
     * getter for the original location set
     * @return dragon's original set
     */
    public ArrayList<Location> getOriginalLocationSet() {
        return originalLocationSet;
    }

    /**
     * setter for the original location set
     * @param originalLocationSet the original set of locations the dragon walks upon
     */
    public void setOriginalLocationSet(ArrayList<Location> originalLocationSet) {
        this.originalLocationSet = originalLocationSet;
    }

    /**
     * getter for the dragons currently active location set
     * @return the dragons currently active location set
     */
    public ArrayList<Location> getLocationSet() {
        return locationSet;
    }

    /**
     * Setter for the dragons currently active location set
     * @param locationSet A new set of locations which depict which locations the dragon walks upon
     */
    public void setLocationSet(ArrayList<Location> locationSet) {
        this.locationSet = locationSet;
    }

    /**
     * getter for the dragons current location index
     * @return the dragons current location index
     */
    public int getLocationIndex() {
        return locationIndex;
    }

    /**
     * setter for the dragons location index
     * @param locationIndex the index of the dragons current location within its location set
     */
    public void setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
    }
}
