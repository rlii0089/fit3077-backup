package Gameboard;

import Actors.DragonCharacter;
import Game.Location;
import Game.Player;
import Utilities.CaveLocation;
import Utilities.DragonLocation;
import Utilities.Engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A class that is responsible for all movements of dragon characters around the board, including collision fights
 * and falling back into a cave
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */
public class MovementManager implements Serializable {

    /**
     * A tracker for the locations of all caves in the game
     */
    private CaveLocation caveLocation;

    /**
     * A tracker for the locations of all dragons in the game
     */
    private DragonLocation dragonLocation;

    /**
     * a flag that checks if a dragon token has moved locations this turn
     */
    private boolean movedThisTurn;

    /**
     * Number of tiles on the board
     */
    private int numOfTiles;

    /**
     * flag used to see if victory as been achieved by a player
     */
    private boolean achievedVictory = false;

    /**
     * the number of the player that has won
     */
    private String winningPlayerName;

    /**
     * Constructor for MovementManager
     * @param dragonLocation Location tracker for all dragon characters
     * @param caveLocation Location tracker for all caves
     */
    public MovementManager(DragonLocation dragonLocation, CaveLocation caveLocation) {
        this.dragonLocation = dragonLocation;
        this.caveLocation = caveLocation;
        numOfTiles = 24;
    }

    /**
     * getter for winning player number
     *
     * @return the number of the player that has won
     */
    public String getWinningPlayerName() {
        return winningPlayerName;
    }

    /**
     * getter for achieved victory flag
     *
     * @return flag used to see if victory as been achieved by a player
     */
    public boolean getAchievedVictory() {
        return achievedVictory;
    }

    /**
     * getter for movedThisTurn
     *
     * @return a flag that checks if a dragon token has moved locations this turn
     */
    public boolean isMovedThisTurn() {
        return movedThisTurn;
    }

    /**
     * Enacts movements of dragon tokens around the board. Firstly, the sequence of locations the specific dragon
     * token needs to travel is extracted and the new location is found. Then checking is done to ensure the dragon
     * token does not go past his starting cave. Checking is also done to see if the dragon has made it back to
     * its starting cave (if it had fallen past before), and then its location set will be set back to its
     * original location set. Lastly, dragon token is moved accordingly using dragonLocation.
     *
     * Note: Collision Fights will not occur if it is collision while moving backwards or if the dragon token is still
     *          in their cave
     *
     * @param player        The player who the current turn belongs to
     * @param numberOfMoves The number of moves the player should attempt to move
     */

    public void moveDragonCharacter(Player player, int numberOfMoves) {
        DragonCharacter dragonCharacter = player.getDragonCharacter();
        ArrayList<Location> locationSet = dragonCharacter.getLocationSet();
        int locationIndex = dragonCharacter.getLocationIndex();
        boolean collisionFightEnabled = true;
        if (caveLocation.hasCave(dragonLocation.getLocation(dragonCharacter))) {
            if (numberOfMoves < 0) {
                return;
            }
            locationIndex = locationSet.indexOf(caveLocation.getCave(dragonLocation.getLocation(dragonCharacter)).getCaveEntrance());
            numberOfMoves -= 1;
            collisionFightEnabled = false;
        }
        int newLocationIndex = locationIndex + numberOfMoves;
        movedThisTurn = false;

        if (newLocationIndex >= locationSet.size()) { //checks if dragon token goes past ending cave
            System.out.println("Oh no! You have gone past your cave. You will now return to your original location");
        } else if (newLocationIndex >= 0) {
            Location newLocation = locationSet.get(newLocationIndex);
            if (!dragonLocation.actorPresent(newLocation)) { //checks if collision with other dragon token occurs
                dragonLocation.move(dragonCharacter, newLocation);
                dragonCharacter.setLocationIndex(newLocationIndex);
                movedThisTurn = true;

                if (!dragonCharacter.getLocationSet().equals(dragonCharacter.getOriginalLocationSet())) { // if dragon has fallen behind starting cave
                    if (newLocationIndex > (dragonCharacter.getLocationSet().size() - dragonCharacter.getOriginalLocationSet().size())) { // if dragon  has returned to starting cave entrance
                        dragonCharacter.setLocationSet(new ArrayList<>(dragonCharacter.getOriginalLocationSet()));
                        dragonCharacter.setLocationIndex(dragonCharacter.getLocationSet().indexOf(dragonLocation.getLocation(dragonCharacter)));
                    }
                }
            } else {
                if (numberOfMoves > 0 & collisionFightEnabled) { //constraint for avoiding collision fight when a player is moving backwards
                    Location currentLocation = dragonCharacter.getLocationSet().get(locationIndex);
                    DragonCharacter newLocationCharacter = dragonLocation.getActorAt(newLocation);
                    collisionFight(dragonCharacter, newLocationCharacter, currentLocation, newLocation, newLocationIndex);
                }
                else{
                    System.out.println("Another dragon is blocking you from moving, you will now return to your original position");
                }
            }
        }

        if (dragonCharacter.getLocationIndex() == locationSet.size() - 1) { //checks for win
            achievedVictory = true;
            winningPlayerName = dragonCharacter.getName();
        }
    }
    /**
     * Used to resolve collisions when a player moves onto a tile occupied by another player. The player
     * moving chooses between heads and tails and the game randomly generates a number (either 0 or 1) which corresponds to
     * either option. If guessed successfully, the players switch position and the turn is ended otherwise the
     * player does not move.
     *
     * @param currentDragonCharacter player currently their turn
     * @param newLocationCharacter  player that is occupying the collision tile
     * @param currentLocation  location of the currentDragonCharacter before they move
     * @param newLocation   location of the collision tile
     * @param newLocationIndex  Index of newLocation in the currentDragonCharacter's locationSet
     */
    private void collisionFight(DragonCharacter currentDragonCharacter, DragonCharacter newLocationCharacter, Location currentLocation, Location newLocation, int newLocationIndex){
        System.out.println("Player " + currentDragonCharacter.getName() + " has collided with player " + newLocationCharacter.getName() + " we must resolve the collision with a coin flip");
        int chosenValue = Engine.getInstance().getValidIntegerInput(currentDragonCharacter.getName() + " choose a number 0 (Heads) or 1 (Tails): ",0,1);
        Random random = new Random();
        int randomValue = random.nextInt(2);
        System.out.println(randomValue + " was generated randomly");
        if(chosenValue == randomValue){
            System.out.println(currentDragonCharacter.getName() + " guessed correctly and will swap positions with " + newLocationCharacter.getName());

            ArrayList<Location> newDragonLocationSet = newLocationCharacter.getLocationSet();
            int currentLocIndex = newDragonLocationSet.indexOf(currentLocation);
            int newLocIndex = newDragonLocationSet.indexOf(newLocation);
            if (currentLocIndex > newLocIndex) { // check if dragon has fallen past starting cave
                newDragonLocationSet.remove(0);
                ArrayList<Location> locationsToAdd = new ArrayList<>();
                while (currentLocIndex < newDragonLocationSet.size() - 1) {
                    locationsToAdd.add(newDragonLocationSet.get(currentLocIndex - 1));
                    currentLocIndex++;
                }
                Collections.reverse(locationsToAdd);
                repairLocationSet(newLocationCharacter, locationsToAdd);
            }

            //swap dragons
            dragonLocation.removeActor(newLocationCharacter);
            dragonLocation.move(currentDragonCharacter, newLocation);
            currentDragonCharacter.setLocationIndex(newLocationIndex);
            dragonLocation.add(newLocationCharacter, currentLocation);
            newLocationCharacter.setLocationIndex(newLocationCharacter.getLocationSet().indexOf(currentLocation));

        }
        else{
            System.out.println(currentDragonCharacter.getName() + " guessed incorrectly and will not move forwards this turn");
        }
    }


