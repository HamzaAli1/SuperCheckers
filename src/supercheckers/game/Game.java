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
public class Game {
    
    private Board board;
    
    //one is red (going up), two is black (going down); this fact while be made obvious in the GUI
    private final Player one;
    private final Player two;

    public Game() {
        one = new HumanPlayer("Test Player"); //TODO maybe change this to a computer player later on to have some fun :)
        two = new ComputerPlayer();
        one.setColor("red");
        two.setColor("black");
    }

    public Game(Player one, Player two) {
        this.one = one;
        this.two = two;
        one.setColor("red");
        two.setColor("black");
    }
    
    public void play() {
        System.out.println("Starting Game...\n");
        board = new Board();
        double rand = Math.random();
        if (rand < 0.5) { //TODO: make this random turn order part more efficient (seems redundant)
            System.out.println("Player 1 Starts!");
            while (canStillPlay()) {
                System.out.println("\n" + getBoard());
                if (board.getTurn() % 2 == 0)
                    one.move(this);
                else
                    two.move(this);
                board.turnUp();
            }
        } else {
            System.out.println("Player 2 Starts!");
            while (canStillPlay()) {
                System.out.println("\n" + getBoard());
                if (board.getTurn() % 2 == 0)
                    two.move(this);
                else
                    one.move(this);
                board.turnUp();
            }
        }
        System.out.println("\n\n" + getBoard() + "\n");
        if (board.getReds().isEmpty() || !one.canMove(this))
            System.out.println("Black Wins!");
        else if (board.getBlacks().isEmpty() || !two.canMove(this))
            System.out.println("Red Wins!");
        else
            System.out.println("Tie!");
    }
    
    public boolean canStillPlay() {
        if (board.getTurn() < 100) {
            if (board.getReds().size() > 0 && board.getBlacks().size() > 0) {
                if (one.canMove(this) && two.canMove(this))
                    return true;
            }
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }
    
    //nonGUI game for debug
    public static void main(String[] args) {
        Player one = new HumanPlayer("test1"), two = new HumanPlayer("test2");
        Game test = new Game(one, two);
        test.play();
    }
}