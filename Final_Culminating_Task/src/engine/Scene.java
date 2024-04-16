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
    public static double view_distance = 10000; // in inch, about 0.25 km
    
    // Consts for operation
    public static double epsilon = 1E-10; // another choice: 1E-14
    private final double pi = Math.PI;

}

class Screen{
    public int[][] colo;
    public double[][] z_buffer;
    public int width;
    public int height;

    public Screen(int width, int height){
        colo = new int[width][height];
        z_buffer = new double[width][height];
        this.width = width;
        this.height = height;
    }
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

    public static double z_buff(double x, double y, double[][] triangle){
        // barycentric coordinate using the inverses. z_buffer's are linear to each other in terms of their inverses
        double t = (triangle[0][0] - triangle[2][0]) * (triangle[1][1] - triangle[2][1]) - (triangle[0][1] - triangle[2][1]) * (triangle[1][0] - triangle[2][0]);
        if(Math.abs(t) < epsilon || Math.abs(triangle[0][2]) < epsilon || Math.abs(triangle[1][2]) < epsilon || Math.abs(triangle[2][2]) < epsilon) return triangle[0][2];
        double l1 = ((triangle[0][0] - x) * (triangle[1][1] - y) - (triangle[0][1] - y) * (triangle[1][0] - x)) / t;
        double l2 = ((triangle[1][0] - x) * (triangle[2][1] - y) - (triangle[1][1] - y) * (triangle[2][0] - x)) / t;
        double l3 = ((triangle[2][0] - x) * (triangle[0][1] - y) - (triangle[2][1] - y) * (triangle[0][0] - x)) / t;
        double z = l1 / triangle[2][2] + l2 / triangle[0][2] + l3 / triangle[1][2];
        if(Math.abs(z) < epsilon) return triangle[0][2];
        return 1 / z;
    }

    // vertices are transformed into camera space before this
    public static void rasterize(Matrix vertices, int color, Screen screen){
        double z0 = Math.max(epsilon, vertices.at(0, 2));
        double z1 = Math.max(epsilon, vertices.at(1, 2));
        double z2 = Math.max(epsilon, vertices.at(2, 2));

        double[][] vect2 = {
                {Consts.distance * vertices.at(0, 0) / z0, Consts.distance * vertices.at(1, 0) / z1, Consts.distance * vertices.at(2, 0) / z2},
                {Consts.distance * vertices.at(0, 1) / z0, Consts.distance * vertices.at(1, 1) / z1, Consts.distance * vertices.at(2, 1) / z2},
                {z0, z1, z2}
        };

        int left = Math.max((int)Math.min(Math.min(vect2[0][0], vect2[0][1]), vect2[0][2]), 0);
        int right = Math.min((int)Math.max(Math.max(vect2[1][0], vect2[1][1]), vect2[1][2]), screen.width);
        int top = Math.max((int)Math.min(Math.min(vect2[1][0], vect2[1][1]), vect2[1][2]), 0);
        int bottom = Math.min((int)Math.max(Math.max(vect2[1][0], vect2[1][1]), vect2[1][2]), screen.height);

        double z;
        for(int i = left; i < right; i++){
            for(int j = top; j < bottom; j++){
                if(point_in_triangle(i, j, vect2)){
                    z = z_buff(i, j, vect2);
                    if(z >= screen.z_buffer[i][j]) continue;
                    // TODO: Surface normal: point out or point in decides color and visibility
                    // r, g, b
                    screen.colo[i][j] = color;
                    screen.z_buffer[i][j] = z;
                }
            }
        }
    }
}

public class Scene {
    // Consider:
    // Light intensity

    private int[] size;
    private int ppi;
    private Object[] obj_list;

    private Screen screen;

    public Scene(int width, int length, int height, int view_distance, int ppi, Object[] obj_list){
        this.ppi = ppi;
        this.obj_list = obj_list;
        this.size = new int[]{width, length, height};
    }

    // canvas declearation: BufferedImage img = new BufferedImage(Consts.width, Consts.height, BufferedImage.TYPE_INT_RGB);
    public void render(BufferedImage canvas){
        
        for(Object obj : obj_list){
            for(int i = 0; i < obj.faces.length; i++){
                Algorithm.rasterize(obj.faces[i], obj.colo[i], screen);
            }
        }

        for(int x = 0; x < canvas.getWidth(); x++){
            for(int y = 0; y < canvas.getHeight(); y++){
                canvas.setRGB(x, y, screen.colo[x][y]);
            }
        }

    }
}
