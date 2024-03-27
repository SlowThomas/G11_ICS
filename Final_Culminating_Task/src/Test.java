import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import algebra.*;

public class Test extends JPanel{
    public Test(){
        // JPanel Default Settings
        setPreferredSize(new Dimension(600,480));
        setBackground(new Color(0,0,0));
    }

    public void paintComponent(Graphics g){
    }



    public static void main(String[] args){
        double[] arr = {3, 2, 1};
        Vector u = new Vector(1, 2, 3), v = new Vector(arr);
        System.out.println(u.dot(v));
        u = v;
        System.out.println(u.dot(v) == u.mag * u.mag);
    }
}