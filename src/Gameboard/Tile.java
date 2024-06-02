package Gameboard;

import Actors.Animal;
import Game.Location;
import Utilities.AnimalLocation;

/**
 * A class that represents the tile object that makes of the three spots in a volcano card, each tile will contain one
 * animal
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */
public class Tile {
    /**
     * the animal contained in the tile
     */
    private Animal animal;
    /**
     * the location of the tile
     */
    private Location location;

    /**
     * Constructor of tile
     */
    public Tile() {
    }

    /**
     * getter of animal
     *
     * @return the animal contained in the tile
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * setter of animal
     *
     * @param animal the animal contained in the tile
     */
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    /**
     * setter of location
     *
     * @param location the location of the tile
     */
    public void setLocation(Location location) {
        this.location = location;
        AnimalLocation.getInstance().add(animal, location);
    }


}
