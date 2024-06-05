package Gameboard;

import Game.Location;
import Utilities.AnimalLocation;
import Utilities.CaveLocation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An abstract class depicting the real life volcano card component of fiery dragon
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */

public abstract class VolcanoCard implements Serializable {
    /**
     * list of tiles on this volcano card (should have size of 3)
     */
    protected ArrayList<Tile> animalTiles;

    /**
     * The cave attached to this volcano card
     */
    protected Cave cave;

    /**
     * The location of the first tile in this volcano card
     */
    protected Location location;

    /**
     * constructor
     */
    public VolcanoCard() {
        this.animalTiles = new ArrayList<>();
    }

    /**
     * getter for list of tiles on this volcano card
     * @return list of tiles on this volcano card
     */
    public ArrayList<Tile> getAnimalTiles() {
        return animalTiles;
    }

    /**
     * getter for cave attached to this volcano card
     * @return cave attached to this volcano card
     */
    public Cave getCave() {
        return cave;
    }

    /**
     * Initialises the location of the tiles, and the walkable locations for this volcano card on the game board
     *
     * @param volcanoCardNumber identifier of the volcano card to find the configuration
     * @param board             the list of locations in the game board
     */
    public abstract void setLocation(int volcanoCardNumber, GameBoard board, CaveLocation caveLocation, AnimalLocation animalLocation);

    /**
     * Adds the locations that are 'walkable' by dragon tokens
     * @param board The game board of the game
     * @param location the location that will be added to the list of 'walkable' locations
     */
    protected void addToBoardLocations(GameBoard board, Location location) {
        board.addToBoardLocations(location);
    }

    /**
     * setter for cave
     * @param cave the cave to attach to this volcano card
     */
    public void setCave(Cave cave) {
        this.cave = cave;
    }

    /**
     * add to the list of tiles on this volcano card
     * @param tile tile to add
     */
    public void addTile(Tile tile) {
        if (this.animalTiles.size() < 3) {
            this.animalTiles.add(tile);
        } else {
            System.out.println("error. volcano card tile limit reached");
        }
    }

    /**
     * checker to see if there is a cave attached to this volcano card
     * @return true if cave is attached, false otherwise
     */
    public Boolean hasCave() {
        if (cave == null) {
            return false;
        }
        return true;
    }


}
