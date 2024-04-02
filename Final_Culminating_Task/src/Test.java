import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import algebra.*;

public class Test extends JPanel{

    public Test()
    {
        // JPanel Default Settings
        setPreferredSize(new Dimension(600,480));
        setBackground(new Color(0,0,0));
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1, 1);
    }
    public static void main(String[] args){
        JFrame f = new JFrame("test");
        JPanel t = new Test();

        f.add(t);
        f.pack();
        f.setVisible(true);
    }
}