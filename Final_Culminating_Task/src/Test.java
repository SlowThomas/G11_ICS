import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Test extends JPanel{
    public Test(){
        // JPanel Default Settings
        setPreferredSize(new Dimension(600,480));
        setBackground(new Color(0,0,0));
    }

    public void paintComponent(Graphics g){
    }

    public static void t1(){
        System.out.println("t1");
    }

    public static void main(String[] args){
        t1();
        t2();
    }

    public static void t2(){
        System.out.println("t2");
    }
}

class T2 extends JPanel{

}