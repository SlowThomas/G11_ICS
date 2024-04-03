package engine;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

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
                Algorithms.floodFill(obj.faces[i], obj.colo[i], screen);
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
