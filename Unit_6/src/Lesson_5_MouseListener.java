import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Lesson_5_MouseListener extends JPanel implements MouseListener{
    // Class Variables
    public static BufferedImage screen1;
    public static BufferedImage screen2;
    public static int gameState = 1;

    // Default Setting for JPanel
    public Lesson_5_MouseListener(){
        setPreferredSize(new Dimension(400, 400));
        this.setFocusable(true);
        addMouseListener(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // Clears the screen
        if(gameState == 1){
            g.drawImage(screen1, 0, 0, null);
        }
        else if(gameState == 2){
            g.drawImage(screen2, 0, 0, null);
        }
    }

    public static void main(String[] args) throws IOException{
        // Image Importation
        screen1 = ImageIO.read(new File("screen1.png"));
        screen2 = ImageIO.read(new File("screen2.png"));

        JFrame frame = new JFrame("Lesson 5 - Mouse Listener");
        Lesson_5_MouseListener panel = new Lesson_5_MouseListener();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void mouseExited(MouseEvent e) {
        if(gameState == 2){
            gameState = 1;
            repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        if(gameState == 1){
            // The commands to retrieve the x and y coordinates of the mouse press
            // e.getX() && e.getY()
            if(e.getX() >= 100 && e.getX() <= 300 &&
               e.getY() >= 100 && e.getY() <= 200 &&
               e.getButton() == 1){ // Button 1 is Left Click
                gameState = 2;
                repaint();
            }
        }
    }

    // Useless Methods
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
}
