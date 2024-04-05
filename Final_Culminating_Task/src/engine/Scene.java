package engine;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import algebra.*;

class Algorithm {
    public static void rasterize(Matrix vertices, Vector colors, byte[][][] screen){
        // TODO: update the projection matrix
        Matrix vect2 = (new Matrix({{}})).dot(vertices);

        int l = Math.max((int)Math.min(Math.min(vect2.at(0, 0), vect2.at(0, 1)), vect2.at(0, 2)), 0);
        int r = Math.min((int)Math.max(Math.max(vect2.at(1, 0), vect2.at(1, 1)), vect2.at(1, 2)), screen.length - 1);
        int t = Math.max((int)Math.min(Math.min(vect2.at(1, 0), vect2.at(1, 1)), vect2.at(1, 2)), 0);
        int b = Math.min((int)Math.max(Math.max(vect2.at(1, 0), vect2.at(1, 1)), vect2.at(1, 2)), screen[0].length - 1);

    }
}

public class Scene {
    // Consider:
    // Light intensity
    // Z buffer as a pair (adjusted color, depth)
    // rotation by re-shifting rotation axis to a primary axis

    // TODO: raw rendering - just use the rendering technique from Dash
    // TODO: partition into different sections



    private int[] size;
    private int ppi;
    private Object[] obj_list;


    private byte[][][] screen;
    public Scene(int width, int length, int height, int view_distance, int ppi, Object[] obj_list){
        this.ppi = ppi;
        this.obj_list = obj_list;
        this.size = new int[]{width, length, height};
    }

    public void render(Graphics g){
        BufferedImage img = new BufferedImage(Screen.width, Screen.height, BufferedImage.TYPE_INT_RGB);

        for(Object obj : obj_list){
            for(int i = 0; i < obj.faces.length; i++){
                Algorithm.rasterize(obj.faces[i], obj.colo[i], screen);
            }
        }

        /*
        for ( int rc = 0; rc < Screen.height; rc++ ) {
            for ( int cc = 0; cc < Screen.width; cc++ ) {
                // Set the pixel colour of the image n.b. x = cc, y = rc
                img.setRGB(cc, rc, Color.BLACK.getRGB() );
            }
        }
         */
    }
    // Surface normal: point out or point in decides color and visibility
}
