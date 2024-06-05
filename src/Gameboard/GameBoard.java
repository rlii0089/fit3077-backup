package Gameboard;

import Actors.DragonCharacter;
import Game.ChitCard;
import Game.Location;
import Game.Player;
import Utilities.ASCIIDisplayMessage;
import Utilities.AnimalLocation;
import Utilities.CaveLocation;
import Utilities.DragonLocation;

import Utilities.Engine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A class that represents the game board which is used in Fiery dragon, containing all the volcano cards, the caves
 * and te dragon tokens in the game. Responsible for enacting movement of dragon characters and checking if a win has
 * taken place
 * animal
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.4
 */
public class GameBoard implements Serializable {
    /**
     * list of the 8 volcano cards
     */
    private ArrayList<VolcanoCard> volcanoCards;


    /**
     * lists in a list containing All the locations in the game board
     */
    private Location[][] board;

    /**
     * the width of the board
     */
    private List<Integer> widths;

    /**
     * the height of the board
     */
    private List<Integer> heights;

    /**
     * A tracker for the locations of all dragons in the game
     */
    private DragonLocation dragonLocation;

    /**
     * A tracker for the locations of all animals in the game
     */
    private AnimalLocation animalLocation;

    /**
     * A tracker for the locations of all caves in the game
     */
    private CaveLocation caveLocation;

    /**
     * List of all locations walkable by a dragon character
     */
    private ArrayList<Location> boardLocations;


    /**
     * constructor for the game board
     *
     * @param volcanoCards list of the 8 volcano cards
     * @param lines        String used to present the look of the board
     */
    public GameBoard(ArrayList<VolcanoCard> volcanoCards, List<String> lines,DragonLocation dragonLocation, AnimalLocation animalLocation, CaveLocation caveLocation) {
        this.volcanoCards = volcanoCards;
        this.dragonLocation = dragonLocation;
        this.animalLocation = animalLocation;
        this.caveLocation = caveLocation;
        createStringMap(lines);
    }

    /**
     * sets up the board and its locations using a string input that depicts how the map should look
     *
     * @param lines string input that depicts how the map should look
     */
    private void createStringMap(List<String> lines) {
        int width = lines.get(0).length();
        int height = lines.size();
        initialiseMap(width, height);

        for (int x : widths) {
            for (int y : heights) {
                char boardChar = lines.get(y).charAt(x);
                at(x, y).setDisplayCharacter(boardChar);
            }
        }
    }

    /**
     * Initialises all locations on the board
     *
     * @param width  width of the board
     * @param height height of the board
     */
    private void initialiseMap(int width, int height) {
        List<Integer> widths = new ArrayList<>();
        List<Integer> heights = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            widths.add(i);
        }
        for (int i = 0; i < height; i++) {
            heights.add(i);
        }

        this.widths = widths;
        this.heights = heights;

        board = new Location[width][height];
        for (int i : widths) {
            for (int j : heights) {
                board[i][j] = new Location(i, j, dragonLocation, animalLocation, caveLocation);
            }
        }

