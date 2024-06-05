package Utilities;

import Game.Game;
import Gameboard.GameBoard;

import java.io.Serializable;

/**
 * Class responsible for storing the data into a format able to be saved and loaded.
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */
public class SaveData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Game game;

    public Game getGame(){
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
