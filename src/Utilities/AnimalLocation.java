package Utilities;

import Actors.Actor;
import Actors.Animal;
import Actors.DragonCharacter;
import Game.Location;

import java.util.HashMap;
import java.util.Map;

public class AnimalLocation {

    /**
     * map that gets actor from location
     */
    private Map<Location, Animal> locationToAnimal;

    /**
     * map that gets location from actor
     */
    private Map<Animal, Location> animalToLocation;

    /**
     * single instance of the class
     */

    private static AnimalLocation instance;

    /**
     * constructor for ActorLocation
     */
    private AnimalLocation(){
        locationToAnimal = new HashMap<Location, Animal>();
        animalToLocation = new HashMap<Animal, Location>();
    }

    /**
     * adds actor to record
     * @param animal actor to add
     * @param location location of the actor
     */
    public void add(Animal animal, Location location) {
        animalToLocation.put(animal, location);
        locationToAnimal.put(location, animal);
    }

    /**
     * checker to see if actor is at location
     * @param location location to check
     * @return
     */
    public Boolean actorPresent(Location location){
        return locationToAnimal.containsKey(location);
    }

    /**
     * get actor at the input location
     * @param location location to retrieve actor
     * @return
     */
    public Actor getActorAt(Location location) {
        return locationToAnimal.get(location);
    }

    /**
     * get location of actor
     * @param actor actor to get location of
     * @return
     */
    public Location getLocation(Actor actor){
        return animalToLocation.get(actor);
    }

    /**
     * get an instance of this class
     * @return instance of this class
     */
    public static AnimalLocation getInstance() {
        if (instance == null){
            instance = new AnimalLocation();
        }
        return instance;
    }
}
