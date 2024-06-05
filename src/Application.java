import Game.Game;
import Gameboard.GameBoard;
import Utilities.ASCIIDisplayMessage;
import Utilities.Engine;
import Utilities.ResourceManager;
import Utilities.SaveData;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


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

        ASCIIDisplayMessage.display(ASCIIDisplayMessage.FIERY_DRAGONS);
        Scanner scanner = new Scanner(System.in);
        Game game;

        boolean programRunning = true;
        while(programRunning){

            String prompt = "Would you like to Start New Game or Load from Save? (New -> 1, Load -> 2): ";
            int gameStart = Engine.getInstance().getValidIntegerInput(prompt, 1, 2);

            switch (gameStart){
                case 1:
                    System.out.println("\n========== New Game Started ==========");
                    game = new Game(board);
                    game.run();
                    programRunning = false;
                    break;


                case 2:
                    System.out.println("Enter save file name: ");
                    String fileName = scanner.nextLine() + ".save";
                    try{
                        SaveData data = (SaveData) ResourceManager.load(fileName);
                        game = data.getGame();
                        System.out.println("Save data found. Loading " + fileName +  "\n");
                        game.run();
                        programRunning = false;
                    }
                    catch (Exception e){
                        System.out.println("No save data named '" + fileName + "'");
                    }
                    break;
            }
        }

    }
}
