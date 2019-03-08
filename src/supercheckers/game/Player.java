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
public abstract class Player implements Comparable {
    private int rank;
    private String name;
    private String color;
    
    public Player(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRank() {
        return rank;
    }

    protected void setRank(int rank) {
        this.rank = rank;
    }
    
    abstract void move(Game g);
    abstract void calcRank(Game g);

    /*
    Makes sure there is a piece at the selected position on the board. 
    This is done by going through the arraylist of pieces cosresponding to the player's <color>,
    and checking if there is a piece in the arraylist with a matching r & c value.
     */
    protected Piece validPiece(Game g, String in) {
        if (getColor().equals("red")) {
            for (Piece p : g.getBoard().getReds()) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2))) {
                    return p;
                }
            }
        } else {
            for (Piece p : g.getBoard().getBlacks()) {
                if (p.getRow() == Character.getNumericValue(in.charAt(0)) && p.getColumn() == Character.getNumericValue(in.charAt(2))) {
                    return p;
                }
            }
        }
        return null;
    }
    
    /*
    Checks to see if current player can make a move. Calls piece can move on all
    pieces the player controls and sums up the number of times the call returns true.
    If sum = 0 then the payer has no moves available.
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
    
    /*
    Checks if Piece <p> can can make a move. This is done by creating all possible 
    moves for that piece and then calling validMove() to confirm if the move is valid.
    Returns flase if no moves are available for piece. This is maily used to 
    check if a player can make any moves.
    */
    protected boolean pieceCanMove(Game g, Piece p, String color) {
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
        
    /*
    Makes sure that an inputted move is valid. This is done by checking if inputted 
    move matchs one of the 4 possible moves any chess piece can make (8 for double).
    Returns false if move is not valid for any reason.
     */
    protected boolean validMove(Game g, String[] moves, Piece piece) {
        int r, c, prevR = piece.getRow(), prevC = piece.getColumn();
        boolean singleHop = false; //piece can only make multiple moves if it is hopping over opposing pieces. This boolean prevents illegal moves by tracking what kinf of moves have been made.
        boolean doubleHop = false; //ditto above except for different cases
        
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
            
            if (singleHop)
                return false;
            
            //make sure move is either diagonally adjacent to piece, or diagonally one space away WITH an opposing piece in between.
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
            } else /*if double*/ { //TODO: when you have the simple stuff working, PLZ make sure that this validation works
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
        return true;
    }
    
    @Override
    //Compare by player ranks.
    public int compareTo(Object o) {
        Player other = (Player) o;
        if (getRank() > other.getRank()) return 1;
        else if (getRank() < other.getRank()) return -1;
        return 0;
    }
}
