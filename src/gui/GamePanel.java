/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import supercheckers.game.Game;
import supercheckers.game.HumanPlayer;
import supercheckers.game.Piece;
import supercheckers.game.Player;

/**
 *
 * @author Hamza Ali
 */
public class GamePanel extends Panel {
    
    //panel manages game
    private final Game game;
    
    /* determines what the paint/repaint method does
    0 = default, just paints board
    1 = higlights a space on the board (cyan)
    2 = prints something out in text box
    3 = selecting spaces for a move (highlights in yellow)
    4 = confirm final move; only paints button */
    private int paintPurpose;
    
    //used to track what has been selected, only used to keep said piece highlighted during paintPurpose 3
    private String piece = "";
    
    //used for paintPurpose 3 to select a space/spaces to move to.
    private String move = "";
    
    //tracks mouse position for highlight methods
    private int mouseX = -1;
    private int mouseY = -1;
    
    //used to print output from game
    private String gameOutput;
    
    //for pieces; regular red and black for board, light red and dark gray for pieces
    private static final Color LIGHT_RED = new Color(255, 102, 102);
    
    //control buttons
    private boolean buttonActive = false;
    private boolean buttonPressed = false;
    private boolean finalButtonPressed = false;
    
    //holds image for a double piece
    private final BufferedImage img_double = loadCrown();
    
    public GamePanel () {
        super();
        game = new Game();
        paintPurpose = 0;
        
        initComponents();
    }
    
    public GamePanel (Game g) {
        super();
        game = g;
        paintPurpose = 0;
        
        initComponents();
    }
    
    private void initComponents() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (buttonActive && (x > 647 && x < 763 && y > 824 && y < 880)) {
                    if ((paintPurpose == 1 || paintPurpose == 3))
                        buttonPressed = true;
                    else if (paintPurpose == 4) {
                        if(x > 645 && x < 700 && y > 824 && y < 880)
                            buttonPressed = true;
                        else {
                            resetSelected();
                            resetMove();
                        }
                        finalButtonPressed = true;
                    }
                }
                else if (paintPurpose == 1 || paintPurpose == 3) {
                    highlightSquare(x, y);
                    buttonActive = true;
                    if (paintPurpose == 1)
                        piece = getSelectedPiece();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }
    
    //plays game, returns winner
    public Player play() throws InterruptedException {
        return game.play(this);
    }
    
    //repaints board, removing button and any highlights
    public void reset() {
        paintPurpose = 0;
        buttonPressed = false;
        buttonActive = false;
        finalButtonPressed = false;
        repaint();
    }
    
    //returns selected piece
    public String getSelectedPiece() {
        if (mouseX == -1 || mouseY == -1)
            return "";
        return mouseX/100 + "," + mouseY/100;
    }
    
    //returns selected piece
    public String getMove() {
        return move;
    }
    
    public void setPaintPurpose(int paintPurpose) {
        this.paintPurpose = paintPurpose;
    }

    public int getPaintPurpose() {
        return paintPurpose;
    }

    public void setGameOutput(String gameOutput) {
        this.gameOutput = gameOutput;
        paintPurpose = 2;
        repaint(0, 800, 800, 100);
    }
    
    public boolean confirmed() {
        return buttonPressed;
    }
    
    public boolean finalConfirm() {
        return finalButtonPressed;
    }
    
    //resets piece
    public void resetSelected() {
        piece = "";
    }
    
    //resets move
    public void resetMove() {
        move = "";
    }
    
    @Override
    public void paint(Graphics g) {
        paintBoard(g);
        paintText(g, "");
        if (!piece.isEmpty()) {
            g.setColor(Color.CYAN);
            g.fillRect(Integer.parseInt(piece.split("[,]")[0])*100, Integer.parseInt(piece.split("[,]")[1])*100, 100, 100);
        }
        if (!move.isEmpty()) {
            g.setColor(Color.YELLOW);
            for (String m : move.split("[ ]")) {
                int x = Integer.parseInt(m.substring(0, 1))*100, y = Integer.parseInt(m.substring(2))*100;
                g.fillRect(x, y, 100, 100);
            }
        }
        
        if (paintPurpose == 1) {
            if (mouseX != -1 && mouseY != -1) {
                g.setColor(Color.CYAN);
                g.fillRect(mouseX, mouseY, 100, 100);
                paintText(g, "Selected Piece: " + mouseX/100 + "," + mouseY/100);
                paintButton(g);
            }
        } else if (paintPurpose == 2) {
            paintText(g, gameOutput);
        } else if (paintPurpose == 3) {
            if (mouseX != -1 && mouseY != -1) {
                g.setColor(Color.YELLOW);
                for (String m : move.split("[ ]")) {
                    int x = Integer.parseInt(m.substring(0, 1))*100, y = Integer.parseInt(m.substring(2))*100;
                    g.fillRect(x, y, 100, 100);
                }
                paintText(g, "Selected Space(s): " + move);
                paintButton(g);
            }
        } else if (paintPurpose == 4) {
            paintText(g, "Confirm move?");
            paintFinalButton(g);
        }
        paintPieces(g);        
    }
    
