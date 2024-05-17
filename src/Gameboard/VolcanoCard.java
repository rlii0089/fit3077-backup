package Gameboard;

import Game.Location;

import java.util.ArrayList;

public abstract class VolcanoCard {
    protected ArrayList<Tile> animalTiles;
    protected Cave cave;
    protected Location location;

    public VolcanoCard(){
        this.animalTiles = new ArrayList<>();
    }

    public ArrayList<Tile> getAnimalTiles() {
        return animalTiles;
    }

    public Cave getCave() {
        return cave;
    }

    /**
     * Initialises the location of the tiles, and the walkable locations for this volcano card on the game board
     * @param volcanoCardNumber identifier of the volcano card to find the configuration
     * @param board the list of locations in the game board
     */
    public abstract void setLocation(int volcanoCardNumber, GameBoard board);

    protected void addToBoardLocations(GameBoard board, Location location){
        board.addToBoardLocations(location);
    }
    public void setCave(Cave cave) {
        this.cave = cave;
    }

    public void addTile(Tile tile){
        if (this.animalTiles.size() < 3) {
            this.animalTiles.add(tile);
        }
        else{
            System.out.println("error. volcano card tile limit reached");
        }
    }

    public Boolean hasCave(){
        if (cave == null){
            return false;
        }
        return true;
    }


}
