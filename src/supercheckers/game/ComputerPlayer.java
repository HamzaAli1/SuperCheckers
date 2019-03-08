/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercheckers.game;

/**
 *
 * @author Hamza Ali
 */
public final class ComputerPlayer extends Player {
    
    //set rank for computer player; TBD
    private final int compRank = 0;
    
    public ComputerPlayer() {
        super("Computer");
        calcRank(null);
    }
    
    @Override
    void move(Game g) {
        System.out.println("Computer is making its move...");
        /*
        for (every piece computer has)
            if (any of its moves can kill a piece)
                make the move that kills the most
                return
        make a move that moves a piece closest to the other side of the board || make a random move
        */
        System.out.println("Computer has made a move.");
    }

    @Override
    void calcRank(Game g) {
        setRank(compRank);
    }
}
