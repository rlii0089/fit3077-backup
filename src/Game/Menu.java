package Game;

import Actors.DragonCharacter;
import Gameboard.GameBoard;
import Utilities.ASCIIDisplayMessage;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class used for printing messages for the user to see
 * @author Max Zhuang, Brandon Luu
 * @version 1.0.1
 */

public class Menu {
    /**
     * game board used in the game
     */
    private GameBoard gameBoard;

    /**
     * A list of the chit cards in the game
     */
    private ArrayList<ChitCard> chitCards;

    /**
     * scanner to take in user input
     */
    private Scanner scanner;

    /**
     * constructor for menu
     * @param gameBoard game board used in the game
     * @param chitCards A list of the chit cards in the game
     */
    public Menu(GameBoard gameBoard, ArrayList<ChitCard> chitCards){
        this.gameBoard = gameBoard;
        this.chitCards = chitCards;
        this.scanner = new Scanner(System.in);
    }

    /**
     * displays initial screen of the game, taking in number of players and birthdays to determine order
     */
    public void displayInitialScreen(){}

    /**
     * displays current state of game board, the list of chit cards and message to take in player input
     */
    public void display(DragonCharacter dragonCharacter){
        gameBoard.draw();
        printEmptyLine();
        gameBoard.printCurrentLocation(dragonCharacter);
        printEmptyLine();
        displayChitCards();
//      displayTemporaryMoveOptions();

    }

    /**
     * displays end screen
     * @param winningPlayerName the name of the player that wins
     */
    public void displayVictoryScreen(String winningPlayerName, GameBoard gameBoard){
        gameBoard.draw();
        System.out.println("Congratulations!!!!");
        System.out.println("Player " + winningPlayerName + " has won the game!");
        ASCIIDisplayMessage.display(ASCIIDisplayMessage.YOU_WIN);
    }

    /**
     * displays all chit cards
     */
    private void displayChitCards(){
        for (int i = 0; i < chitCards.size(); i++){
            String retVal = String.valueOf(i+1);
//            if (chitCards.get(i).revealed == true){
            retVal += ". " + chitCards.get(i).getActor().getName() + " x" +chitCards.get(i).getQuantity();
//            }
//            else{
//                retVal += ". Unknown";
//            }
            System.out.println(retVal);
        }
    }


//    /**
//     * displays temp move options used to demonstrate winning of the game
//     */
//    private void displayTemporaryMoveOptions(){
//        for (int i = chitCards.size()+1; i < chitCards.size() + 4; i++){
//            System.out.print(i);
//            System.out.print(". Move player by  ");
//            System.out.print(i - chitCards.size());
//            System.out.print(" squares");
//            printEmptyLine();
//        }
//
//        for (int i = chitCards.size() + 4; i < chitCards.size() + 6; i++){
//            System.out.print(i);
//            System.out.print(". Move player backwards by  ");
//            System.out.print(i - chitCards.size() - 3);
//            System.out.print(" squares");
//            printEmptyLine();
//        }
//    }

    private void printEmptyLine(){
        System.out.println("");
    }

}