    /**
     * Moves the current player's dragon character backwards until an empty cave is found, and moves the dragon
     * character into that empty cave
     * @param player The current player
     */
    public void returnToCave(Player player) {
        DragonCharacter dragonCharacter = player.getDragonCharacter();
        if (!caveLocation.hasCave(dragonLocation.getLocation(dragonCharacter))) { //makes sure dragon character is not in a cave
            ArrayList<Location> locationSet = dragonCharacter.getLocationSet();
            int locationIndex = dragonCharacter.getLocationIndex();
            ArrayList<Cave> caves = caveLocation.getAllCaves();
            ArrayList<Location> caveEntrances = new ArrayList<>();
            ArrayList<Location> newLocations = new ArrayList<>();
            boolean fallenPastStartingPosition = false; // flag to check if dragon character falls behind starting cave
            for (Cave cave : caves) { // retrieves all cave entrances of empty caves
                if (!dragonLocation.actorPresent(caveLocation.getLocation(cave))) {
                    caveEntrances.add(cave.getCaveEntrance());
                } else {
                    caveEntrances.add(null);
                }
            }

            while (!caveEntrances.contains(locationSet.get(locationIndex))) {
                locationIndex -= 1;
                if (locationIndex < 0) {
                    if (locationSet.equals(dragonCharacter.getOriginalLocationSet())) { // checks if dragon character is not currently behind starting cave
                        locationIndex = locationSet.size() - 3;
                    }
                    else{
                        locationIndex+=numOfTiles + 1;
                    }
                    fallenPastStartingPosition = true;
                }
                if (fallenPastStartingPosition) {
                    newLocations.add(locationSet.get(locationIndex));
                }
            }

            Cave freeCave = caves.get(caveEntrances.indexOf(locationSet.get(locationIndex))); // find the cave to move into
            dragonLocation.move(dragonCharacter, caveLocation.getLocation(freeCave));

            if (fallenPastStartingPosition) {
                dragonCharacter.getLocationSet().remove(0); // remove starting cave from location set
                repairLocationSet(dragonCharacter, newLocations); // add all locations from curr to starting cave entrance
                locationSet.add(0, caveLocation.getLocation(freeCave)); // add curr location
                dragonCharacter.setLocationIndex(0);
            }
        }
    }

    /**
     * Used to add more locations to a dragon character's location set if they were to fall past their starting cave
     * @param dragonCharacter
     * @param newLocations
     */
    private void repairLocationSet(DragonCharacter dragonCharacter, ArrayList<Location> newLocations) {
        ArrayList<Location> locationSet = dragonCharacter.getLocationSet();
        for (Location location : newLocations) {
            locationSet.add(0, location);
        }
    }
}
