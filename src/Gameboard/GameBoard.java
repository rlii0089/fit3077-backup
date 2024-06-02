package Gameboard;

import Actors.DragonCharacter;
import Game.ChitCard;
import Game.Location;
import Game.Player;
import Utilities.ASCIIDisplayMessage;
import Utilities.AnimalLocation;
import Utilities.CaveLocation;
import Utilities.DragonLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the game board which is used in Fiery dragon, containing all the volcano cards, the caves
 * and te dragon tokens in the game. Responsible for enacting movement of dragon characters and checking if a win has
 * taken place
 * animal
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.1
 */
public class GameBoard {
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
     * flag used to see if victory as been achieved by a player
     */
    private boolean achievedVictory = false;

    /**
     * the number of the player that has won
     */
    private String winningPlayerName;

    /**
     * a flag that checks if a dragon token has moved locations this turn
     */
    private boolean movedThisTurn;

    /**
     * constructor for the game board
     *
     * @param volcanoCards list of the 8 volcano cards
     * @param lines        String used to present the look of the board
     */
    public GameBoard(ArrayList<VolcanoCard> volcanoCards, List<String> lines) {
        this.volcanoCards = volcanoCards;
        this.dragonLocation = DragonLocation.getInstance();
        this.animalLocation = AnimalLocation.getInstance();
        this.caveLocation = CaveLocation.getInstance();
        createStringMap(lines);
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
                board[i][j] = new Location(i, j);
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
            volcanoCards.get(i).setLocation(i, this);
        }
    }

    /**
     * getter for movedThisTurn
     * @return a flag that checks if a dragon token has moved locations this turn
     */
    public boolean isMovedThisTurn() {
        return movedThisTurn;
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
                System.out.print(this.at(x, y).getDisplayChar());
            }
            System.out.println("");
        }
    }

    /**
     * Prints out current location of the dragon token to menu so the player knows what animal he should aim to flip
     * over
     * @param dragonCharacter dragon character of the current player
     */
    public void printCurrentLocation(DragonCharacter dragonCharacter) {
        Location currLocation = dragonLocation.getLocation(dragonCharacter);
        if (caveLocation.hasCave(currLocation)) {
            System.out.println("Player " + dragonCharacter.getName() + " is currently in their cave");
            Location caveEntrance = caveLocation.getCave(currLocation).getCaveEntrance();
            System.out.println("Try to find a " + animalLocation.getActorAt(caveEntrance).getName() + " card!");
        } else {
            System.out.println("Player " + dragonCharacter.getName() + " is current on a tile with the animal " + animalLocation.getActorAt(currLocation).getName());
        }
    }


    /**
     * Enacts movements of dragon tokens around the board. Firstly, the sequence of locations the specific dragon
     * token needs to travel is extracted and the new location is found. Then checking is done to ensure the dragon
     * token does not go past his starting cave, and does not move to a location where another dragon token is
     * already there. Lastly, dragon token is moved accordingly using actorlocation.
     *
     * @param player The player who the current turn belongs to
     * @param numberOfMoves The number of moves the player should attempt to move
     */

    public void moveDragonCharacter(Player player, int numberOfMoves) {
        DragonCharacter dragonCharacter = player.getDragonCharacter();
        ArrayList<Location> locationSet = dragonCharacter.getLocationSet();
        int locationIndex = dragonCharacter.getLocationIndex();
        if(caveLocation.hasCave(dragonLocation.getLocation(dragonCharacter))){
            if(numberOfMoves < 0){
                return;
            }
            locationIndex = locationSet.indexOf(caveLocation.getCave(dragonLocation.getLocation(dragonCharacter)).getCaveEntrance());
            numberOfMoves -= 1;
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

                if(dragonCharacter.getOriginalLocationSet().size() != dragonCharacter.getLocationSet().size()){
                    if(newLocationIndex > (dragonCharacter.getLocationSet().size() - dragonCharacter.getOriginalLocationSet().size())){
                        dragonCharacter.setLocationSet(new ArrayList<>(dragonCharacter.getOriginalLocationSet()));
                        dragonCharacter.setLocationIndex(dragonCharacter.getLocationSet().indexOf(dragonLocation.getLocation(dragonCharacter)));
                    }
                }
            } else{
                System.out.println("Oh no! Another dragon token is already at that location. you will now return to your original location");
            }
        }

        if (dragonCharacter.getLocationIndex() == locationSet.size() - 1) { //checks for win
            achievedVictory = true;
            winningPlayerName = dragonCharacter.getName();
        }
    }

    /**
     * Method to return the number of tiles left for a player to reach their cave
     * @param player the player whose tiles are being counted
     * @return the number of tiles left for a player to reach their cave
     */
    public static int numberOfTilesFromCave(Player player){
        DragonCharacter dragonCharacter = player.getDragonCharacter();

        // Add one since location index starts at 0
        int locationIndex = dragonCharacter.getLocationIndex() + 1;

        return dragonCharacter.getOriginalLocationSet().size() - locationIndex;
    }

    /**
     * Sorts the players based on the number of tiles left for them to reach their cave and prints out the leaderboard
     * @param players list of players to be sorted
     */
    public void allPLayersLeaderboardRanking(ArrayList<Player> players){
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
        ASCIIDisplayMessage.printLeaderboard(listOfPlayers);
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
     * @param locationSet The location set of the dragon character
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

    public void returnToCave(Player player){
        DragonCharacter dragonCharacter = player.getDragonCharacter();
        if(!caveLocation.hasCave(dragonLocation.getLocation(dragonCharacter))) {
            ArrayList<Location> locationSet = dragonCharacter.getLocationSet();
            int locationIndex = dragonCharacter.getLocationIndex();
            ArrayList<Cave> caves = caveLocation.getAllCaves();
            ArrayList<Location> caveEntrances = new ArrayList<>();
            ArrayList<Location> newLocations = new ArrayList<>();
            boolean fallenPastStartingCave = false;
            for (Cave cave : caves) {
                if (!dragonLocation.actorPresent(caveLocation.getLocation(cave))) {
                    caveEntrances.add(cave.getCaveEntrance());
                } else {
                    caveEntrances.add(null);
                }
            }
            while (!caveEntrances.contains(locationSet.get(locationIndex))) {
                locationIndex -= 1;
                if (locationIndex < 0) {
                    locationIndex = locationSet.size() - 3;
                    fallenPastStartingCave = true;
                }
                if (fallenPastStartingCave) {
                    newLocations.add(locationSet.get(locationIndex));
                }
            }
            Cave freeCave = caves.get(caveEntrances.indexOf(locationSet.get(locationIndex)));
            dragonLocation.move(dragonCharacter, caveLocation.getLocation(freeCave));
            if (fallenPastStartingCave) {
                dragonCharacter.getLocationSet().remove(0); // remove starting cave from location set
                repairLocationSet(dragonCharacter, newLocations); // add all locations from curr to starting cave entrance
                locationSet.add(0, caveLocation.getLocation(freeCave)); // add curr location
                dragonCharacter.setLocationIndex(0);
            }
        }
    }

    private void repairLocationSet(DragonCharacter dragonCharacter, ArrayList<Location> newLocations){
        ArrayList<Location> locationSet = dragonCharacter.getLocationSet();
        for(Location location : newLocations){
            locationSet.add(0,location);
        }
    }
}
