/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercheckers.game;

import gui.GamePanel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author Hamza Ali
 */
public abstract class Player implements Comparable, Serializable {
    
    /**
     * holds number of points player has earned
     */
    private int points;
    
    /**
     * player name
     */
    private String name;
    
    /**
     * player color, set during Game
     */
    private String color;
    
    /**
     * holds all possible kill moves a player has; used during the Game
     */
    protected final ArrayList<Piece> kills = new ArrayList<>();
    
    /**
     * main constructor, creates a new Player
     * @param n 
     */
    public Player(String n) {
        name = n;
    }

    /** 
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the player's name
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * @return the player's color
     */
    public String getColor() {
        return color;
    }

    /**
     * sets the player's color
     * @param color new color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /** 
     * @return the player's points
     */
    public int getPoints() {
        return points;
    }

    /**
     * sets the player's points
     * @param points new point value
     */
    protected void setPoints(int points) {
        this.points = points;
    }
    
    /**
     * makes a move for a player
     * @param g instance of the Game
     */
    abstract void move(Game g);
    
    /**
     * connects with front end instead of printing
     * @param g instance of the Game
     * @param panel panel used by front end
     * @throws InterruptedException 
     */
    abstract void move(Game g, GamePanel panel) throws InterruptedException;
    
    /**
     * adjusts players points after a match
     * @param g instance of the Game
     * @param win 
     */
    public abstract void calcPoints(Game g, boolean win);

