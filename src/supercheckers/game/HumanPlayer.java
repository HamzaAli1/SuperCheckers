/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercheckers.game;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Hamza Ali
 */
public class HumanPlayer extends Player {

    private Scanner chop;
    private String password;

    public HumanPlayer(String n, String p) {
        super(n);
        password = p;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
    Method that actually makes player's move. First asks for a piece to move, 
    then asks for a postion to move the piece to. Multiple "hops" are possible 
    by inputting multiple r,c pairs seperated by spaces. After both are inputted,
    move is validated before being executed.
     */
    @Override
    void move(Game g) {
        killMoves(g);
        chop = new Scanner(System.in);
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
            chop.nextLine();//TODO: why does this mess up when killMoves() is invoked?
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

    @Override
    void calcRank(Game g) {
        int newRank = getRank();
        //TODO: original rank + win/loss/tie + turnCount + opponentsRank + ...
        setRank(newRank);
    }
}
