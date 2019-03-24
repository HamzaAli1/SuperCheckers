/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import supercheckers.game.Game;
import supercheckers.game.Player;

/**
 *
 * @author Hamza Ali
 */
public class GameWindow extends JFrame {
    
    /**
     * GamePanel to be added to JFrame
     */
    private final GamePanel panel;
    
    /**
     * temp variable used to set main menu visible should the window close prematurely
     */
    private final MainMenu menu;
    
    /**
     * main constructor, creates a new GameWindow
     * @param g Game to be played
     * @param m MainMenu window is being accessed from
     */
    public GameWindow(Game g, MainMenu m) {
        panel = new GamePanel(g);
        menu = m;
        setUp();
    }
    
    /**
     * sets up window dimensions, listeners, etc.
     */
    private void setUp() {
        add(panel);
        
        Dimension dms = new Dimension(800, 940); //board is only 800, 840
        setSize(dms);
        setMinimumSize(dms);
        setResizable(false);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                menu.setVisible(true);
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }
    
    /**
     * plays game, returns winner
     * @return the Player that won
     * @throws InterruptedException 
     */
    public Player play() throws InterruptedException {
        return panel.play();
    }
    
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        GameWindow window = new GameWindow(game, null);
        
        window.play();
        
        Thread.sleep(2500);
        
        window.dispose();
    }
}
