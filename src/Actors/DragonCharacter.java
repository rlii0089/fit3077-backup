package Actors;

import Game.Location;
import Gameboard.Cave;

import java.util.ArrayList;

/**
 * An entity that acts as the token for player which starts in a cave and moves around the board
 * @author Max Zhuang
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

    private ArrayList<Location> locationSet;

    private int locationIndex;

    /**
     * Constructor for dragon character
     * @param name the name of the dragon character
     * @param displayChar the display character of the dragon token
     * @param playerNumber the number of the player which is is in charge of this token
     */
    public DragonCharacter(String name, char displayChar, int playerNumber) {
        super(name, displayChar);
        this.playerNumber = playerNumber;
    }

    /**
     * getter method for player number
     * @return the number of the player which is is in charge of this token
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * getter method for the starting cave
     * @return The cave that the dragon character will start in when the game first starts
     */
    public Cave getStartingCave() {
        return startingCave;
    }

    /**
     * setter of the starting cave
     * @param startingCave The cave that the dragon character will start in when the game first starts
     */
    public void setStartingCave(Cave startingCave) {
        this.startingCave = startingCave;
    }


    public ArrayList<Location> getLocationSet() {
        return locationSet;
    }

    public void setLocationSet(ArrayList<Location> locationSet) {
        this.locationSet = locationSet;
    }

    public int getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(int locationIndex) {
        this.locationIndex = locationIndex;
    }
}
