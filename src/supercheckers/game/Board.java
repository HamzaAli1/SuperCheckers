/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercheckers.game;

import java.util.ArrayList;

/**
 *
 * @author Hamza Ali
 */
public class Board {
    private int turn;
    private ArrayList<Piece> reds, blacks;
    
    public Board() {
        turn = 0;
        
        reds = new ArrayList();
        blacks = new ArrayList();
                
        populateBoard();
    }

    public void turnUp() {
        turn++;
    }
    
    public int getTurn() {
        return turn;
    }

    public ArrayList<Piece> getReds() {
        return reds;
    }

    public ArrayList<Piece> getBlacks() {
        return blacks;
    }
    
    private void populateBoard() {
        Piece temp1, temp2;
        for (int r = 0; r < 3; r+=2) {
            for (int c = 0; c < 8; c+=2) {
                temp2 = new Piece("black", r, c + 1);
                temp1 = new Piece("red", r+5, c);
                reds.add(temp1);
                blacks.add(temp2);
            }
        }
        for (int c = 1; c < 8; c+=2) {
            temp2 = new Piece("black", 1, c - 1);
            temp1 = new Piece("red", 6, c);
            reds.add(temp1);
            blacks.add(temp2);
        }
    }

    public Piece pieceAtPoint(int r, int c, String color) {
        if (color.equals("red")) {
            for (Piece red : reds) {
                if (red.getRow() == r && red.getColumn() == c)
                    return red;
            }
        } else {
            for (Piece black : blacks) {
                if (black.getRow() == r && black.getColumn() == c)
                    return black;
            }
        }
        return null;
    }
    
    //returns true if a space on the board is empty
    public boolean noPieceAtPoint(int r, int c) {
        return pieceAtPoint(r, c, "red") == null && pieceAtPoint(r, c, "black") == null;
    }
    
    public void removePiece(int r, int c, String color) {
        if (color.equals("red"))
            reds.remove(pieceAtPoint(r, c, color));
        else if (color.equals("black"))
            blacks.remove(pieceAtPoint(r, c, color));
    }
    
    @Override
    public String toString() {
        String output = "---------------------------------\n| ";
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (pieceAtPoint(r, c, "red") != null) {
                    if (pieceAtPoint(r, c, "red").getType().equals("double"))
                        output += "R" + " | ";
                    else
                        output += "r" + " | ";
                }
                else if (pieceAtPoint(r, c, "black") != null) {
                    if (pieceAtPoint(r, c, "black").getType().equals("double"))
                        output += "B" + " | ";
                    else
                        output += "b" + " | ";
                }
                else
                    output += "  | ";
            }
            output += "\n---------------------------------\n| ";
        }
        return output.substring(0, output.length() - 2);
    }
    
    public static void main(String[] args) {
        Board test = new Board();
        System.out.println(test);
        System.out.println("Reds:\n" + test.getReds() + "\nBlacks:\n" + test.getBlacks());
    }
}