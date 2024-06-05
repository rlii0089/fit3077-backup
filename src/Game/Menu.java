package Game;

import Actors.DragonCharacter;
import Gameboard.GameBoard;
import Utilities.ASCIIDisplayMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class used for printing messages for the user to see
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.2
 */

public class Menu{
    /**
     * game board used in the game
     */
    private final GameBoard gameBoard;

    /**
     * A list of the chit cards in the game
     */
    private final ArrayList<ChitCard> chitCards;

    /**
     * scanner to take in user input
     */
    private final Scanner scanner;

    /**
     * constructor for menu
     *
     * @param gameBoard game board used in the game
     * @param chitCards A list of the chit cards in the game
     */
    public Menu(GameBoard gameBoard, ArrayList<ChitCard> chitCards) {
        this.gameBoard = gameBoard;
        this.chitCards = chitCards;
        this.scanner = new Scanner(System.in);
    }

    /**
     * displays current state of game board, the list of chit cards and message to take in player input
     */
    public void display(DragonCharacter dragonCharacter) {
        gameBoard.draw();
        printEmptyLine();
        gameBoard.printCurrentLocation(dragonCharacter);
        printEmptyLine();
        displayChitCards();


    }

    /**
     * displays end screen
     *
     * @param winningPlayerName the name of the player that wins
     */
    public void displayVictoryScreen(String winningPlayerName, GameBoard gameBoard) {
        gameBoard.draw();
        System.out.println("Congratulations!!!!\n" + "Player " + winningPlayerName + " has won the game!\n");
        ASCIIDisplayMessage.display(ASCIIDisplayMessage.YOU_WIN);
    }

    /**
     * displays all chit cards
     */
    private void displayChitCards() {
        for (int i = 0; i < chitCards.size(); i++) {
            String retVal = String.valueOf(i + 1);
//            if (chitCards.get(i).revealed == true){
            retVal += ". " + chitCards.get(i).getActor().getName() + " x" + chitCards.get(i).getQuantity();
//            }
//            else{
//                retVal += ". Unknown";
  //          }

            System.out.println(retVal);
        }
        String saveGame = String.valueOf(chitCards.size() + 1) + ". Save Game";
        String exitGame = String.valueOf(chitCards.size() + 2) + ". Exit Game";
        System.out.println(saveGame);
        System.out.println(exitGame);
    }

    /**
     * Prints empty line
     */
    private void printEmptyLine() {
        System.out.println();
    }

}
