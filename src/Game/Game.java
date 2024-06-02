package Game;

import Actors.DragonCharacter;
import Actors.PirateSkeleton;
import Actors.Snake;
import Gameboard.GameBoard;
import Utilities.ASCIIDisplayMessage;
import Utilities.Engine;

import java.util.ArrayList;

/**
 * A class representing the Fiery Dragon game. Contains the players in the game, the game board and is responsible for
 * running the fiery dragon game and communicating between player and the game.
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.1
 */
public class Game {
    /**
     * The game board used in this instance of the Fiery Dragon game
     */
    private GameBoard gameBoard;

    /**
     * A list of all the chit cards in the game
     */
    private ArrayList<ChitCard> chitCards;

    /**
     * A flag to see if the game should be stopped (if a player has achieved victory)
     */
    private Boolean stillRunning;

    /**
     * A flag to see if a player's turn is over (if a player chose a non-matching card or moved backwards)
     */
    boolean turnFinished = false;

    /**
     * A list of all the players in the game
     */
    private ArrayList<Player> players;

    /**
     * The number of teh player who wins
     */
    private String winningPlayerName;

    /**
     * A string to display when a player's turn is over
     */
    private static final String TURN_OVER = "Your turn is now over!\n";

    /**
     * Constructor for game
     *
     * @param gameBoard The game board used in this instance of the Fiery Dragon game
     */
    public Game(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

        ASCIIDisplayMessage.display(ASCIIDisplayMessage.FIERY_DRAGONS);

        chitCards = Engine.getInstance().generateChitCards();
        players = Engine.getInstance().insertPlayers(gameBoard);

        ASCIIDisplayMessage.display(ASCIIDisplayMessage.START_GAME);
        stillRunning = true;
    }

    /**
     * On each iteration of the while loop, this method will display the current state of the game board, take in the
     * input from the player whose turn it is to go, and finally enact the effects the players move will have on
     * the state of the game. The majority of the game will run in this loop.
     */
    public void run() {
        Menu menu = new Menu(gameBoard, chitCards);

        while (stillRunning) {

            for (int i = 0; i < players.size(); i++) {
                DragonCharacter dragonCharacter = players.get(i).getDragonCharacter();
                System.out.println("========== Player " + dragonCharacter.getName() + "'s Turn ==========");
                while (!turnFinished) {
                    menu.display(dragonCharacter);
                    int userInput = Engine.getInstance().getValidIntegerInput("Please Enter Your Move: ", 0, 17);
                    processTurn(players.get(i), userInput, menu);
                }
                for (int j = 0; j < chitCards.size(); j++) {
                    chitCards.get(j).resetCard();
                }
                turnFinished = false;
                if (!stillRunning) {
                    break;
                }
            }
        }
        menu.displayVictoryScreen(winningPlayerName, gameBoard);
    }

    /**
     * Checks to see if the players pick corresponds to a chit card resulting in a movement of their dragon character,
     * if so, moves their dragon character.
     *
     * @param player    The player whose move it is
     * @param userInput The chit card that the player chooses to flip
     */
    private void processTurn(Player player, int userInput, Menu menu) {
        ChitCard currCard = chitCards.get(userInput - 1);

        // If the player tries to flip a card that has already been flipped this turn, ask them to choose again
        while (!currCard.revealCard()) {
            System.out.println("This card has already been flipped this turn.");
            userInput = Engine.getInstance().getValidIntegerInput("Please Enter Your Move: ", 0, 16);
            currCard = chitCards.get(userInput - 1);
        }

        // If the player finds a pirate skeleton, move the player backwards
        if (currCard.getActor().getClass().equals(PirateSkeleton.class)) {
            System.out.println("You will be punished. You will move backwards " + currCard.quantity + " spaces.\n");
            gameBoard.moveDragonCharacter(player, currCard.quantity * -1);
            System.out.println(TURN_OVER);
            turnFinished = true;
        } else if (currCard.getActor().getClass().equals(Snake.class)) {
            System.out.println("Ouch! You have been bitten by a snake! You will now fall back to a cave to recover.\n");
            gameBoard.returnToCave(player);
        } else {
            // If the player finds a match, move the player forward
            if (gameBoard.checkChitCardMatches(player.getDragonCharacter(), currCard)) {
                System.out.println("You found a match! You will now move forward " + currCard.quantity + " spaces.\n");
                gameBoard.moveDragonCharacter(player, currCard.quantity);
                if (!gameBoard.isMovedThisTurn()) {
                    System.out.println(TURN_OVER);
                    turnFinished = true;
                }
                // If the player finds a non-matching card, end their turn
            } else {
                System.out.println("You did not find a match. You will NOT move.\n");
                System.out.println(TURN_OVER);
                turnFinished = true;
            }
        }

        gameBoard.allPLayersLeaderboardRanking(players);

        if (gameBoard.getAchievedVictory()) {
            turnFinished = true;
            stillRunning = false;
            winningPlayerName = gameBoard.getWinningPlayerName();
        }
    }
}
