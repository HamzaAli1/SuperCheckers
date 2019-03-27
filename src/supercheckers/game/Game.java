/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercheckers.game;

import gui.GamePanel;

/**
 *
 * @author Hamza Ali
 */
public class Game {
    
    /**
     * instance of the board used by the game
     */
    private final Board board;
    
    /**
     * one is red, two is black; this fact while be made obvious in the GUI
     */
    private final Player one;
    private final Player two;
    
    /**
     * how long a match can go before it has to end
     */
    private final int turnLimit;

    /**
     * default constructor, starts a match between two com players
     */
    public Game() {
        one = new ComputerPlayer("test1", 50);
        two = new ComputerPlayer("test2", 50);
        one.setColor("red");
        two.setColor("black");
        
        turnLimit = 1000;
        
        board = new Board();
    }

    /**
     * main constructor, starts a match between 2 Players
     * @param one player 1
     * @param two player 2
     */
    public Game(Player one, Player two) {
        this.one = one;
        this.two = two;
        one.setColor("red");
        two.setColor("black");
        
        turnLimit = 100;
        
        board = new Board();
    }
    
    /**
     * starts game, returns winner
     * @return the Player that won
     */
    public Player play() {
        System.out.println("Starting Game...\n");
        
        System.out.println(one.getName() + " is " + one.getColor());
        System.out.println(two.getName() + " is " + two.getColor() + "\n");
        
        double rand = Math.random();
        if (rand < 0.5) {
            System.out.println(two.getName() + " Starts!");
            while (canStillPlay()) {
                System.out.println("\n" + getBoard());
                if (board.getTurn() % 2 == 0)
                    one.move(this);
                else
                    two.move(this);
                board.turnUp();
            }
        } else {
            System.out.println(one.getName() + " Starts!");
            while (canStillPlay()) {
                System.out.println("\n" + getBoard());
                if (board.getTurn() % 2 == 0)
                    two.move(this);
                else
                    one.move(this);
                board.turnUp();
            }
        }
        System.out.println("\n\n" + getBoard() + "\n");
        if (board.getReds().isEmpty() || !one.canMove(this)) {
            System.out.println("Black Wins!");
            return two;
        }
        else if (board.getBlacks().isEmpty() || !two.canMove(this)) {
            System.out.println("Red Wins!");
            return one;
        }
        else {
            System.out.println("Tie!");
            return null;
        }
    }
    
    /**
     * plays a match, outputs to the front end GUI
     * @param panel panel used by front end
     * @return the Player that won
     * @throws InterruptedException 
     */
    public Player play(GamePanel panel) throws InterruptedException {
        panel.setGameOutput("Starting Game...");
        Thread.sleep(1000);
        
        panel.setGameOutput(one.getName() + " is " + one.getColor() + ", and " + two.getName() + " is " + two.getColor());
        Thread.sleep(3500);
        
        double rand = Math.random();
        if (rand < 0.5) {
            panel.setGameOutput(two.getName() + " starts!");
            Thread.sleep(2000);
            while (canStillPlay()) {
                panel.repaint();
                if (board.getTurn() % 2 == 0)
                    one.move(this, panel);
                else
                    two.move(this, panel);
                board.turnUp();
            }
        } else {
            panel.setGameOutput(one.getName() + " starts!");
            Thread.sleep(2000);
            while (canStillPlay()) {
                panel.repaint();
                if (board.getTurn() % 2 == 0)
                    two.move(this, panel);
                else
                    one.move(this, panel);
                board.turnUp();
            }
        }
        panel.resetSelected();
        panel.resetMove();
        panel.repaint();
        if (board.getReds().isEmpty() || !one.canMove(this)) {
            panel.setGameOutput(two.getName() + " (Black) Wins!");
            return two;
        }
        else if (board.getBlacks().isEmpty() || !two.canMove(this)) {
            panel.setGameOutput(one.getName() + " (Red) Wins!");
            return one;
        }
        else {
            panel.setGameOutput("Tie!");
            return null;
        }
    }
    
    /**
     * returns whether or not the game should should continue, based on turn count, 
     * number of pieces both players have, and if both players can make moves.
     * @return true if game can continue, false otherwise
     */
    public boolean canStillPlay() {
        if (board.getTurn() < turnLimit) {
            if (board.getReds().size() > 0 && board.getBlacks().size() > 0) {
                if (one.canMove(this) && two.canMove(this))
                    return true;
            }
        }
        return false;
    }

    /**
     * @return the board being used by the Game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return player 1
     */
    public Player getOne() {
        return one;
    }

    /**
     * @return player 2
     */
    public Player getTwo() {
        return two;
    }
    
    /**
     * nonGUI game for debug
     * @param args
     */
    public static void main(String[] args) {
        //human match
        //Player one = new HumanPlayer("test1", ""), two = new HumanPlayer("test2", "");
        //Game test = new Game(one, two);
        
        //com match
        Game test = new Game();
        test.play();
    }
}