    //prints text at bottom of board
    private void paintText(Graphics g, String text) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 800, 800, 100);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 25));
        g.drawString(text, 100, 860);
    }
    
    //paints board
    private void paintBoard(Graphics g) {
        for (int r = 0; r < 800; r+=100) {
            for (int c = 0; c < 800; c+=100) {
                if (r % 200 == 0) {
                    if (c % 200 == 0)
                        g.setColor(Color.RED);
                    else
                        g.setColor(Color.BLACK);
                } else {
                    if (c % 200 == 0)
                        g.setColor(Color.BLACK);
                    else
                        g.setColor(Color.RED);
                }
                g.fillRect(r, c, 100, 100);
            }
        }   
    }
    
    //paints pieces
    private void paintPieces(Graphics g) {
        Piece temp;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (!game.getBoard().noPieceAtPoint(r, c)) {
                    temp = game.getBoard().pieceAtPoint(r, c, "red");
                    if (temp != null) /*paint a red piece*/
                        g.setColor(LIGHT_RED);
                    else { /*paint a black piece*/
                        g.setColor(Color.DARK_GRAY);
                        temp = game.getBoard().pieceAtPoint(r, c, "black");
                    }
                    g.fillOval(r*100, c*100, 100, 100);
                    if (temp.getType().equals("double")) {
                        paintDouble(g, r*100, c*100);
                    }
                }
            }
        }
    }
    
    //paints crown onto double pieces
    private void paintDouble(Graphics g, int x, int y) {
        g.drawImage(img_double, x, y, null);
    }
    
    //loads crown png
    private BufferedImage loadCrown() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./dat/double_final.png"));
        } catch (IOException e) {}
        return img;
    }
    
    //paints button used to confirm certain actions
    private void paintButton(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(647, 824, 116, 56);
        g.setColor(Color.RED);
        g.fillRect(650, 827, 110, 50);
        g.setColor(Color.BLACK);
        g.drawString("Confirm?", 650, 860);
        buttonActive = true;
    }
    
    //for move finalization
    private void paintFinalButton(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(645, 824, 120, 56);
        g.setColor(Color.GREEN);
        g.fillRect(648, 827, 55, 50);
        g.setColor(Color.RED);
        g.fillRect(707, 827, 55, 50);
        g.setColor(Color.BLACK);
        g.drawString("Yes", 652, 860);
        g.drawString("No", 715, 860);
        buttonActive = true;
    }
    
    private void highlightSquare(int x, int y) {
        if (y > 799) {
            repaint();
            return;
        }
        for (int r = 0; r < 800; r+=100) {
            for (int c = 0; c < 800; c+=100) {
                if (x > r && x < r + 100) {
                    if (y > c && y < c + 100) {
                        mouseX = r;
                        mouseY = c;
                        break;
                    }
                }
            }
        }
        
        if (paintPurpose == 3) {
            move += mouseX/100 + "," + mouseY/100 + " ";
        }
        repaint();
        
        //System.out.println("x: " + mouseX + "\ny: " + mouseY);
    }
    
    public static void main(String[] args) throws InterruptedException {
        Player one = new HumanPlayer("test1", ""), two = new HumanPlayer("test2", "");
        Game game = new Game(one, two);
        
        JFrame window = new JFrame();
        GamePanel test = new GamePanel(game);
        window.add(test);
        
        Dimension dms = new Dimension(800, 940); //board is only 800, 840
        window.setSize(dms);
        window.setMinimumSize(dms);
        window.setResizable(false);
        window.setVisible(true);
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                //System.out.println(window.getSize());
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        //start game
        test.play();
    }
}
