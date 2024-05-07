import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;


public class Lesson_6_Runnable extends JPanel implements KeyListener, Runnable{
    // Class Variables
    public static BufferedImage ninja;

    // Game Stats for Ninja
    public static int ninjaX = 0;
    public static int ninjaY = 0;
    public static boolean wPressed = false;
    public static boolean aPressed = false;
    public static boolean sPressed = false;
    public static boolean dPressed = false;

    // JPanel Settings
    public Lesson_6_Runnable(){
        setPreferredSize(new Dimension(500, 500));
        this.setFocusable(true);
        addKeyListener(this);
        // Adding Thread (Timer)
        Thread thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // Clears the screen

        if(wPressed) ninjaY -= 5;
        if(aPressed) ninjaX -= 5;
        if(sPressed) ninjaY += 5;
        if(dPressed) ninjaX += 5;
        g.drawImage(ninja, ninjaX, ninjaY, null);
    }

    public static void main(String[] args) throws IOException{
        ninja = ImageIO.read(new File("ninja.png"));

        JFrame frame = new JFrame("Lesson 6 - Runnable");
        Lesson_6_Runnable panel = new Lesson_6_Runnable();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void run(){
        while(true){
            // 1) Screen Freeze
            try{
                Thread.sleep(20);
            }
            catch(Exception e){}

            // 2) Redraw the screen
            repaint();

            // NOTE: If you implement runnable, there should only be
            //       one 'repaint()' command in your ENTIRE program
        }
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            wPressed = true;
        }
        if(e.getKeyChar() == 'a'){
            aPressed = true;
        }
        if(e.getKeyChar() == 's'){
            sPressed = true;
        }
        if(e.getKeyChar() == 'd'){
            dPressed = true;
        }
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            wPressed = false;
        }
        if(e.getKeyChar() == 'a'){
            aPressed = false;
        }
        if(e.getKeyChar() == 's'){
            sPressed = false;
        }
        if(e.getKeyChar() == 'd'){
            dPressed = false;
        }
    }

    // Useless Methods
    public void keyTyped(KeyEvent e){}
}
