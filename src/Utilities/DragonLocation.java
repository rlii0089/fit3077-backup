package Utilities;

import Actors.Actor;
import Actors.DragonCharacter;
import Game.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * A class keeps track of the location of every dragon character on the game board
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */

public class DragonLocation {
    /**
     * map that gets dragon character from location
     */
    private Map<Location, DragonCharacter> locationToActor;

    /**
     * map that gets location from dragon character
     */
    private Map<DragonCharacter, Location> actorToLocation;

    /**
     * single instance of the class
     */
    private static DragonLocation instance;

    /**
     * constructor for DragonLocation
     */
    private DragonLocation() {
        locationToActor = new HashMap<Location, DragonCharacter>();
        actorToLocation = new HashMap<DragonCharacter, Location>();
    }

    /**
     * adds dragon character to record
     *
     * @param dragon   actor to add
     * @param location location of the actor
     */
    public void add(DragonCharacter dragon, Location location) {
        actorToLocation.put(dragon, location);
        locationToActor.put(location, dragon);
    }

    /**
     * move dragon character to new location
     *
     * @param actor dragon character to move
     */
    public void move(DragonCharacter actor, Location newLocation) {
        Location oldLocation = actorToLocation.get(actor);
        actorToLocation.put(actor, newLocation);
        locationToActor.remove(oldLocation);
        locationToActor.put(newLocation, actor);
    }

    /**
     * checker to see if dragon character is at location
     *
     * @param location location to check
     * @return
     */
    public Boolean actorPresent(Location location) {
        return locationToActor.containsKey(location);
    }

    /**
     * get dragon character at the input location
     *
     * @param location location to retrieve dragon character
     * @return dragon character at the location
     */
    public Actor getActorAt(Location location) {
        return locationToActor.get(location);
    }

    /**
     * get location of dragon character
     *
     * @param actor dragon character to get location of
     * @return
     */
    public Location getLocation(Actor actor) {
        return actorToLocation.get(actor);
    }


    /**
     * get an instance of this class
     *
     * @return instance of this class
     */
    public static DragonLocation getInstance() {
        if (instance == null) {
            instance = new DragonLocation();
        }
        return instance;
    }
}
