/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc;

import gui.GameWindow;
import gui.MainMenu;
import supercheckers.game.Game;
import supercheckers.game.HumanPlayer;
import supercheckers.game.Player;

/**
 *
 * @author Hamza Ali
 */
public class SuperCheckers {
    
    /**
     * holds info on the two players playing a match
     */
    public static Player p1 = null;
    public static Player p2 = null;
    
    /**
     * main application logic
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        Player winner;
        MainMenu menu = new MainMenu();
        menu.setVisible(true);
        while(true) {
            while (p1 == null || p2 == null) {
                Thread.sleep(50);
            }

            Game game = new Game(p1, p2);

            GameWindow window = new GameWindow(game, menu);
            winner = window.play();
            Thread.sleep(3000);
            window.dispose();
            
            if (p1.getClass() == HumanPlayer.class && p2.getClass() == HumanPlayer.class) {
                if (winner.equals(p1)) {
                    p1.calcPoints(game, true);
                    p2.calcPoints(game, false);
                } else {
                    p1.calcPoints(game, false);
                    p2.calcPoints(game, true);
                }
            }
            
            p1 = null;
            p2 = null;
            menu.setVisible(true);
        }
    }
}