package Utilities;

import Actors.Actor;
import Actors.DragonCharacter;
import Game.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * A class keeps track of the location of every actor on the game board
 *
 * @author Max Zhuang
 * @version 1.0.0
 */

public class DragonLocation {
    /**
     * map that gets actor from location
     */
    private Map<Location, DragonCharacter> locationToActor;

    /**
     * map that gets location from actor
     */
    private Map<DragonCharacter, Location> actorToLocation;

    /**
     * single instance of the class
     */
    private static DragonLocation instance;

    /**
     * constructor for ActorLocation
     */
    private DragonLocation() {
        locationToActor = new HashMap<Location, DragonCharacter>();
        actorToLocation = new HashMap<DragonCharacter, Location>();
    }

    /**
     * adds actor to record
     *
     * @param dragon   actor to add
     * @param location location of the actor
     */
    public void add(DragonCharacter dragon, Location location) {
        actorToLocation.put(dragon, location);
        locationToActor.put(location, dragon);
    }

    /**
     * move actor to new location
     *
     * @param actor actor to move
     */
    public void move(DragonCharacter actor, Location newLocation) {
        Location oldLocation = actorToLocation.get(actor);
        actorToLocation.put(actor, newLocation);
        locationToActor.remove(oldLocation);
        locationToActor.put(newLocation, actor);
    }

    /**
     * checker to see if actor is at location
     *
     * @param location location to check
     * @return
     */
    public Boolean actorPresent(Location location) {
        return locationToActor.containsKey(location);
    }

    /**
     * get actor at the input location
     *
     * @param location location to retrieve actor
     * @return
     */
    public Actor getActorAt(Location location) {
        return locationToActor.get(location);
    }

    /**
     * get location of actor
     *
     * @param actor actor to get location of
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
