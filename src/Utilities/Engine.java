package Utilities;

import Actors.*;
import Game.ChitCard;
import Game.Player;
import Gameboard.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A class responsible for setting up all elements of the game at the beginning of the game
 *
 * @author Max Zhuang, Brandon Luu, Jeremy Ockerby, Raymond Li
 * @version 1.0.1
 */

public class Engine {

    /**
     * single instance of the Engine class
     */
    private static Engine instance = null;

    /**
     * constructor for engine
     */
    private Engine() {
    }

    /**
     * gets the instance of this class
     *
     * @return single instance of the Engine class
     */
    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    /**
     * Initial generation of game board and its components. Creates volcano cards, then the tiles in teh volcano cards,
     * then the caves if they should have one and lastly sets up the animals in each tile
     *
     * @param lines String representation of the look of the board
     * @return the game board for the game
     */
    public GameBoard generateGameBoard(List<String> lines) {

        int boardSize = 2; // how many volcano cards on each side of the board, for sprint 2, 8 cards, so board size = 2
        ArrayList<VolcanoCard> volcanoCards = generateVolcanoCards(boardSize);
        ArrayList<VolcanoCard> volcanoCardsWithCave = new ArrayList<>();
        ArrayList<VolcanoCard> volcanoCardsNoCave = new ArrayList<>();
        for (int i = 0; i < volcanoCards.size(); i++) {
            if (volcanoCards.get(i).hasCave()) {
                volcanoCardsWithCave.add(volcanoCards.get(i));
            } else {
                volcanoCardsNoCave.add(volcanoCards.get(i));
            }
        }
        ArrayList<ArrayList<Animal>> cavedAnimals = VolcanoCardConfiguration.getInstance().getCavedConfiguration();
        ArrayList<ArrayList<Animal>> nonCavedAnimals = VolcanoCardConfiguration.getInstance().getNonCavedConfiguration();
        assignTiles(volcanoCardsWithCave, cavedAnimals);
        assignTiles(volcanoCardsNoCave, nonCavedAnimals);
        randomiseAnimals(volcanoCardsWithCave);
        randomiseAnimals(volcanoCardsNoCave);

        GameBoard gameBoard = new GameBoard(volcanoCards, lines);

        return gameBoard;
    }

