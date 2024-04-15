import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;

public class Lesson_2_ImportingImages extends JPanel{

    // Class Variables
    // Class variables are a type of GLOBAL VARIABLES
    // that are accessible in any methods inside this java class file
    public static BufferedImage ninja;

    public Lesson_2_ImportingImages(){
        setPreferredSize(new Dimension(600, 480));
    }

    public void paintComponent(Graphics g){
        g.drawImage(ninja, 275, 215, null);
    }

    public static void main(String[] args) throws IOException{

        ninja = ImageIO.read(new File("ninja.png"));

        JFrame frame = new JFrame("Lesson 2");
        Lesson_2_ImportingImages panel = new Lesson_2_ImportingImages();
        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }
}
