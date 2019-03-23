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
    
    private final Board board;
    
    //one is red, two is black; this fact while be made obvious in the GUI
    private final Player one;
    private final Player two;

    public Game() {
        one = new HumanPlayer("Test Player", ""); //TODO maybe change this to a computer player later on to have some fun :)
        two = new ComputerPlayer(ComputerPlayer.EASY);
        one.setColor("red");
        two.setColor("black");
        
        board = new Board();

    }

    public Game(Player one, Player two) {
        this.one = one;
        this.two = two;
        one.setColor("red");
        two.setColor("black");
        
        board = new Board();
    }
    
    //starts game, returns winner
    public Player play() {
        System.out.println("Starting Game...\n");
        
        System.out.println(one.getName() + " is " + one.getColor());
        System.out.println(two.getName() + " is " + two.getColor() + "\n");
        
        double rand = Math.random();
        if (rand < 0.5) { //TODO: make this random turn order part more efficient (seems redundant)
            System.out.println(one.getName() + " Starts!");
            while (canStillPlay()) {
                System.out.println("\n" + getBoard());
                if (board.getTurn() % 2 == 0)
                    one.move(this);
                else
                    two.move(this);
                board.turnUp();
            }
        } else {
            System.out.println(two.getName() + " Starts!");
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
    
    //connects with front end gui instead of printing
    public Player play(GamePanel panel) throws InterruptedException {
        panel.setGameOutput("Starting Game...");
        Thread.sleep(2000);
        
        panel.setGameOutput(one.getName() + " is " + one.getColor() + ", and " + two.getName() + " is " + two.getColor());
        Thread.sleep(3500);
        
        double rand = Math.random();
        if (rand < 0.5) {
            panel.setGameOutput(one.getName() + " starts!");
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
            panel.setGameOutput(two.getName() + " starts!");
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
        panel.repaint();
        if (board.getReds().isEmpty() || !one.canMove(this)) {
            panel.setGameOutput("Black Wins!");
            return two;
        }
        else if (board.getBlacks().isEmpty() || !two.canMove(this)) {
            panel.setGameOutput("Red Wins!");
            return one;
        }
        else {
            panel.setGameOutput("Tie!");
            return null;
        }
    }
    
    public boolean canStillPlay() {
        if (board.getTurn() < 100) {
            if (board.getReds().size() > 0 && board.getBlacks().size() > 0) {
                if (one.canMove(this) && two.canMove(this))
                    return true;
            }
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    public Player getOne() {
        return one;
    }

    public Player getTwo() {
        return two;
    }
    
    //nonGUI game for debug
    public static void main(String[] args) {
        Player one = new HumanPlayer("test1", ""), two = new HumanPlayer("test2", "");
        Game test = new Game(one, two);
        test.play();
    }
}