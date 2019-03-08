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
public class Piece {
        private int row, column; //These do NOT correspond to x,y pairs; think about a java matrix :|
    	private final String color; //red, black
        private String type; //regular, double

    public Piece(String color, int r, int c) {
        this.color = color;
        row = r;
        column = c;
        type = "regular";
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public void toDouble() {
        setType("double");
    }
    
    private void setType(String type) {
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    /*
    After a move has been validated, piece is moves according to <moves>. Piece is moved to final location, while any pieces hopped over are removed from the board.
    */
    public void move(String[] moves, Game g) {
        int prevR = getRow(), prevC = getColumn(), r, c;

        for (int i = 0; i < moves.length; i+=2) {
            r = Integer.parseInt(moves[i]);
            c = Integer.parseInt(moves[i+1]);

            if (Math.abs(r - prevR) == 2 && Math.abs(c - prevC) == 2) {
                if (color.equals("red")) {
                    g.getBoard().removePiece((r + prevR)/2, (c + prevC)/2, "black");
                } else /*if black*/ {
                    g.getBoard().removePiece((r + prevR)/2, (c + prevC)/2, "red");
                }
            }

            prevR = r;
            prevC = c;
        }
        
        setRow(Integer.parseInt(moves[moves.length - 2]));
        setColumn(Integer.parseInt(moves[moves.length - 1]));
        
        if(getRow() == 0 && getColor().equals("red"))
            toDouble();
        else if(getRow() == 7 && getColor().equals("black"))
            toDouble();
    }
    
    @Override
    public String toString() {
        return getColor() + getType() + getRow() + getColumn();
        
        /*
        if (color.equals("red")) {
            if (getType().equals("double"))
                return "R";
            return "r";
        } else {
            if (getType().equals("double"))
                return "B";
            return "B";
        } */
    }
}