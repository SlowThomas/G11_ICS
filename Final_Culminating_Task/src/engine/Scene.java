package engine;
import java.awt.*;
import java.awt.image.*;
import java.nio.Buffer;

import algebra.*;


public class Scene {

    private static class Consts {
        public static double distance = 1000; // in pixel
        // taken the midpoint in the range of recommended eye-screen distance
        public static double view_distance = 10000; // in inch, about 0.25 km

        // Consts for operation
        public static double epsilon = 1E-10; // another choice: 1E-14
        private final double pi = Math.PI;

    }

    private static class Screen{
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

    private static class Algorithm {
       private static final double epsilon = Consts.epsilon;

        private static double[] barycentric(double x, double y, double[][] triangle){
            // barycentric coordinate
            double t = Math.max(epsilon, Math.abs((triangle[0][0] - triangle[2][0]) * (triangle[1][1] - triangle[2][1]) - (triangle[0][1] - triangle[2][1]) * (triangle[1][0] - triangle[2][0])));
            double l1 = Math.abs((triangle[0][0] - x) * (triangle[1][1] - y) - (triangle[0][1] - y) * (triangle[1][0] - x)) / t;
            double l2 = Math.abs((triangle[1][0] - x) * (triangle[2][1] - y) - (triangle[1][1] - y) * (triangle[2][0] - x)) / t;
            double l3 = Math.abs((triangle[2][0] - x) * (triangle[0][1] - y) - (triangle[2][1] - y) * (triangle[0][0] - x)) / t;
            return new double[]{l1, l2, l3};
        }

        public static boolean edgeFunction(double ax, double ay, double bx, double by, double px, double py) {
            return (px - ax) * (by - ay) - (py - ay) * (bx - ax) >= 0;
        }

        public static boolean point_in_triangle(double px, double py, double[][] triangle){
            boolean inside = true;
            // inside &= edgeFunction(V0, V1, p);
            // inside &= edgeFunction(V1, V2, p);
            // inside &= edgeFunction(V2, V0, p);
            // inside &= edgeFunction(triangle[0][0], triangle[0][1], triangle[1][0], triangle[1][1], px, py);
            // inside &= edgeFunction(triangle[1][0], triangle[1][1], triangle[2][0], triangle[2][1], px, py);
            // inside &= edgeFunction(triangle[2][0], triangle[2][1], triangle[0][0], triangle[0][1], px, py);
            boolean a = edgeFunction(triangle[0][0], triangle[0][1], triangle[1][0], triangle[1][1], px, py);
            boolean b = edgeFunction(triangle[1][0], triangle[1][1], triangle[2][0], triangle[2][1], px, py);
            boolean c = edgeFunction(triangle[2][0], triangle[2][1], triangle[0][0], triangle[0][1], px, py);
            return a == b && b == c;
        }

        public static double z_buff(double x, double y, double[][] triangle){
            // barycentric coordinate using the inverses. z_buffer's are linear to each other in terms of their inverses
            if(Math.abs(triangle[0][2]) < epsilon || Math.abs(triangle[1][2]) < epsilon || Math.abs(triangle[2][2]) < epsilon)
                return Math.max(triangle[0][2], Math.max(triangle[1][2], triangle[2][2]));
            double[] weight = barycentric(x, y, triangle);
            double z = weight[0] / triangle[2][2] + weight[1] / triangle[0][2] + weight[2] / triangle[1][2];
            if(Math.abs(z) < epsilon)
                return Math.max(triangle[0][2], Math.max(triangle[1][2], triangle[2][2]));
            return 1 / z;
        }

        private static double[][] getProjected(Camera camera, Obj obj, Screen screen, int[] vertex_idx) {
            Vector v0 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[0] - 1])));
            Vector v1 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[1] - 1])));
            Vector v2 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[2] - 1])));

            double z0 = Math.max(epsilon, v0.at(2));
            double z1 = Math.max(epsilon, v1.at(2));
            double z2 = Math.max(epsilon, v2.at(2));

            return new double[][]{
                    {
                        Consts.distance * (v0.at(0) + (double)screen.width / 2) / z0,
                        Consts.distance * (v1.at(0) + (double)screen.width / 2) / z1,
                        Consts.distance * (v2.at(0) + (double)screen.width / 2) / z2
                    },
                    {
                        Consts.distance * ((double)screen.height / 2 - v0.at(1)) / z0,
                        Consts.distance * ((double)screen.height / 2 - v1.at(1)) / z1,
                        Consts.distance * ((double)screen.height / 2 - v2.at(1)) / z2
                    },
                    {z0, z1, z2}
            };
        }

        // vertices are transformed into camera space before this
        public static void rasterize(Camera camera, Obj obj, Screen screen){
            //public static void rasterize(Matrix vertices, int color, Screen screen){
            for(int f_idx = 0; f_idx < obj.f.length; f_idx++){
                double[][] vect2 = getProjected(camera, obj, screen, obj.f[f_idx]);

                int left = Math.min(Math.max((int)Math.min(Math.min(vect2[0][0], vect2[0][1]), vect2[0][2]), 0), screen.width - 1);
                int right = Math.min(Math.max((int)Math.max(Math.max(vect2[1][0], vect2[1][1]), vect2[1][2]), 0), screen.width - 1);
                int top = Math.min(Math.max((int)Math.min(Math.min(vect2[1][0], vect2[1][1]), vect2[1][2]), 0), screen.height - 1);
                int bottom = Math.min(Math.max((int)Math.max(Math.max(vect2[1][0], vect2[1][1]), vect2[1][2]), 0), screen.height - 1);

                double[] color = obj.material.get_Kd(obj.mtl[f_idx]);

                double z;
                for(int i = left; i <= right; i++){
                    for(int j = top; j <= bottom; j++){
                        if(point_in_triangle(i, j, vect2)){
                            z = z_buff(i, j, vect2);
                            //if(z >= screen.z_buffer[i][j]) continue;
                            // TODO: Surface normal: point out or point in decides color and visibility
                            screen.colo[i][j] =
                                        (int)(color[0] * 255) * (1 << 16)
                                    +   (int)(color[1] * 255) * (1 << 8)
                                    +   (int)(color[2] * 255);
                            screen.z_buffer[i][j] = z;
                        }
                    }
                }
            }
        }
    }


    private Obj[] obj_list;
    private Camera[] cameras;
    public int view_idx = 0;

    private Screen screen = new Screen(800, 450);

    public Scene(Camera[] cameras, Obj[] obj_list){
        this.obj_list = obj_list;
        this.cameras = cameras;
    }

    // canvas declaration: BufferedImage img = new BufferedImage(Consts.width, Consts.height, BufferedImage.TYPE_INT_RGB);
    public BufferedImage render(){
        BufferedImage canvas = new BufferedImage(screen.width, screen.height, BufferedImage.TYPE_INT_RGB);

        flush();

        for(Obj obj : obj_list){
            Algorithm.rasterize(cameras[view_idx], obj, screen);
        }

        for(int x = 0; x < canvas.getWidth(); x++){
            for(int y = 0; y < canvas.getHeight(); y++){
                canvas.setRGB(x, y, screen.colo[x][y]);
            }
        }

        return canvas;
    }

    public void flush(){
        this.screen = new Screen(800, 450);
    }
}
