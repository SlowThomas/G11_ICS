import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import algebra.*;

public class Test{
    public static void main(String[] args){
        Vector v;
        {
            double[] a = {1, 2, 3};
            v = new Vector(a);
        }
        System.out.println(v.at(0));
        System.out.println(v.at(1));
        System.out.println(v.at(2));
    }
}