    /**
     * Creates the 8 volcano cards for the game board
     *
     * @param boardSize number of volcano cards on each side of the board
     * @return list of the 8 volcano cards
     */
    private ArrayList<VolcanoCard> generateVolcanoCards(int boardSize) {
        int numOfCards = boardSize * 4;
        ArrayList<VolcanoCard> volcanoCards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            if (i < boardSize) {
                VolcanoCard volcanoCard = new TopVolcanoCard();
                if (i == 0) {
                    volcanoCard.setCave(new Cave());
                }
                volcanoCards.add(volcanoCard);
            } else if (i < 2 * boardSize) {
                VolcanoCard volcanoCard = new RightVolcanoCard();
                if (i == 1 * boardSize) {
                    volcanoCard.setCave(new Cave());
                }
                volcanoCards.add(volcanoCard);
            } else if (i < 3 * boardSize) {
                VolcanoCard volcanoCard = new BottomVolcanoCard();
                if (i == 2 * boardSize) {
                    volcanoCard.setCave(new Cave());
                }
                volcanoCards.add(volcanoCard);
            } else {
                VolcanoCard volcanoCard = new LeftVolcanoCard();
                if (i == 3 * boardSize) {
                    volcanoCard.setCave(new Cave());
                }
                volcanoCards.add(volcanoCard);
            }
        }
        return volcanoCards;
    }

    /**
     * randomises the order of the animals of the volcano card, 3 if no cave, 2 if cave as the middle tile of caved
     * volcano cards cannot be changed
     *
     * @param volcanoCards list of volcano cards to randomise
     * @return list of volcano cards with randomised animals
     */
    private ArrayList<VolcanoCard> randomiseAnimals(ArrayList<VolcanoCard> volcanoCards) {
        for (int i = 0; i < volcanoCards.size(); i++) {
            VolcanoCard volcanoCard = volcanoCards.get(i);
            if (volcanoCard.hasCave()) {
                int randomNumber = new Random().nextInt(100);
                if (randomNumber > 50) {
                    Tile tile1 = volcanoCard.getAnimalTiles().get(0);
                    Tile tile3 = volcanoCard.getAnimalTiles().get(2);
                    Animal animal1 = tile1.getAnimal();
                    Animal animal3 = tile3.getAnimal();
                    tile3.setAnimal(animal1);
                    tile1.setAnimal(animal3);
                }
            } else {
                Collections.shuffle(volcanoCard.getAnimalTiles());
            }
        }
        return volcanoCards;
    }

    /**
     * Assigns the animals onto each tile of the volcano cards
     *
     * @param volcanoCards list of volcano cards
     * @param animals      animals that can be on this type of volcano card 9(caved or non caved)
     */
    private void assignTiles(ArrayList<VolcanoCard> volcanoCards, ArrayList<ArrayList<Animal>> animals) {
        Random random = new Random();
        for (int i = 0; i < volcanoCards.size(); i++) {
            VolcanoCard volcanoCard = volcanoCards.get(i);
            int indexNumber = random.nextInt(animals.size());
            ArrayList<Animal> tileAnimals = animals.get(indexNumber);
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                Animal animal = tileAnimals.get(j);
                tile.setAnimal(animal);
                volcanoCard.addTile(tile);
            }
            animals.remove(indexNumber);
        }
    }

    /**
     * initial created of the 16 chit cards in the game
     *
     * @return list of the 16 chit cards
     */
    public ArrayList<ChitCard> generateChitCards() {
//        ArrayList<ChitCard> chitCards = new ArrayList<>();
//        for (int i = 0; i < 3; i++){
//            ChitCard chitCard = new ChitCard(new Salamander(), i+1);
//            chitCards.add(chitCard);
//            chitCard = new ChitCard(new Bat(), i+1);
//            chitCards.add(chitCard);
//            chitCard = new ChitCard(new Spider(), i+1);
//            chitCards.add(chitCard);
//            chitCard = new ChitCard(new BabyDragon(), i+1);
//            chitCards.add(chitCard);
//        }
//
//        for (int i = 0; i < 2; i++){
//            ChitCard chitCard = new ChitCard(new PirateSkeleton(), 1);
//            chitCards.add(chitCard);
//        }
//        for (int i = 0; i < 2; i++){
//            ChitCard chitCard = new ChitCard(new PirateSkeleton(), 2);
//            chitCards.add(chitCard);
//        }

        // optimised implementation
        ArrayList<ChitCard> chitCards = new ArrayList<>();
        ArrayList<Animal> animals = new ArrayList<>(Arrays.asList(new Salamander(), new Bat(), new Spider(), new BabyDragon()));
        for (int i = 0; i < 3; i++) {
            for (Animal animal : animals) {
                ChitCard chitCard = new ChitCard(animal, i + 1);
                chitCards.add(chitCard);
            }
        }
        for (int i = 0; i < 2; i++) {
            ChitCard chitCard = new ChitCard(new PirateSkeleton(), 1);
            chitCards.add(chitCard);
        }
        for (int i = 0; i < 2; i++) {
            ChitCard chitCard = new ChitCard(new PirateSkeleton(), 2);
            chitCards.add(chitCard);
        }

        Collections.shuffle(chitCards);
        return chitCards;
    }

    /**
     * Initialises the players in the game, and sets up the dragon tokens for eac player and puts them in their caves
     *
     * @param gameBoard the game board played on
     * @return List of players in the game
     */
    public ArrayList<Player> insertPlayers(GameBoard gameBoard) {
        ArrayList players = new ArrayList();
        ArrayList charList = new ArrayList();
        int numOfPlayers;
        numOfPlayers = getValidIntegerInput("Enter Number of Players: ", 2, 4);
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println("==========New Player Registration==========");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Player Name: ");
            String name = scanner.nextLine();

            char displayChar = getValidCharacterInput("Enter Player Character (1 characters): ", charList);
            DragonCharacter dragonCharacter = new DragonCharacter(name, displayChar, i + 1);

            int age = getValidIntegerInput("Enter Player Age: ", 0, 122);
            Player player = new Player(dragonCharacter, age);
            players.add(player);
            gameBoard.insertDragonCharacter(dragonCharacter);
        }

        sortPlayerOrder(players);
        return players;
    }

    /**
     * Asks the player a prompt until a valid integer input has been entered which falls between the upper and lower bound
     *
     * @param prompt     string that is asked until a valid input is made
     * @param lowerBound lower bound integer value
     * @param upperBound lower bound integer value
     * @return valid integer input value
     */
    public int getValidIntegerInput(String prompt, int lowerBound, int upperBound) {
//        Scanner scanner = new Scanner(System.in);
//        int userInput;
//        while (true) {
//            System.out.println(prompt);
//            while (!scanner.hasNextInt()) {
//                System.out.println("Invalid input. Please enter an integer.");
//                System.out.println(prompt);
//                scanner.next();
//            }
//            userInput = scanner.nextInt();
//            if (userInput >= lowerBound && userInput <= upperBound) {
//                return userInput;
//            } else {
//                System.out.printf("Please enter a number between %d and %d.%n", lowerBound, upperBound);
//            }
//        }

        // optimised implementation with try-catch
        Scanner scanner = new Scanner(System.in);
        int userInput;
        while (true) {
            System.out.println(prompt);
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                if (userInput >= lowerBound && userInput <= upperBound) {
                    return userInput;
                } else {
                    System.out.printf("Please enter a number between %d and %d.%n", lowerBound, upperBound);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    /**
     * Asks the player a prompt until a valid character input which has not already been entered previously is made
     *
     * @param prompt   string that is asked until a valid input is made
     * @param charList list of previously made character inputs
     * @return valid character input value
     */
    private char getValidCharacterInput(String prompt, ArrayList charList) {
        Scanner scanner = new Scanner(System.in);
        char charOutput;
        while (true) {
            System.out.println(prompt);
            String userInput = scanner.nextLine();

            if (userInput.length() == 1) {
                charOutput = userInput.charAt(0);
                if (charList.contains(charOutput)) {
                    System.out.println("Invalid input. This character has already been chosen");
                } else {
                    charList.add(charOutput);
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter exactly one character.");
            }
        }
        return charOutput;
    }

    /**
     * Sorts the player list by age
     *
     * @param playerList list of player instances that have been created
     */
    public static void sortPlayerOrder(ArrayList<Player> playerList) {
//        int n = playerList.size();
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = 0; j < n - i - 1; j++) {
//                if (playerList.get(j).getAge() > playerList.get(j + 1).getAge()) {
//                    Player temp = playerList.get(j);
//                    playerList.set(j, playerList.get(j + 1));
//                    playerList.set(j + 1, temp);
//                }
//            }
//        }

        // optimised implementation
        playerList.sort(Comparator.comparingInt(Player::getAge));
    }


}
