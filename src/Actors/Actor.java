package Actors;

import Game.Printable;

/**
 * An entity that is alive and a part of the game
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */
public abstract class Actor implements Printable {
    /**
     * The name of the Actor
     */
    private final String name;

    /**
     * The character which depicts the actor shown when diplayed
     */
    private final char displayChar;

    /**
     * Constructor for the actor
     *
     * @param name        name of actor
     * @param displayChar character which depicts the actor shown when displayed
     */
    public Actor(String name, char displayChar) {
        this.name = name;
        this.displayChar = displayChar;
    }

    /**
     * retrieves name of actor
     *
     * @return name of actor
     */
    public String getName() {
        return name;
    }

    /**
     * retrieves display character of actor
     *
     * @return display character of actor
     */
    public char getDisplayChar() {
        return displayChar;
    }
}
