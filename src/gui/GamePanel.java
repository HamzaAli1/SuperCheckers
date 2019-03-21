/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    private Game game;
    
    /* determines what the paint/repaint method does
    0 = default, just paints board
    1 = higlights a space on the board
    2 = paints all pieces onto board
    */
    private int paintPurpose;
    
    //tracks mouse position for highlight methods
    private int mouseX;
    private int mouseY;
    
    //for pieces; regular red and black for board, light red and dark gray for pieces
    private static final Color LIGHT_RED = new Color(255, 102, 102);
    
    public GamePanel () {
        super();
        game = new Game();
        paintPurpose = 0;
    }
    
    public GamePanel (Game g) {
        super();
        game = g;
        paintPurpose = 0;
    }
    
    public Player play() {
        return game.play();
    }
    
    @Override
    public void paint(Graphics g) {
        paintBoard(g);
        if (paintPurpose == 1) {
            g.setColor(Color.cyan);
            g.fillRect(mouseX, mouseY, 100, 100);
        }
        paintPieces(g);
        paintPurpose = 0;
    }
    
    //paints board
    private void paintBoard(Graphics g) {
        Piece temp;
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
    
    //paints pieces TODO
    private void paintPieces(Graphics g) {
        Piece temp;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (!game.getBoard().noPieceAtPoint(r, c)) {
                    temp = game.getBoard().pieceAtPoint(r, c, "red");
                    if (temp != null) /*paint a red piece*/
                        g.setColor(LIGHT_RED);
                    else /*paint a black piece*/
                        g.setColor(Color.DARK_GRAY);
                    g.fillOval(r*100, c*100, 100, 100);
                }
            }
        }
    }
    
    private void highlightSquare(int x, int y) {
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
        paintPurpose = 1;
        repaint();
    }
    
    public static void main(String[] args) {
        Player one = new HumanPlayer("test1", ""), two = new HumanPlayer("test2", "");
        Game game = new Game(one, two);
        
        JFrame window = new JFrame();
        GamePanel test = new GamePanel(game);
        window.add(test);
        
        Dimension dms = new Dimension(800, 840);
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
        
        test.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                test.highlightSquare(x, y);
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
                test.repaint();
            }
        });
        
        //start game
        test.play();
    }
}
