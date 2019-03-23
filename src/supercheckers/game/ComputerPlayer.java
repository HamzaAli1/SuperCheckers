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
public final class ComputerPlayer extends Player {
        
    public static int EASY = 5;
    public static int HARD = 10; //??? maybe
    private final int rank;
    
    public ComputerPlayer(int dif) {
        super("Computer");
        rank = dif;
        calcPoints(null, false);
    }
    
    @Override
    void move(Game g) {
        System.out.println("Computer is making its move...");
        
        if (rank == EASY) {
            easy(g);
        } else /*HARD*/ {
            hard(g);
        }
        
        System.out.println("Computer has made a move.");
    }

    //make kill moves, else choose random
    private void easy(Game g) {
        Piece temp = null;
        boolean loop = true;
        String move1, move2, move1d, move2d;
        int r, c;
        
        killMoves(g);
        
        if (getColor().equals("red")) {
            if (!kills.isEmpty()) {
                temp = kills.get((int) (kills.size()*Math.random()));
                r = temp.getRow(); c = temp.getColumn();
                if (temp.getType().equals("double")) {
                    move1 = "" + (r-2) + "," + (c-2);
                    move2 = "" + (r-2) + "," + (c+2);
                    move1d = "" + (r+2) + "," + (c-2);
                    move2d = "" + (r+2) + "," + (c+2);
                } else {
                    move1 = "" + (r-2) + "," + (c-2);
                    move2 = "" + (r-2) + "," + (c+2);
                    //TODO.... :||||||
                }
            } else {
                while (loop) {
                    temp = g.getBoard().getReds().get((int) (g.getBoard().getReds().size()*Math.random()));
                    loop = pieceCanMove(g, temp, getColor());
                }
                move1 = "" + (temp.getRow()-2) + "," + (temp.getColumn()-2);
                move2 = "" + (temp.getRow()-2) + "," + (temp.getColumn()+2);
                if (validMove(g, move1.split("[,]"), temp))
                    temp.move(move1.split("[,]"), g);
                else
                    temp.move(move2.split("[,]"), g);
            }
        }
    }
    
    private void hard(Game g) {
        if (getColor().equals("red")) {
             /*
            for (every piece computer has)
                if (any of its moves can kill a piece)
                    make the move that kills the most
                    return
            make a move that moves a piece closest to the other side of the board || make a random move
            */
        } else {
            
        }
    }

    @Override
    void calcPoints(Game g, boolean win) { //change this later?!?
        setPoints(rank);
    }

    @Override
    void move(Game g, GamePanel panel) {
        //TODO
    }
}
