package Game;

import Actors.DragonCharacter;

import java.io.Serializable;

/**
 * A class used to represent a player of the game
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.1
 */

public class Player implements Serializable {
    /**
     * the dragon token this player controls
     */
    private DragonCharacter dragonCharacter;

    /**
     * the age of the player
     */
    private int age;

    /**
     * Constructor for player
     *
     * @param dragonCharacter the dragon token thus player controls
     */
    public Player(DragonCharacter dragonCharacter, int age) {
        this.dragonCharacter = dragonCharacter;
        this.age = age;
    }

    /**
     * getter for dragon character
     *
     * @return dragon character the player controls
     */
    public DragonCharacter getDragonCharacter() {
        return dragonCharacter;
    }

    /**
     * getter for player's age
     *
     * @return player's age
     */
    public int getAge() {
        return age;
    }
}
