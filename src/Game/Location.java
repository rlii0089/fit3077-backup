package Game;

import Utilities.AnimalLocation;
import Utilities.CaveLocation;
import Utilities.DragonLocation;

import java.io.Serializable;

/**
 * A class representing a location on the game board, responsible for keeping track of character representation.
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */

public class Location implements Printable, Serializable {
    /**
     * the x coordinate of the location, leftmost element being 0 and increasing towards the right
     */
    private int x;

    /**
     * the y coordinate of the location, uppermost element being 0 and increasing as it goes down
     */
    private int y;

    /**
     * the character that should be displayed at this location during the current state of the game board
     */
    private char displayCharacter;

    private DragonLocation dragonlocation;

    private AnimalLocation animalLocation;

    private CaveLocation caveLocation;

    /**
     * Constructor for Location
     *
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     */
    public Location(int x, int y, DragonLocation dragonLocation, AnimalLocation animalLocation, CaveLocation caveLocation) {
        this.x = x;
        this.y = y;
        this.dragonlocation = dragonLocation;
        this.animalLocation = animalLocation;
        this.caveLocation = caveLocation;
    }

    /**
     * getter for x
     *
     * @return the x coordinate of the location
     */
    public int x() {
        return x;
    }

    /**
     * getter for y
     *
     * @return the y coordinate of the location
     */
    public int y() {
        return y;
    }

    /**
     * setter for display character
     *
     * @param displayCharacter character to display at this location
     */
    public void setDisplayCharacter(char displayCharacter) {
        this.displayCharacter = displayCharacter;
    }

    /**
     * getter for display character, the priority goes actor -> cave -> original map character
     *
     * @return character to display at this location
     */
    public char getDisplayChar() {
        return dragonPresent() ? dragonlocation.getActorAt(this).getDisplayChar() :
                animalPresent() ? animalLocation.getActorAt(this).getDisplayChar() :
                        cavePresent() ? caveLocation.getCave(this).getDisplayChar() :
                                displayCharacter;
    }

    /**
     * checks to see if an dragon token is at this location
     *
     * @return true if an dragon token is at this location, false otherwise
     */
    private Boolean dragonPresent() {
        if (dragonlocation.actorPresent(this) == false) {
            return false;
        }
        return true;
    }

    /**
     * checks to see if an animal is at this location
     *
     * @return true if an animal is at this location, false otherwise
     */
    private Boolean animalPresent() {
        if (animalLocation.actorPresent(this) == false) {
            return false;
        }
        return true;
    }

    /**
     * checks to see if a cave is at this location
     *
     * @return true if a cave is at this location, false otherwise
     */
    public Boolean cavePresent() {
        return caveLocation.hasCave(this);
    }

}
