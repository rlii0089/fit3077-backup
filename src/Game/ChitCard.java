package Game;

import Actors.Actor;

import java.io.Serializable;

/**
 * A class representing physical chit card in Fiery Dragon which the player flips over during their turn
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.1
 */
public class ChitCard implements Serializable {

    /**
     * The actor contained inside the chit card (could be animal or pirate skeleton)
     */
    protected Actor actor;

    /**
     * The number of the specific actor contained inside the card
     */
    protected int quantity;

    /**
     * Determines whether the card is showing or not
     */
    protected boolean revealed;

    /**
     * Constructor for chit card
     *
     * @param actor    The actor contained inside the chit card (could be animal or pirate skeleton)
     * @param quantity The number of the specific actor contained inside the card
     */
    public ChitCard(Actor actor, int quantity) {
        this.actor = actor;
        this.quantity = quantity;
        this.revealed = false;
    }

    /**
     * getter for the actor on this chit card
     *
     * @return actor on this chit card
     */
    public Actor getActor() {
        return this.actor;
    }

    /**
     * getter for the quantity of the actor on this chit card
     *
     * @return the quantity of the actor on this chit card
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Will reveal the card once the player has chosen it, then print the chosen card
     */
    public boolean revealCard() {
        if (!revealed) {
            revealed = true;
            System.out.println("You have chosen the " + this.quantity + "x " + this.actor.getName() + " card.");
            return true;
        }
        return false;
    }

    /**
     * Will become unrevealed when switching turns.
     */
    public void resetCard() {
        this.revealed = false;
    }
}
