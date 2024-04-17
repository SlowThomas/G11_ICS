import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Lesson_3_KeyListener extends JPanel implements KeyListener{
    // Class Variables
    public static BufferedImage ninja;
    public static int ninjaX = 275;
    public static int ninjaY = 215;

    public Lesson_3_KeyListener(){
        setPreferredSize(new Dimension(600, 480));
        this.setFocusable(true);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g){
        // Clear screen command
        super.paintComponent(g);

        g.drawImage(ninja, ninjaX, ninjaY, null);
    }

    // Key Listener Methods:
    // The computer needs to know what commands to execute
    // for the following 3 situations:
    // keyPressed, keyReleased, keyTyped
    // You MUST have these 3 MANDATORY methods, otherwise
    // the program will not run
    // You can choose to not fill in the commands if you do
    // not plan on using it
    public void keyPressed(KeyEvent e){
        // Capture WASD such that we can move ninja
        // e.getKeyChar() returns the character of the key that pressed
        if(e.getKeyChar() == 'w'){
            ninjaY -= 5;
            // NEVER use this command to redraw:
            // paintComponent(this.getGraphics());
            // The command repaint() will call paintComponent method
            repaint();
        }
        if(e.getKeyChar() == 'a'){
            ninjaX -= 5;
            repaint();
        }
        if(e.getKeyChar() == 's'){
            ninjaY += 5;
            repaint();
        }
        if(e.getKeyChar() == 'd'){
            ninjaX += 5;
            repaint();
        }
    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}

    public static void main(String[] args) throws IOException {

        ninja = ImageIO.read(new File("ninja.png"));

        JFrame frame = new JFrame("Lesson 3 - Key Listener");
        Lesson_3_KeyListener panel = new Lesson_3_KeyListener();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
