import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Lesson_8_CollisionDetection extends JPanel implements Runnable, KeyListener{

    // Class Variables

    // game state 0 - menu state
    // game state 1 - in-game state
    // game state 2 - game over state
    public static int gamestate = 0;

    // Image
    public static BufferedImage gs0;
    public static BufferedImage gs2;
    public static BufferedImage ninja;
    public static BufferedImage skull;

    // Game Stats for Ninja
    public static int ninjaX;
    public static int ninjaY;
    public static boolean wPressed;
    public static boolean aPressed;
    public static boolean sPressed;
    public static boolean dPressed;

    public Lesson_8_CollisionDetection(){
        setPreferredSize(new Dimension(500, 500));
        // Add Thread
        Thread thread = new Thread(this);
        thread.start();
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(gamestate == 0){
            g.drawImage(gs0, 0, 0, null);
        }
        else if(gamestate == 1){
            // Update Ninja Position
            if(wPressed) ninjaY -= 5;
            if(aPressed) ninjaX -= 5;
            if(sPressed) ninjaY += 5;
            if(dPressed) ninjaX += 5;

            // Collision Detection
            if(!(ninjaX + 50 < 225 ||
                 ninjaX      > 275 ||
                 ninjaY + 50 < 225 ||
                 ninjaY      > 275)){
                gamestate = 2;
            }

            g.drawImage(ninja, ninjaX, ninjaY, null);
            g.drawImage(skull, 225, 225, null);
        }
        else if(gamestate == 2){
            g.drawImage(gs2, 0, 0, null);
        }
    }

    public static void main(String[] args) throws IOException{
        // Image Importation
        gs0 = ImageIO.read(new File("gs0.png"));
        gs2 = ImageIO.read(new File("gs4.png"));
        ninja = ImageIO.read(new File("ninja.png"));
        skull = ImageIO.read(new File("skull.png"));

        JFrame frame = new JFrame("Lesson 8 - Collision Detection");
        Lesson_8_CollisionDetection panel = new Lesson_8_CollisionDetection();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void run(){
        while(true){
            // 1) Frame Rate
            try{
                Thread.sleep(20); // 50 FPS
            }
            catch(Exception e){}

            // 2) Drawing the Screen
            repaint();
        }
    }

    public void keyPressed(KeyEvent e){
        if(gamestate == 0){
            if(e.getKeyChar() == 's') gamestate = 1;
            ninjaX = 0;
            ninjaY = 0;
            wPressed = false;
            aPressed = false;
            sPressed = false;
            dPressed = false;
        }
        else if(gamestate == 1){
            if(e.getKeyChar() == '-') gamestate = 2;
            if(e.getKeyChar() == 'w') wPressed = true;
            if(e.getKeyChar() == 'a') aPressed = true;
            if(e.getKeyChar() == 's') sPressed = true;
            if(e.getKeyChar() == 'd') dPressed = true;
        }
        else if(gamestate == 2){
            gamestate = 0;
        }
    }
    public void keyReleased(KeyEvent e){
        if(gamestate == 1){
            if(e.getKeyChar() == 'w') wPressed = false;
            if(e.getKeyChar() == 'a') aPressed = false;
            if(e.getKeyChar() == 's') sPressed = false;
            if(e.getKeyChar() == 'd') dPressed = false;
        }
    }

    // Useless Method
    public void keyTyped(KeyEvent e){}

}
