package Actors;

/**
 * A creature that exists in the Fiery Dragon game
 * @author Max Zhuang
 * @version 1.0.0
 */

public abstract class Animal extends Actor {

    /**
     * constructor for animal
     * @param name name of animal
     * @param displayChar character that depicts the animal when displayed
     */
    public Animal(String name, char displayChar) {
        super(name, displayChar);
    }
}