    /**
     * Makes sure there is a piece at the selected position on the board. 
     * This is done by going through the ArrayList of pieces corresponding to the player's color,
     * and checking if there is a piece in the ArrayList with a matching r & c value. 
     * This method also enforces the rule where any kill moves available to the player must be taken.
     * @param g instance of the Game
     * @param in String containing row & column of piece; "r,c"
     * @return the piece if there is a valid piece at the coordinates, else null
     */
    protected Piece validPiece(Game g, String in) {
        if (!kills.isEmpty()) {
            for (Piece p : kills) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2)))
                    return p;
            }
            System.out.println("You have a capture available that you have to make.");
            return null;
        }
        
        if (getColor().equals("red")) {
            for (Piece p : g.getBoard().getReds()) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2))) {
                    return p;
                }
            }
        } else /*if black*/ {
            for (Piece p : g.getBoard().getBlacks()) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2))) {
                    return p;
                }
            }
        }
        System.out.println("No piece found at point. Please try again.");
        return null;
    }
    
    /**
     * Same method as above, except it interacts with the GUI
     * @param g instance of the Game
     * @param in String containing row & column of piece; "r,c"
     * @param panel the panel used by the front end
     * @return the piece if there is a valid piece at the coordinates, else null
     * @throws InterruptedException 
     */
    protected Piece validPiece(Game g, String in, GamePanel panel) throws InterruptedException {
        if (!kills.isEmpty()) {
            for (Piece p : kills) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2)))
                    return p;
            }
            panel.setGameOutput("You have a capture available that you have to make.");
            return null;
        }
        
        if (getColor().equals("red")) {
            for (Piece p : g.getBoard().getReds()) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2))) {
                    return p;
                }
            }
        } else /*if black*/ {
            for (Piece p : g.getBoard().getBlacks()) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2))) {
                    return p;
                }
            }
        }
        panel.setGameOutput("No piece found at point. Please try again.");
        return null;
    }
    
    /**
     * Method finds all pieces with possible kill moves. One of these pieces must 
     * be moved during a players turn. Finds pieces by running validMove() on all
     * pieces player controls, and checking if those piece have a valid kill move (diagonal hop).
     * @param g instance of the game
     */
    protected void killMoves(Game g) {
        ArrayList<Piece> pieces = new ArrayList<>();
        String moveR, moveL, moveR2, moveL2;
        
        if (getColor().equals("red")) {
            for (Piece p : g.getBoard().getReds()) {
                moveL = "" + (p.getRow()-2) + "," + (p.getColumn()-2);
                moveR = "" + (p.getRow()-2) + "," + (p.getColumn()+2);
                moveL2 = "" + (p.getRow()+2) + "," + (p.getColumn()-2);
                moveR2 = "" + (p.getRow()+2) + "," + (p.getColumn()+2);
                if (validMove(g, moveL.split("[,]"), p))
                    pieces.add(p);
                else if (validMove(g, moveR.split("[,]"), p))
                    pieces.add(p);
                else if (p.getType().equals("double")) {
                    if (validMove(g, moveL2.split("[,]"), p))
                        pieces.add(p);
                    else if (validMove(g, moveR2.split("[,]"), p))
                        pieces.add(p);
                }
            }
        } else /*if black*/ {
            for (Piece p : g.getBoard().getBlacks()) {
                moveL = "" + (p.getRow()+2) + "," + (p.getColumn()-2);
                moveR = "" + (p.getRow()+2) + "," + (p.getColumn()+2);
                moveL2 = "" + (p.getRow()-2) + "," + (p.getColumn()-2);
                moveR2 = "" + (p.getRow()-2) + "," + (p.getColumn()+2);
                if (validMove(g, moveL.split("[,]"), p))
                    pieces.add(p);
                else if (validMove(g, moveR.split("[,]"), p))
                    pieces.add(p);
                else if (p.getType().equals("double")) {
                    if (validMove(g, moveL2.split("[,]"), p))
                        pieces.add(p);
                    else if (validMove(g, moveR2.split("[,]"), p))
                        pieces.add(p);
                }
            }
        }
        kills.addAll(pieces);
    }
    
    /**
     * clears killMoves
     */
    public void clearKillMoves() {kills.removeAll(kills);}
    
    /**
     * Checks to see if current player can make a move. Calls piece can move on all
     * pieces the player controls and sums up the number of times the call returns true.
     * If sum = 0 then the payer has no moves available.
     * @param g instance of the Game
     * @return true if the player has any available moves
     */
    public boolean canMove(Game g) {
        int moves = 0;
        if (getColor().equals("red")) {
            for (Piece red : g.getBoard().getReds()) {
                if (pieceCanMove(g, red, "red")) moves++;
            }
        } else /*if black*/ {
            for (Piece blk : g.getBoard().getBlacks()) {
                if (pieceCanMove(g, blk, "black")) moves++;
            }
        }
        return moves > 0;
    }
    
    /**
     * Checks if Piece p can can make a move. This is done by creating all possible 
     * moves for that piece and then calling validMove() to confirm if the move is valid.
     * Returns false if no moves are available for piece. This is mainly used to 
     * check if a player can make any moves.
     * @param g instance of the Game
     * @param p piece being checked
     * @param color piece color...
     * @return true if the piece has any available moves
     */
    protected boolean pieceCanMove(Game g, Piece p, String color) {
        if (kills.contains(p)) {
            return true;
        }
        int r, c;
        String move1, move2, move3, move4, move1d, move2d, move3d, move4d;
        r = p.getRow(); c = p.getColumn();
        if (p.getType().equals("double")) {
            move1 = "" + (r-1) + "," + (c-1);
            move2 = "" + (r-1) + "," + (c+1);
            move3 = "" + (r-2) + "," + (c-2);
            move4 = "" + (r-2) + "," + (c+2);
            move1d = "" + (r+1) + "," + (c-1);
            move2d = "" + (r+1) + "," + (c+1);
            move3d = "" + (r+2) + "," + (c-2);
            move4d = "" + (r+2) + "," + (c+2);
            if (validMove(g, move1.split("[,]"), p)) return true;
            if (validMove(g, move2.split("[,]"), p)) return true;
            if (validMove(g, move3.split("[,]"), p)) return true;
            if (validMove(g, move4.split("[,]"), p)) return true;
            if (validMove(g, move1d.split("[,]"), p)) return true;
            if (validMove(g, move2d.split("[,]"), p)) return true;
            if (validMove(g, move3d.split("[,]"), p)) return true;
            if (validMove(g, move4d.split("[,]"), p)) return true;
        } else {
            if (color.equals("red")) {
                move1 = "" + (r-1) + "," + (c-1);
                move2 = "" + (r-1) + "," + (c+1);
                move3 = "" + (r-2) + "," + (c-2);
                move4 = "" + (r-2) + "," + (c+2);
                if (validMove(g, move1.split("[,]"), p)) return true;
                if (validMove(g, move2.split("[,]"), p)) return true;
                if (validMove(g, move3.split("[,]"), p)) return true;
                if (validMove(g, move4.split("[,]"), p)) return true;
            } else /*if black*/ {
                move1 = "" + (r+1) + "," + (c-1);
                move2 = "" + (r+1) + "," + (c+1);
                move3 = "" + (r+2) + "," + (c-2);
                move4 = "" + (r+2) + "," + (c+2);
                if (validMove(g, move1.split("[,]"), p)) return true;
                if (validMove(g, move2.split("[,]"), p)) return true;
                if (validMove(g, move3.split("[,]"), p)) return true;
                if (validMove(g, move4.split("[,]"), p)) return true;
            }
        }
        return false;
    }
        
    /**
     * Makes sure that an inputted move is valid. This is done by checking if inputted 
     * move matches one of the 4 possible moves any chess piece can make (8 for double).
     * Returns false if move is not valid for any reason.
     * @param g instance of the Game
     * @param moves moves being made
     * @param piece piece being moved
     * @return true if move is valid
     */
    protected boolean validMove(Game g, String[] moves, Piece piece) {
        int r, c, prevR = piece.getRow(), prevC = piece.getColumn();
        boolean singleHop = false; //piece can only make multiple moves if it is hopping over opposing pieces. This boolean prevents illegal moves by tracking what kinf of moves have been made.
        boolean doubleHop = false; //ditto above except for different cases
        boolean killMade = false; //makes sure player makes kill move
        
        //for every inputted move in <moves>
        for (int i = 0; i < moves.length; i += 2) {
            r = Integer.parseInt(moves[i]);
            c = Integer.parseInt(moves[i + 1]);
            if (i != 0) {
                prevR = Integer.parseInt(moves[i - 2]);
                prevC = Integer.parseInt(moves[i - 1]);
            }
            
            if ((r < 0 || r > 7) || (c < 0 || c > 7))
                return false;
            
            else if (!killMade && !kills.isEmpty()) {
                if (piece.getType().equals("double")) {
                    if ((Math.abs(r - prevR) == 2) && (Math.abs(c - prevC) == 2)) {
                        if (getColor().equals("black")) {
                            if (g.getBoard().pieceAtPoint((r + prevR)/2, (c + prevC)/2, "red") == null || !g.getBoard().noPieceAtPoint(r, c))
                                return false;
                        } else /*if red*/ {
                            if (g.getBoard().pieceAtPoint((r + prevR)/2, (c + prevC)/2, "black") == null || !g.getBoard().noPieceAtPoint(r, c))
                                return false;
                        }
                    } else return false;
                } else /*if regular*/ {
                    if (getColor().equals("black")) {
                        if (r - 2 == prevR && (c + 2 == prevC || c - 2 == prevC)) {
                            if ((g.getBoard().pieceAtPoint(r - 1, (c + prevC)/2, "red") == null) || !g.getBoard().noPieceAtPoint(r, c)) {
                                return false;
                            }
                        } else return false;
                    } else /*if red*/ {
                        if (r + 2 == prevR && (c + 2 == prevC || c - 2 == prevC)) {
                            if (g.getBoard().pieceAtPoint(r + 1, (c + prevC)/2, "black") == null || !g.getBoard().noPieceAtPoint(r, c)) {
                                return false;
                            }
                        } else return false;
                    }
                }
                killMade = true;
                doubleHop = true;
            }
            
            else if (singleHop)
                return false;
            
            //make sure move is either diagonally adjacent to piece, or diagonally one space away WITH an opposing piece in between.
            else {
                if (piece.getType().equals("regular")) {
                    if (getColor().equals("black")) {
                        if (r - 1 == prevR && (c + 1 == prevC || c - 1 == prevC)) {
                            if (doubleHop == true)
                                return false;
                            if (!g.getBoard().noPieceAtPoint(r, c))
                                return false;
                            singleHop = true;
                        } else if (r - 2 == prevR && (c + 2 == prevC || c - 2 == prevC)) {
                            if ((g.getBoard().pieceAtPoint(r - 1, (c + prevC)/2, "red") == null) || !g.getBoard().noPieceAtPoint(r, c)) {
                                return false;
                            }
                            doubleHop = true;
                        } else return false;
                    } else /*if red*/ {
                        if (r + 1 == prevR && (c + 1 == prevC || c - 1 == prevC)) {
                            if (doubleHop == true)
                                return false;
                            if (!g.getBoard().noPieceAtPoint(r, c))
                                return false;
                            singleHop = true;
                        } else if (r + 2 == prevR && (c + 2 == prevC || c - 2 == prevC)) {
                            if (g.getBoard().pieceAtPoint(r + 1, (c + prevC)/2, "black") == null || !g.getBoard().noPieceAtPoint(r, c)) {
                                return false;
                            }
                            doubleHop = true;
                        } else return false;
                    }
                } else /*if double*/ {
                    if ((Math.abs(r - prevR) > 2) && (Math.abs(c - prevC) > 2)) {
                        return false;
                    } else if ((Math.abs(r - prevR) == 2) && (Math.abs(c - prevC) == 2)) {
                        if (getColor().equals("black")) {
                            if (g.getBoard().pieceAtPoint((r + prevR)/2, (c + prevC)/2, "red") == null || !g.getBoard().noPieceAtPoint(r, c))
                                return false;
                            doubleHop = true;
                        } else /*if red*/ {
                            if (g.getBoard().pieceAtPoint((r + prevR)/2, (c + prevC)/2, "black") == null || !g.getBoard().noPieceAtPoint(r, c))
                                return false;
                            doubleHop = true;
                        }
                    } else if ((Math.abs(r - prevR) == 1) && (Math.abs(c - prevC) == 1)) {
                        if (doubleHop == true)
                                return false;
                        if (!g.getBoard().noPieceAtPoint(r, c))
                            return false;
                        singleHop = true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo(obj) == 0;
    }
    
    /**
     * compares Players; Players cannot have the same name, so only factor is ranking points
     * @param o player being compared to this player
     * @return 1 is greater, 0 if equal, -1 if less
     */
    @Override
    //Compare by player ranks.
    public int compareTo(Object o) {
        Player other = (Player) o;
        if (!name.equals(other.getName())) {
            if (points != other.getPoints()) return (points - other.getPoints());
            else
                return hashCode() - other.hashCode();
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    /**
     * debug compareTo
     * @param args 
     */
    public static void main(String[] args) {
        TreeSet<Player> players = new TreeSet<>();
        HumanPlayer hamza = new HumanPlayer("hamza", "boop"), baba = new HumanPlayer("baba", "boop"), bob = new HumanPlayer("bob", "boop"), test = new HumanPlayer("test", "boop");
        
        hamza.setPoints(100);
        bob.setPoints(38);
        baba.setPoints(42);
        test.setPoints(0);
        
        players.add(bob);
        players.add(test);
        players.add(baba);
        players.add(hamza);
        
        System.out.println(players.toString() + "\n" + players.descendingSet().toString());
    }
}
