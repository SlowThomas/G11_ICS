import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Lesson_7_SpriteAnimation extends JPanel implements KeyListener, Runnable{

    // Game Stats for Pikachu
    public static int posX = 200;
    public static int posY = 150;
    public static BufferedImage[] pikachus = new BufferedImage[4];
    public static int pikachuIndex = 0;
    public static int pikachuFrameCounter = 0;
    public static boolean aPressed = false;
    public static boolean dPressed = false;

    // JPanel Settings
    public Lesson_7_SpriteAnimation(){
        setPreferredSize(new Dimension(500, 300));
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        // Add Thread (Timer)
        Thread thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // Adjust pikachu position
        if(aPressed) posX -= 5;
        if(dPressed) posX += 5;

        g.drawImage(pikachus[pikachuIndex], posX, posY, null);

        // The pikachu frame counter is used to control
        // how fast pikachu changes sprite
        // Without this, pikachu will be changing sprite
        // every 20 millisecond
        pikachuFrameCounter++;
        if(pikachuFrameCounter == 4){
            // Adjust the sprite
            pikachuIndex++;
            if(pikachuIndex == 4) pikachuIndex = 0;
            pikachuFrameCounter = 0;
        }
    }

    public static void main(String[] args) throws IOException{
        // Image Importation
        pikachus[0] = ImageIO.read(new File("pikachu0.png"));
        pikachus[1] = ImageIO.read(new File("pikachu1.png"));
        pikachus[2] = ImageIO.read(new File("pikachu2.png"));
        pikachus[3] = ImageIO.read(new File("pikachu3.png"));

        JFrame frame = new JFrame("Lesson 7 - Sprite Animation");
        Lesson_7_SpriteAnimation panel = new Lesson_7_SpriteAnimation();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void run() {
        while(true){
            // 1) Set up frame rate
            try{
                Thread.sleep(20); // 20 ms per frame <=> 50 frames per second
            }
            catch(Exception e){}

            // 2) Redraw the screen
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'a') aPressed = true;
        if(e.getKeyChar() == 'd') dPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'a') aPressed = false;
        if(e.getKeyChar() == 'd') dPressed = false;
    }

    // Useless Method
    public void keyTyped(KeyEvent e) {}
}
