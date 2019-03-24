/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import java.io.Serializable;
import java.util.TreeSet;
import supercheckers.game.Player;

/**
 *
 * @author Hamza Ali
 */
public class DataModel implements Serializable {
    
    /**
     * holds player rankings to be saved to file
     */
    private TreeSet<Player> players;

    /**
     * creates a new data model to save player rankings to file; makes sure that 
     * the kill moves array is cleared beforehand to prevent serializable errors.
     * @param players rankings TreeSet to be saved
     */
    public DataModel(TreeSet<Player> players) {
        for (Player p : players)
            p.clearKillMoves();
        this.players = players;
    }

    /**
     * @return the rankings TreeSet saved in the data model
     */
    public TreeSet<Player> getPlayers() {
        return players;
    }

    /**
     * sets the value of the TreeSet this.players; also makes sure that the 
     * kill moves array is cleared beforehand to prevent serializable errors.
     * @param players rankings TreeSet to be saved
     */
    public void setPlayers(TreeSet<Player> players) {
        for (Player p : players)
            p.clearKillMoves();
        this.players = players;
    }
}
