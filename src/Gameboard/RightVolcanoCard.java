package Gameboard;

import Game.Location;
import Utilities.AnimalLocation;
import Utilities.CaveLocation;
import Utilities.VolcanoCardConfiguration;

import java.util.ArrayList;

/**
 * A class that represents a volcano card on the right side of the board
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */

public class RightVolcanoCard extends VolcanoCard {

    /**
     * Initialises the location of the tiles, and the walkable locations for this volcano card on the game board
     *
     * @param volcanoCardNumber identifier of the volcano card to find the configuration
     * @param board             the list of locations in the game board
     */
    @Override
    public void setLocation(int volcanoCardNumber, GameBoard board, CaveLocation caveLocation, AnimalLocation animalLocation) {
        ArrayList<Integer> coordinates = VolcanoCardConfiguration.getInstance().getLocation(volcanoCardNumber);
        this.location = board.at(coordinates.get(0), coordinates.get(1));

        for (int i = 0; i < animalTiles.size(); i++) {
            Location tileLocation = board.at(location.x(), location.y() + 3 * i);
            animalTiles.get(i).setLocation(tileLocation, animalLocation);
            addToBoardLocations(board, tileLocation);
        }

        if (hasCave()) {
            Location caveLoc = board.at(location.x() + 2, location.y() + 3);
            caveLocation.add(cave, caveLoc);
            cave.setCaveEntrance(board.at(location.x(), location.y() + 3));
        }
    }
}
