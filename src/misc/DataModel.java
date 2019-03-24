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
    private TreeSet<Player> players;

    public DataModel(TreeSet<Player> players) {
        for (Player p : players)
            p.clearKillMoves();
        this.players = players;
    }

    public TreeSet<Player> getPlayers() {
        return players;
    }

    public void setPlayers(TreeSet<Player> players) {
        for (Player p : players)
            p.clearKillMoves();
        this.players = players;
    }
}
