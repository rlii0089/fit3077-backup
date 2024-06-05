package Utilities;

import Game.Location;
import Gameboard.Cave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class keeps track of the location of every cave on the game board
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.1
 */
public class CaveLocation implements Serializable {

    /**
     * map that gets cave from location
     */
    private Map<Location, Cave> locationToCave;

    /**
     * map that gets location from cave
     */
    private Map<Cave, Location> caveToLocation;

    /**
     * Constructor for CaveLocation
     */
    public CaveLocation() {
        locationToCave = new HashMap<Location, Cave>();
        caveToLocation = new HashMap<Cave, Location>();
    }

    /**
     * adds cave to record
     *
     * @param cave     cave to add
     * @param location location of cave
     */
    public void add(Cave cave, Location location) {
        caveToLocation.put(cave, location);
        locationToCave.put(location, cave);
    }


    /**
     * checks if there is cave at input location
     *
     * @param location location to check
     * @return true if there is cave, false otherwise
     */
    public Boolean hasCave(Location location) {
        return locationToCave.containsKey(location);
    }

    /**
     * get cave at input location
     *
     * @param location location to retrieve cave
     * @return cave at location
     */
    public Cave getCave(Location location) {
        return locationToCave.get(location);
    }

    /**
     * get location of a cave
     *
     * @param cave cave to get location of
     * @return location of cave
     */
    public Location getLocation(Cave cave) {
        return caveToLocation.get(cave);
    }

    /**
     * Retrieves all the caves in the game
     * @return An array list of all caves in the game
     */
    public ArrayList<Cave> getAllCaves(){
        ArrayList<Cave> returnList = new ArrayList<>();
        for(Map.Entry<Cave, Location> entry : caveToLocation.entrySet()){
            returnList.add(entry.getKey());
        }
        return returnList;
    }
}
