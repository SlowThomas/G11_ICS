import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Lesson_4_GameStatesTransition extends JPanel implements KeyListener{

    // Game State Variable
    // gs0 - menu
    // gs1 - level 1
    // gs2 - level 2
    // gs3 - win
    // gs4 - game over
    // gs5 - credit
    public static int gameState = 0;

    // Image Variables
    public static BufferedImage gs0;
    public static BufferedImage gs1;
    public static BufferedImage gs2;
    public static BufferedImage gs3;
    public static BufferedImage gs4;
    public static BufferedImage gs5;

    public Lesson_4_GameStatesTransition(){
        setPreferredSize(new Dimension(500, 500));
        this.setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g){
        if(gameState == 0){
            g.drawImage(gs0, 0, 0, null);
        }
        else if(gameState == 1){
            g.drawImage(gs1, 0, 0, null);
        }
        else if(gameState == 2){
            g.drawImage(gs2, 0, 0, null);
        }
        else if(gameState == 3){
            g.drawImage(gs3, 0, 0, null);
        }
        else if(gameState == 4){
            g.drawImage(gs4, 0, 0, null);
        }
        else if(gameState == 5){
            g.drawImage(gs5, 0, 0, null);
        }
    }

    public static void main(String[] args) throws IOException{

        // Image Importation
        gs0 = ImageIO.read(new File("gs0.png"));
        gs1 = ImageIO.read(new File("gs1.png"));
        gs2 = ImageIO.read(new File("gs2.png"));
        gs3 = ImageIO.read(new File("gs3.png"));
        gs4 = ImageIO.read(new File("gs4.png"));
        gs5 = ImageIO.read(new File("gs5.png"));

        JFrame frame = new JFrame("Lesson 4 - Game States Transition");
        Lesson_4_GameStatesTransition panel = new Lesson_4_GameStatesTransition();
        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }

    public void keyPressed(KeyEvent e){
        if(gameState == 0){
            if(e.getKeyChar() == 's'){
                gameState = 1;
                repaint();
            }
            else if(e.getKeyChar() == 'c'){
                gameState = 5;
                repaint();
            }
        }
        else if(gameState == 1){
            if(e.getKeyChar() == '='){
                gameState = 2;
                repaint();
            }
            else if(e.getKeyChar() == '-'){
                gameState = 4;
                repaint();
            }
        }
        else if(gameState == 2){
            if(e.getKeyChar() == '='){
                gameState = 3;
                repaint();
            }
            else if(e.getKeyChar() == '-'){
                gameState = 4;
                repaint();
            }
        }
        else if(gameState == 3 || gameState == 4){
            gameState = 0;
            repaint();
        }
        else if(gameState == 5){
            if(e.getKeyCode() == 27){
                gameState = 0;
                repaint();
            }
        }
    }

    // Useless Methods
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

}
