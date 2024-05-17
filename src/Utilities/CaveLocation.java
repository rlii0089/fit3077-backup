package Utilities;

import Game.Location;
import Gameboard.Cave;

import java.util.HashMap;
import java.util.Map;

/**
 * A class keeps track of the location of every cave on the game board
 *
 * @author Max Zhuang
 * @version 1.0.0
 */
public class CaveLocation {

    /**
     * map that gets cave from location
     */
    private Map<Location, Cave> locationToCave;

    /**
     * map that gets location from cave
     */
    private Map<Cave, Location> caveToLocation;

    /**
     * single instance of this class
     */
    private static CaveLocation instance;

    /**
     * Constructor for CaveLocation
     */
    private CaveLocation() {
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
     * get an instance of this class
     *
     * @return instance of this class
     */
    public static CaveLocation getInstance() {
        if (instance == null) {
            instance = new CaveLocation();
        }
        return instance;
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
}
