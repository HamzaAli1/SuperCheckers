/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercheckers.game;

import gui.GamePanel;
import java.util.ArrayList;

/**
 *
 * @author Hamza Ali
 */
public final class ComputerPlayer extends Player {
    
    private final int rank = 10;
    
    public ComputerPlayer(String n) {
        super(n + "_COM");
        calcPoints(null, false);
    }
    
    @Override
    void move(Game g) {
        System.out.println("Computer is making its move...");
        
        Piece p;
        String moveL, moveR, moveL2, moveR2;
        
        killMoves(g);
        
        if (!kills.isEmpty()) {
            p = kills.get((int) (kills.size()*Math.random()));
            moveL = "" + (p.getRow()-2) + "," + (p.getColumn()-2);
            moveR = "" + (p.getRow()-2) + "," + (p.getColumn()+2);
            moveL2 = "" + (p.getRow()+2) + "," + (p.getColumn()-2);
            moveR2 = "" + (p.getRow()+2) + "," + (p.getColumn()+2);

            if (validMove(g, moveL.split("[,]"), p))
                p.move(moveL.split("[,]"), g);
            else if (validMove(g, moveR.split("[,]"), p))
                p.move(moveR.split("[,]"), g);
            else if (validMove(g, moveL2.split("[,]"), p))
                p.move(moveL2.split("[,]"), g);
            else
                p.move(moveR2.split("[,]"), g);
        }
        else {
            p = getRandPiece(g);
            moveL = "" + (p.getRow()-1) + "," + (p.getColumn()-1);
            moveR = "" + (p.getRow()-1) + "," + (p.getColumn()+1);
            moveL2 = "" + (p.getRow()+1) + "," + (p.getColumn()-1);
            moveR2 = "" + (p.getRow()+1) + "," + (p.getColumn()+1);
            if (validMove(g, moveL.split("[,]"), p))
                p.move(moveL.split("[,]"), g);
            else if (validMove(g, moveR.split("[,]"), p))
                p.move(moveR.split("[,]"), g);
            else if (validMove(g, moveL2.split("[,]"), p))
                p.move(moveL2.split("[,]"), g);
            else if (validMove(g, moveR2.split("[,]"), p))
                p.move(moveR2.split("[,]"), g);
        }
        
        clearKillMoves();
        
        System.out.println("Computer has made a move.");
    }
    
    private Piece getRandPiece(Game g) {
        ArrayList<Piece> iterator, temp = new ArrayList<>();
        if (getColor().equals("red"))
            iterator = g.getBoard().getReds();
        else
            iterator = g.getBoard().getBlacks();
        for (Piece piece : iterator) {
            if (pieceCanMove(g, piece, getColor()))
                temp.add(piece);
        }
        return temp.get((int)(temp.size() * Math.random()));
    }
    
    /*
    for (every piece computer has)
        if (any of its moves can kill a piece)
            make the move that kills the most
            return
    make a move that moves a piece closest to the other side of the board || make a random move
    */

    @Override
    void calcPoints(Game g, boolean win) {
        setPoints(rank);
    }

    @Override
    void move(Game g, GamePanel panel) throws InterruptedException {
        panel.setGameOutput("Computer is making its move...");
        
        Piece p;
        String moveL, moveR, moveL2, moveR2;
        
        killMoves(g);
        
        if (!kills.isEmpty()) {
            p = kills.get((int) (kills.size()*Math.random()));
            moveL = "" + (p.getRow()-2) + "," + (p.getColumn()-2);
            moveR = "" + (p.getRow()-2) + "," + (p.getColumn()+2);
            moveL2 = "" + (p.getRow()+2) + "," + (p.getColumn()-2);
            moveR2 = "" + (p.getRow()+2) + "," + (p.getColumn()+2);

            if (validMove(g, moveL.split("[,]"), p))
                p.move(moveL.split("[,]"), g);
            else if (validMove(g, moveR.split("[,]"), p))
                p.move(moveR.split("[,]"), g);
            else if (validMove(g, moveL2.split("[,]"), p))
                p.move(moveL2.split("[,]"), g);
            else
                p.move(moveR2.split("[,]"), g);
        }
        else {
            p = getRandPiece(g);
            moveL = "" + (p.getRow()-1) + "," + (p.getColumn()-1);
            moveR = "" + (p.getRow()-1) + "," + (p.getColumn()+1);
            moveL2 = "" + (p.getRow()+1) + "," + (p.getColumn()-1);
            moveR2 = "" + (p.getRow()+1) + "," + (p.getColumn()+1);
            if (validMove(g, moveL.split("[,]"), p))
                p.move(moveL.split("[,]"), g);
            else if (validMove(g, moveR.split("[,]"), p))
                p.move(moveR.split("[,]"), g);
            else if (validMove(g, moveL2.split("[,]"), p))
                p.move(moveL2.split("[,]"), g);
            else if (validMove(g, moveR2.split("[,]"), p))
                p.move(moveR2.split("[,]"), g);
        }
        
        clearKillMoves();
        
        panel.setGameOutput("Computer has made a move.");
        Thread.sleep(100);
    }
}
