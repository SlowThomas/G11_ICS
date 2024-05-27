package engine;

import algebra.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Flat_Obj {

    public BufferedImage img;
    public double scale = 1;

    public Vector pos = new Vector(0, 0, 0, 1);

    public Flat_Obj(String filename){
        try{
            img = ImageIO.read(new File("img/" + filename));
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void move(Vector trail){
        pos = new Matrix(new double[][]{
                {1, 0, 0, trail.at(0)},
                {0, 1, 0, trail.at(1)},
                {0, 0, 1, trail.at(2)},
                {0, 0, 0, 1}
        }).dot(pos);
    }

    public void move(double x, double y, double z){
        pos = new Matrix(new double[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        }).dot(pos);
    }

    public void cd(Vector destination){
        move(destination.subtract(pos));
    }

    public void scale(double scale){
        this.scale *= scale;
    }
}
