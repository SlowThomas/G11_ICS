package engine;
import java.awt.*;
import java.awt.image.*;
import algebra.*;
class Consts {
    // MacBook Air display: 13.3-inch 1440 × 900
    public static int width = 1440;
    public static int height = 900;
    public static double size = 13.3; // in inch
    public static int ppi = (int)(Math.sqrt(width * width + height * height) / size) + 1;

    public static double distance = 30; // in inch
    // taken the midpoint in the range of recommended eye-screen distance
<<<<<<< HEAD
    public static double view_distance = 10000; // in inch, about 0.25 km
=======
    public static double view_distance = 10000 // in inch, about 0.25 km
>>>>>>> 2c31dadabf7df6661a9b20177f5ea6e7d9339353
    
    // Consts for operation
    public static double epsilon = 1E-10; // another choice: 1E-14
    private final double pi = Math.PI;

}
class Algorithm {
    private static final double epsilon = 1E-10; // another choice: 1E-14

    public static boolean edgeFunction(double ax, double ay, double bx, double by, double px, double py) {
        return (px - ax) * (by - ay) - (py - ay) * (bx - ax) >= 0;
    }

    public static boolean point_in_triangle(double px, double py, double[][] triangle){
        boolean inside = true;
        // inside &= edgeFunction(V0, V1, p);
        // inside &= edgeFunction(V1, V2, p);
        // inside &= edgeFunction(V2, V0, p);
        inside &= edgeFunction(triangle[0][0], triangle[0][1], triangle[1][0], triangle[1][1], px, py);
        inside &= edgeFunction(triangle[1][0], triangle[1][1], triangle[2][0], triangle[2][1], px, py);
        inside &= edgeFunction(triangle[2][0], triangle[2][1], triangle[0][0], triangle[0][1], px, py);
        return inside;
    }
    public static void rasterize(Matrix vertices, Vector color, byte[][][] screen){
        /*double[][] vect2 = {
                {Consts.distance * vertices.at(0, 0) / vertices.at(0, 2), Consts.distance * vertices.at(1, 0) / vertices.at(1, 2), Consts.distance * vertices.at(2, 0) / vertices.at(2, 2)},
                {Consts.distance * vertices.at(0, 1) / vertices.at(0, 2), Consts.distance * vertices.at(1, 1) / vertices.at(1, 2), Consts.distance * vertices.at(2, 1) / vertices.at(2, 2)},
        };*/
        Matrix proj = (new Matrix(new double[][]{
            {Consts.distance, 0, 0, 0},
            {0, Consts.distance, 0, 0},
            {0, 0, Consts.distance + Consts.view_distance, -Consts.distance * Consts.view_distance},
            {0, 0, 1, 0}
        })).dot(vertices);

        double[][] vect2 = {
            {proj.at(0, 0) / proj.at(0, 3), proj.at(1, 0) / proj.at(1, 3), proj.at(2, 0) / proj.at(2, 3)},
            {proj.at(0, 1) / proj.at(0, 3), proj.at(1, 1) / proj.at(1, 3), proj.at(2, 1) / proj.at(2, 3)}
        };

        // Generate the equation of the plane: ax + by + cz = d
        Vector normal = vertices.at(0).subtract(vertices.at(1)).cross(vertices.at(0).subtract(vertices.at(2)));
        double a = normal.at(0);
        double b = normal.at(1);
        double c = normal.at(2);
        double d = a * vertices.at(0, 0) + b * vertices.at(0, 1) + c * vertices.at(0, 2);

        int left = Math.max((int)Math.min(Math.min(vect2[0][0], vect2[0][1]), vect2[0][2]), 0);
        int right = Math.min((int)Math.max(Math.max(vect2[1][0], vect2[1][1]), vect2[1][2]), screen.length);
        int top = Math.max((int)Math.min(Math.min(vect2[1][0], vect2[1][1]), vect2[1][2]), 0);
        int bottom = Math.min((int)Math.max(Math.max(vect2[1][0], vect2[1][1]), vect2[1][2]), screen[0].length);

        for(int i = left; i < right; i++){
            for(int j = top; j < bottom; j++){
                if(point_in_triangle(i, j, vect2) && Math.abs(c) > epsilon){
                    // r, g, b, z-buffer
                    screen[i][j] = new byte[]{
                            (byte)color.at(0), (byte)color.at(1), (byte)color.at(2),
                            };
                }
            }
        }
    }
}

public class Scene {
    // Consider:
    // Light intensity
    // Z buffer as a pair (adjusted color, depth)
    // rotation by re-shifting rotation axis to a primary axis

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
        BufferedImage img = new BufferedImage(Consts.width, Consts.height, BufferedImage.TYPE_INT_RGB);

        for(Object obj : obj_list){
            for(int i = 0; i < obj.faces.length; i++){
                Algorithm.rasterize(obj.faces[i], obj.colo[i], screen);
            }
        }

        /*
        for ( int rc = 0; rc < Consts.height; rc++ ) {
            for ( int cc = 0; cc < Consts.width; cc++ ) {
                // Set the pixel colour of the image n.b. x = cc, y = rc
                img.setRGB(cc, rc, Color.BLACK.getRGB() );
            }
        }
         */
    }
    // Surface normal: point out or point in decides color and visibility
}
