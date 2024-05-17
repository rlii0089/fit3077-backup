import Game.Game;
import Gameboard.GameBoard;
import Utilities.Engine;

import java.util.Arrays;
import java.util.List;

/**
 * main class to start the game
 */
public class Application {
    public static void main(String[] args) {
        List<String> board = Arrays.asList(
                "                                                ",
                "        _     _     _     _     _     _         ",
                "       | |   | |   | |   | |   | |   | |        ",
                "        —     —     —     —     —     —         ",
                "                                                ",
                "  _                                         _   ",
                " | |                                       | |  ",
                "  —                                         —   ",
                "  _                                         _   ",
                " | |                                       | |  ",
                "  —                                         —   ",
                "  _                                         _   ",
                " | |                                       | |  ",
                "  —                                         —   ",
                "  _                                         _   ",
                " | |                                       | |  ",
                "  —                                         —   ",
                "  _                                         _   ",
                " | |                                       | |  ",
                "  —                                         —   ",
                "  _                                         _   ",
                " | |                                       | |  ",
                "  —                                         —   ",
                "                                                ",
                "        _     _     _     _     _     _         ",
                "       | |   | |   | |   | |   | |   | |        ",
                "        —     —     —     —     —     —         ",
                "                                                ");

        GameBoard gameBoard = Engine.getInstance().generateGameBoard(board);
        Game game = new Game(gameBoard);
        game.run();
    }
}
