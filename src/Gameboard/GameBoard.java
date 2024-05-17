package Gameboard;

import Actors.DragonCharacter;
import Game.Location;
import Game.Player;
import Game.ChitCard;
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
 * @author Max Zhuang, Brandon Luu
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

    private boolean movedThisTurn;

    /**
     * constructor for the game board
     * @param volcanoCards list of the 8 volcano cards
     * @param lines String used to presesent the look of the board
     */
    public GameBoard(ArrayList<VolcanoCard> volcanoCards, List<String> lines){
        this.volcanoCards = volcanoCards;
        this.dragonLocation = DragonLocation.getInstance();
        this.animalLocation = AnimalLocation.getInstance();
        this.caveLocation = CaveLocation.getInstance();
        createStringMap(lines);
    }

    /**
     * getter for winning player number
     * @return the number of the player that has won
     */
    public String getWinningPlayerName() {
        return winningPlayerName;
    }

    /**
     * getter for achieved victory flag
     * @return flag used to see if victory as been achieved by a player
     */
    public boolean getAchievedVictory(){
        return achievedVictory;
    }

    /**
     * sets up the board and its locations using a string input that depicts how the map should look
     * @param lines string input that depicts how the map should look
     */
    private void createStringMap(List<String> lines){
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
     * @param width width of the board
     * @param height height of the board
     */
    private void initialiseMap(int width, int height){
        List<Integer> widths = new ArrayList<>();
        List<Integer> heights = new ArrayList<>();
        for (int i = 0; i < width; i++){
            widths.add(i);
        }
        for (int i = 0; i < height; i++){
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
     * @param x x coordinate
     * @param y y coordinate
     * @return location at the given coordinates
     */
    public Location at(int x, int y){
        return board[x][y];
    }

    /**
     * sets up locations of each of the volcano cards
     */
    public void setLocations(){

        for(int i = 0; i < volcanoCards.size(); i++){
            volcanoCards.get(i).setLocation(i, this);
        }
    }

    public boolean isMovedThisTurn() {
        return movedThisTurn;
    }

    /**
     * add method to the list containing all teh walkable locations
     * @param location location to be added
     */
    public void addToBoardLocations(Location location){
        if(boardLocations == null){
            boardLocations = new ArrayList<>();
        }
        boardLocations.add(location);
    }

    /**
     * prints out the current state of the board to console
     */
    public void draw(){
        for(int y : heights){
            for (int x : widths){
                System.out.print(this.at(x,y).getDisplayChar());
            }
            System.out.println("");
        }
    }

    public void printCurrentLocation(DragonCharacter dragonCharacter){
        Location currLocation = dragonLocation.getLocation(dragonCharacter);
        if (caveLocation.hasCave(currLocation)){
            System.out.println("Player " + dragonCharacter.getName() + " is currently in their cave");
            Location caveEntrance = caveLocation.getCave(currLocation).getCaveEntrance();
            System.out.println("Try to find a " + animalLocation.getActorAt(caveEntrance).getName() + " card!");
        }
        else{
            System.out.println("Player " + dragonCharacter.getName() + " is current on a tile with the animal " + animalLocation.getActorAt(currLocation).getName());
        }
    }

    /**
     * Completes movement of the dragon character that represents the cyurent player
     * @param player the current player
     * @param numberOfMoves the number of moves to be made
     */
    /*
    public void moveDragonCharacter(Player player, int numberOfMoves) {
        ArrayList<Location> locationsThisTurn = new ArrayList<>();
        DragonCharacter dragonCharacter = player.getDragonCharacter();
        initiateMove(dragonCharacter, numberOfMoves, locationsThisTurn);
    }
    */
    /*
    /**
     * Initiates the move of the dragon character. Firstly checking whether is a forward of backward movement. Then
     * checking if the dragon character has almost won (if they have walked past the second last tile before the cave).
     * Another check to see if the dragon character is right in front of the cave to see if it can win. This is
     * recursively called so that the dragon character moves one tile at a time so that the necessary checks can be made.
     *
     * Location this turn records all locations that the dragon character has gone through this turn, so that the
     * dragon character can return to its initial location if needed (collision or not exact amount of moves to enter cave)
     *
     * @param dragonCharacter the dragon character to move
     * @param numberOfMoves the number of moves to make (negative means moving backwards)
     * @param locationsThisTurn a record of all locations the dragon character has been this turn


    private void initiateMove(DragonCharacter dragonCharacter, int numberOfMoves, ArrayList<Location> locationsThisTurn){
        Location characterLocation = actorLocation.getLocation(dragonCharacter);
        if (numberOfMoves > 0) {
            locationsThisTurn.add(characterLocation);
            if (checkAlmostWin(dragonCharacter)){ // if walking past second last tile
                dragonCharacter.setCanWin(true);
            }
            if (checkForCaveEntrance(dragonCharacter)) {
                if (dragonCharacter.isCanWin()) {
                    if (checkForWin(numberOfMoves)) { // if the player can win
                        achievedVictory = true;
                        winningPlayerNumber = dragonCharacter.getPlayerNumber();
                    } else { // if player cannot win adn has to go back to intial location
                        actorLocation.move(dragonCharacter, locationsThisTurn.get(0));
                    }
                } else {
                    int boardLocationIndex = boardLocations.indexOf(characterLocation);
                    int newBoardLocationIndex = checkBoardLocationIndexing(boardLocationIndex + 1);
                    Location newLocation = boardLocations.get(newBoardLocationIndex);
                    actorLocation.move(dragonCharacter, newLocation);
                    initiateMove(dragonCharacter, numberOfMoves - 1, locationsThisTurn);
                }
            } else {
                if (characterLocation.cavePresent()) { //to move out of cave
                    Cave cave = CaveLocation.getInstance().getCave(characterLocation);
                    actorLocation.move(dragonCharacter, cave.getCaveEntrance());
                } else {
                    int boardLocationIndex = boardLocations.indexOf(characterLocation);
                    int newBoardLocationIndex = checkBoardLocationIndexing(boardLocationIndex + 1);
                    Location newLocation = boardLocations.get(newBoardLocationIndex);
                    actorLocation.move(dragonCharacter, newLocation);
                }
                initiateMove(dragonCharacter, numberOfMoves - 1, locationsThisTurn);
            }

        } else if (numberOfMoves < 0) { // to move backwards
            if (characterLocation.cavePresent() == false) { // does not move back further if in cave
                if (checkAlmostWin(dragonCharacter)){ // resets flag
                    dragonCharacter.setCanWin(false);
                }
                if (checkForCaveEntrance(dragonCharacter) & !dragonCharacter.isCanWin()) {
                    Location caveLocation = CaveLocation.getInstance().getLocation(dragonCharacter.getStartingCave());
                    actorLocation.move(dragonCharacter, caveLocation);
                } else {
                    int boardLocationIndex = boardLocations.indexOf(characterLocation);
                    int newBoardLocationIndex = checkBoardLocationIndexing(boardLocationIndex - 1);
                    Location newLocation = boardLocations.get(newBoardLocationIndex);
                    actorLocation.move(dragonCharacter, newLocation);

                }
                initiateMove(dragonCharacter, numberOfMoves + 1,locationsThisTurn);
            }
        }
    }
    */

    public void moveDragonCharacter(Player player, int numberOfMoves) {
        DragonCharacter dragonCharacter = player.getDragonCharacter();
        ArrayList<Location> locationSet = dragonCharacter.getLocationSet();
        int locationIndex = dragonCharacter.getLocationIndex();
        int newLocationIndex = locationIndex + numberOfMoves;
        movedThisTurn = false;

        if (newLocationIndex >= locationSet.size()){
            newLocationIndex = locationIndex;
            System.out.println("Oh no! You have gone past your cave. You will now return to your original location");
        }
        else {
            if (newLocationIndex < 0) {
                newLocationIndex = 0;
            }

            Location newLocation = locationSet.get(newLocationIndex);
            if (!dragonLocation.actorPresent(newLocation)) {
                dragonLocation.move(dragonCharacter, newLocation);
                dragonCharacter.setLocationIndex(newLocationIndex);
                movedThisTurn = true;
            } else if (locationIndex != 0) {
                System.out.println("Oh no! Another dragon token is already at that location. you will now return to your original location");
            }
        }

        if (dragonCharacter.getLocationIndex() == locationSet.size()-1){
            achievedVictory = true;
            winningPlayerName = dragonCharacter.getName();
        }
    }

    /**
     * Methods to ensure no index error when traversing the list of walkable locations
     * @param boardLocation original index
     * @return fixed index
     */
    private int checkBoardLocationIndexing(int boardLocation){
        int fixedBoardLocation = boardLocation;
        if (boardLocation >= boardLocations.size()){
            fixedBoardLocation = 0;
        } else if (boardLocation < 0) {
            fixedBoardLocation = boardLocations.size() - 1;
        }

        return fixedBoardLocation;
    }

    /*
    /**
     * checks if the dragon character is in front of its starting cave
      * @param dragonCharacter dragon character to check
     * @return true if the dragon character is in front of its starting cave, false otherwise

    private boolean checkForCaveEntrance(DragonCharacter dragonCharacter){
        Cave startingCave = dragonCharacter.getStartingCave();
        Location caveEntrance = startingCave.getCaveEntrance();
        if (caveEntrance == dragonLocation.getLocation(dragonCharacter)) {
            return true;
        }
        return false;
    }

    /**
     * checks if the dragon character is one away from the front of its starting cave
     * @param dragonCharacter dragon character to check
     * @return true if the dragon character is one away from the front of its starting cave, false otherwise

    private boolean checkAlmostWin(DragonCharacter dragonCharacter){
        Cave startingCave = dragonCharacter.getStartingCave();
        Location caveEntrance = startingCave.getCaveEntrance();
        Location lastTile = boardLocations.get(boardLocations.indexOf(caveEntrance)-1);
        if (lastTile == dragonLocation.getLocation(dragonCharacter)){
            return true;
        }
        return false;
    }

    /**
     * checks if the dragon character is suitable to win
     * @param numberOfMoves the number of moves to make
     * @return

    private boolean checkForWin(int numberOfMoves){
        if (numberOfMoves == 1){
            return true;
        }
        return false;
    }
    */
    /**
     * Inserts the dragon characters onto the board at the start of the game
     * @param dragonCharacter the dragon character to insert
     */
    public void insertDragonCharacter(DragonCharacter dragonCharacter){
        for (VolcanoCard volcanoCard : volcanoCards){
            if (volcanoCard.hasCave()){
                Cave cave = volcanoCard.getCave();
                Location caveLoc = caveLocation.getLocation(cave);
                if (dragonLocation.actorPresent(caveLoc) == false){
                    dragonLocation.add(dragonCharacter, caveLoc);
                    dragonCharacter.setStartingCave(cave);
                    ArrayList<Location> locationSet = new ArrayList<>();
                    locationSet.add(caveLoc);
                    setLocationSet(locationSet, cave.getCaveEntrance());
                    locationSet.add(caveLoc);
                    dragonCharacter.setLocationSet(locationSet);
                    dragonCharacter.setLocationIndex(0);
                    break;
                }
            }
        }
    }

    private void setLocationSet(ArrayList<Location> locationSet, Location caveEntrance){
        int index = boardLocations.indexOf(caveEntrance);
        for(int i=0; i<=boardLocations.size(); i++){
            locationSet.add(boardLocations.get(index));
            index++;
            index = checkBoardLocationIndexing(index);
        }

    }

    /**
     * checks if the player has chosen a matching card for their current location
     * @param chitCard chit card being checked
     * @return
     */
    public boolean checkChitCardMatches(DragonCharacter dragonCharacter, ChitCard chitCard){
        Location currLocation = dragonLocation.getLocation(dragonCharacter);
        if (caveLocation.hasCave(currLocation)){
            Cave cave = caveLocation.getCave(currLocation);
            if(chitCard.getActor().getName() == animalLocation.getActorAt(cave.getCaveEntrance()).getName()){
                return true;
            }
        } else if (chitCard.getActor().getName() == animalLocation.getActorAt(currLocation).getName()) {
            return true;
        }
        return false;
    }


}
