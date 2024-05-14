import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Lesson_9_Gravity extends JPanel implements KeyListener, Runnable{

    // Game States for T Rex
    public static BufferedImage[] trex = new BufferedImage[3];
    public static int trexIndex = 0;
    public static int trexX = 110;
    public static int trexY = 206;
    public static int trexFrameCounter = 0;
    public static int velocity = -20;
    public static int gravity = 2;
    public static boolean isJumping = false;

    // Setting Up JPanel
    public Lesson_9_Gravity(){
        setPreferredSize(new Dimension(300, 300));
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        // Add Thread
        Thread thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(isJumping){
            trexIndex = 2;
            trexY += velocity;
            velocity += gravity;

            if(trexY == 206){ // If t-rex gets back on the floor, we need to reset:
                isJumping = false;
                trexIndex = 0;
                velocity = -20;
            }
        }
        else{ // If trex is NOT jumping, he must be on the ground
            trexFrameCounter++;
            if (trexFrameCounter == 4) {
                trexIndex++;
                if (trexIndex == 2) trexIndex = 0;
                trexFrameCounter = 0;
            }
        }

        g.drawImage(trex[trexIndex], trexX, trexY, null);
    }

    public static void main(String[] args) throws IOException{
        // Image Importation
        trex[0] = ImageIO.read(new File("trexright.png"));
        trex[1] = ImageIO.read(new File("trexleft.png"));
        trex[2] = ImageIO.read(new File("trexjump.png"));

        JFrame frame = new JFrame("Lesson 9 - Gravity");
        Lesson_9_Gravity panel = new Lesson_9_Gravity();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // Runnable Methods
    public void run() {
        while(true){
            // 1) Program Delay
            try{ Thread.sleep(20); }
            catch(Exception e){}

            // 3) Drawing the Screen
            repaint();
        }
    }

    // Key Listener Methods
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 32){
            isJumping = true;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
