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
    
    /**
     * the set point value of the com player
     */
    private final int pointValue = 10;
    
    /**
     * the delay (in milliseconds) the computer must make between moves
     */
    private final int delay;
    
    /**
     * main constructor, creates a new computer player
     * @param n name of the player
     * @param d delay
     */
    public ComputerPlayer(String n, int d) {
        super(n + "_COM");
        calcPoints(null, false);
        delay = d;
    }
    
    /**
     * Logic:
     * for (every piece computer has) {
     *      if (any of its moves can kill a piece)
     *           make that move (pick randomly if there are multiple)
     *      return;
     * }
     * make a random move
     * @param g instance of the Game
     */
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
    
    /**
     * @param g instance of the Game
     * @return a random piece that can move
     */
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

    /**
     * sets points to value dictated by the pointValue variable
     * @param g
     * @param win 
     */
    @Override
    public void calcPoints(Game g, boolean win) {
        setPoints(pointValue);
    }

    /**
     * move method used to interact with front end
     * @param g instance of the Game
     * @param panel panel used by front end
     * @throws InterruptedException 
     */
    @Override
    void move(Game g, GamePanel panel) throws InterruptedException {
        panel.resetSelected();
        panel.resetMove();
        panel.reset();
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
        
        Thread.sleep(delay);
    }
}