        setLocations();

    }

    /**
     * retrieves the location at the given coordinates
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return location at the given coordinates
     */
    public Location at(int x, int y) {
        return board[x][y];
    }

    /**
     * sets up locations of each of the volcano cards
     */
    public void setLocations() {

        for (int i = 0; i < volcanoCards.size(); i++) {
            volcanoCards.get(i).setLocation(i, this, caveLocation, animalLocation);
        }
    }

    /**
     * add method to the list containing all teh walkable locations
     *
     * @param location location to be added
     */
    public void addToBoardLocations(Location location) {
        if (boardLocations == null) {
            boardLocations = new ArrayList<>();
        }
        boardLocations.add(location);
    }

    /**
     * prints out the current state of the board to console
     */
    public void draw() {
        for (int y : heights) {
            for (int x : widths) {
                Location location = this.at(x,y);
                System.out.print(location.getDisplayChar());
            }
            System.out.println("");
        }
    }

    /**
     * Prints out current location of the dragon token to menu so the player knows what animal he should aim to flip
     * over
     *
     * @param dragonCharacter dragon character of the current player
     */
    public void printCurrentLocation(DragonCharacter dragonCharacter) {
        Location currLocation = dragonLocation.getLocation(dragonCharacter);
        if (caveLocation.hasCave(currLocation)) {
            System.out.println("Player " + dragonCharacter.getName() + " is currently in their cave.");
            Location caveEntrance = caveLocation.getCave(currLocation).getCaveEntrance();
            System.out.println("Try to find a " + animalLocation.getActorAt(caveEntrance).getName().toLowerCase() + " card!");
        } else {
            System.out.println("Player " + dragonCharacter.getName() + " is currently on the " + animalLocation.getActorAt(currLocation).getName().toLowerCase() + " tile.");
        }
    }

    /**
     * Method to return the number of tiles left for a player to reach their cave
     *
     * @param player the player whose tiles are being counted
     * @return the number of tiles left for a player to reach their cave
     */
    public int numberOfTilesFromCave(Player player) {
        DragonCharacter dragonCharacter = player.getDragonCharacter();

        // Add one since location index starts at 0
        int locationIndex = dragonCharacter.getLocationIndex() + 1;
        Location location = dragonLocation.getLocation(dragonCharacter);
        if (!dragonCharacter.getLocationSet().contains(location)){
            locationIndex = dragonCharacter.getLocationSet().indexOf(caveLocation.getCave(location).getCaveEntrance());
        }

        return dragonCharacter.getLocationSet().size() - locationIndex;
    }

    /**
     * Sorts the players based on the number of tiles left for them to reach their cave and prints out the leaderboard
     *
     * @param players list of players to be sorted
     */
    public void allPLayersLeaderboardRanking(ArrayList<Player> players) {
        ArrayList<Player> listOfPlayers = new ArrayList<>(players);

        // Sort the players based on the number of tiles left for them to reach their cave
        listOfPlayers.sort((p1, p2) -> {
            DragonCharacter dragonCharacter1 = p1.getDragonCharacter();
            DragonCharacter dragonCharacter2 = p2.getDragonCharacter();
            int locationIndex1 = dragonCharacter1.getLocationIndex();
            int locationIndex2 = dragonCharacter2.getLocationIndex();
            int distanceToCave1 = dragonCharacter1.getOriginalLocationSet().size() - locationIndex1;
            int distanceToCave2 = dragonCharacter2.getOriginalLocationSet().size() - locationIndex2;
            return distanceToCave1 - distanceToCave2;
        });

        // Print the leaderboard
        ASCIIDisplayMessage.printLeaderboard(listOfPlayers, this);
    }

    /**
     * Methods to ensure no index error when traversing the list of walkable locations
     *
     * @param boardLocation original index
     * @return fixed index
     */
    private int checkBoardLocationIndexing(int boardLocation) {
        int fixedBoardLocation = boardLocation;
        if (boardLocation >= boardLocations.size()) {
            fixedBoardLocation = 0;
        } else if (boardLocation < 0) {
            fixedBoardLocation = boardLocations.size() - 1;
        }

        return fixedBoardLocation;
    }


    /**
     * Inserts the dragon characters onto the board at the start of the game
     *
     * @param dragonCharacter the dragon character to insert
     */
    public void insertDragonCharacter(DragonCharacter dragonCharacter) {
        for (VolcanoCard volcanoCard : volcanoCards) {
            if (volcanoCard.hasCave()) {
                Cave cave = volcanoCard.getCave();
                Location caveLoc = caveLocation.getLocation(cave);
                if (dragonLocation.actorPresent(caveLoc) == false) {
                    dragonLocation.add(dragonCharacter, caveLoc);
                    dragonCharacter.setStartingCave(cave);
                    //set up list of locations the dragon character will travel through
                    ArrayList<Location> locationSet = new ArrayList<>();
                    locationSet.add(caveLoc);
                    setLocationSet(locationSet, cave.getCaveEntrance());
                    locationSet.add(caveLoc);
                    dragonCharacter.setOriginalLocationSet(locationSet);
                    dragonCharacter.setLocationSet(new ArrayList<>(locationSet));
                    dragonCharacter.setLocationIndex(0);
                    break;
                }
            }
        }
    }

    /**
     * Adds onto the location set for dragon characters (locations that dragon tokens walk through)
     *
     * @param locationSet  The location set of the dragon character
     * @param caveEntrance the entrance of the starting cave of the dragon character
     */
    private void setLocationSet(ArrayList<Location> locationSet, Location caveEntrance) {
        int index = boardLocations.indexOf(caveEntrance);
        for (int i = 0; i <= boardLocations.size(); i++) {
            locationSet.add(boardLocations.get(index));
            index++;
            index = checkBoardLocationIndexing(index);
        }

    }

    /**
     * checks if the player has chosen a matching card for their current location
     *
     * @param chitCard chit card being checked
     * @return
     */
    public boolean checkChitCardMatches(DragonCharacter dragonCharacter, ChitCard chitCard) {
        Location currLocation = dragonLocation.getLocation(dragonCharacter);
        if (caveLocation.hasCave(currLocation)) {
            Cave cave = caveLocation.getCave(currLocation);
            if (chitCard.getActor().getName() == animalLocation.getActorAt(cave.getCaveEntrance()).getName()) {
                return true;
            }
        } else if (chitCard.getActor().getName() == animalLocation.getActorAt(currLocation).getName()) {
            return true;
        }
        return false;
    }

}
