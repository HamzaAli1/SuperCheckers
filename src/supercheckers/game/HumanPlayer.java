/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercheckers.game;

import gui.GamePanel;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Hamza Ali
 */
public class HumanPlayer extends Player {

    /**
     * password used to login as player
     */
    private String password;

    /**
     * main constructor, creates a new HumanPlayer
     * @param n name
     * @param p password
     */
    public HumanPlayer(String n, String p) {
        super(n);
        password = p;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method that actually makes player's move. First asks for a piece to move, 
     * then asks for a position to move the piece to. Multiple "hops" are possible 
     * by inputting multiple r,c pairs separated by spaces. After both are inputted,
     * move is validated before being executed.
     * @param g instance of the Game
     */
    @Override
    void move(Game g) {
        killMoves(g);
        Scanner chop = new Scanner(System.in);
        boolean loop = true, confirmed = false;
        String input = "";
        Piece selected = null;
        String[] moves = null;
        
        while (!confirmed) {
            //select piece
            System.out.println(getName() + ", select a piece...");
            while (loop) {
                try {
                    input = chop.next("[0-7],[0-7]");
                    selected = validPiece(g, input);
                    loop = selected == null || !pieceCanMove(g, selected, getColor());
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid piece...");
                    chop.next();
                }
            }
            chop.nextLine();
            //select new position (Note: the inputted moves MUST be in order. This will be accounted for in GUI)
            loop = true;
            System.out.println(getName() + ", select where to move your piece...");
            while (loop) {
                input = chop.nextLine();
                if (input.matches("([0-8],[0-8] ?)+")) {
                    moves = input.split("[ ,]");
                    loop = !validMove(g, moves, selected);
                }
                if (loop)
                        System.out.println("Inputted move not valid, please try again.");
            }
            //confirm move (y/n)
            loop = true;
            while (loop) {
                try {
                    System.out.println("Confirm move? (Y/N)");
                    input = chop.next("[ynYN]{1}?");
                    loop = false;
                } catch (InputMismatchException e) {
                    System.out.println("Please answer the prior question correctly.");
                    chop.next();
                }
            }
            if (input.equals("Y") || input.equals("y")) confirmed = true;
            else {
                loop = true;
                selected = null;
            }
        }
        clearKillMoves();
        if (selected != null)
            selected.move(moves, g);
    }
    
    /**
     * move method used by GUI, replaces scanner input with input from the GUI itself
     * @param g instance of the Game
     * @param panel panel used by the front end
     * @throws InterruptedException 
     */
    @Override
    void move(Game g, GamePanel panel) throws InterruptedException {
        killMoves(g);
        boolean loop = true, confirmed = false;
        String input;
        Piece selected = null;
        String[] moves = null;
        
        while (!confirmed) {
            //select piece
            panel.reset();
            panel.resetSelected();
            panel.resetMove();
            while (loop) {
                while (!panel.buttonPressed()) {
                    panel.enableTouch();
                    if (panel.getPaintPurpose() != 1) {
                        panel.setGameOutput(getName() + ", select a piece...");
                        Thread.sleep(50);
                        panel.setPaintPurpose(1);
                    }
                    Thread.sleep(50);
                }
                panel.disableTouch();
                input = panel.getSelectedPiece();
                selected = validPiece(g, input, panel);
                loop = selected == null || !pieceCanMove(g, selected, getColor());
                if (selected != null && !pieceCanMove(g, selected, getColor()))
                    panel.setGameOutput("Please select a valid piece...");
                if (loop) {
                    panel.resetSelected();
                    Thread.sleep(450);
                }
                Thread.sleep(50);
                panel.reset();
            }
            //select new position & confirm move
            loop = true;
            while (loop) {
                while (!panel.buttonPressed()) {
                    panel.enableTouch();
                    if (panel.getPaintPurpose() != 3) {
                        panel.setGameOutput(getName() + ", select where to move your piece...");
                        Thread.sleep(50);
                        panel.setPaintPurpose(3);
                    }
                    Thread.sleep(50);
                }
                panel.disableTouch();
                if (!panel.confirmed())
                    break;
                else {
                    input = panel.getMove();
                    if (input.matches("([0-8],[0-8] ?)+")) {
                        moves = input.split("[ ,]");
                        loop = !validMove(g, moves, selected);
                    }
                    if (loop) {
                        panel.setGameOutput("Selected move not valid, please try again.");
                        panel.resetMove();
                        Thread.sleep(450);
                    }
                    Thread.sleep(50);
                    panel.reset();
                }
            }
            if (panel.confirmed()) {
                confirmed = true;
            } else {
                loop = true;
                selected = null;
            }
        }
        clearKillMoves();
        if (selected != null)
            selected.move(moves, g);
    }

    /**
     * new rank = original rank + win (100) or loss/tie(10) + 100/turnCount + 100/opponentsRank
     * @param g instance of the Game
     * @param win whether the Player won or not
     */
    @Override
    public void calcPoints(Game g, boolean win) {
        int newRank = getPoints();
        newRank += (100/g.getBoard().getTurn());
        if (getColor().equals("red"))
            newRank += g.getTwo().getPoints()/4;
        else
            newRank += g.getOne().getPoints()/4;
        
        if (win)
            newRank += 100;
        else
            newRank += 10;
        
        setPoints(newRank);
    }
}
