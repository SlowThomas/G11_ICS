package engine;
import java.awt.*;
import java.awt.image.BufferedImage;

import algebra.*;


public class Scene {

    private static class Consts {
        public static double distance = 590; // in mm
        // taken the midpoint in the range of recommended eye-screen distance
        // 800 pixels = 220 mm
        public static double view_distance = 10000; // in inch, about 0.25 km

        // Consts for operation
        public static double epsilon = 1E-10; // another choice: 1E-14
        private final double pi = Math.PI;

    }

    private static class Screen{
        public int[][] colo;
        public double[][] z_buffer;
        public boolean[][] z_buffed;
        public int width;
        public int height;

        public Screen(int width, int height){
            this.width = width;
            this.height = height;
            colo = new int[this.width][this.height];
            z_buffer = new double[this.width][this.height];
            z_buffed = new boolean[this.width][this.height];
        }
    }

    private static class Algorithm {
       private static final double epsilon = Consts.epsilon;

        private static double[] barycentric(double x, double y, double[][] triangle){
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
            boolean a = edgeFunction(triangle[0][0], triangle[0][1], triangle[1][0], triangle[1][1], px, py);
            boolean b = edgeFunction(triangle[1][0], triangle[1][1], triangle[2][0], triangle[2][1], px, py);
            boolean c = edgeFunction(triangle[2][0], triangle[2][1], triangle[0][0], triangle[0][1], px, py);
            return !((a ^ b) | (b ^ c));
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

        private static double[][] getProjected(Camera camera, Obj obj, Screen screen, double pixel_per_mm, int[] vertex_idx) {
            Vector v0 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[0] - 1])));
            Vector v1 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[1] - 1])));
            Vector v2 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[2] - 1])));

            double z0 = Math.max(epsilon, v0.at(2));
            double z1 = Math.max(epsilon, v1.at(2));
            double z2 = Math.max(epsilon, v2.at(2));

            return new double[][]{
                    {
                            (double)screen.width / 2 + Consts.distance * v0.at(0) / z0 * pixel_per_mm,
                            (double)screen.height / 2 - Consts.distance * v0.at(1) / z0 * pixel_per_mm,
                            z0
                    },
                    {
                            (double)screen.width / 2 + Consts.distance * v1.at(0) / z1 * pixel_per_mm,
                            (double)screen.height / 2 - Consts.distance * v1.at(1) / z1 * pixel_per_mm,
                            z1
                    },
                    {
                            (double)screen.width / 2 + Consts.distance * v2.at(0) / z2 * pixel_per_mm,
                            (double)screen.height / 2 - Consts.distance * v2.at(1) / z2 * pixel_per_mm,
                            z2
                    }
            };
        }

        public static void rasterize(Camera camera, Obj obj, Screen screen, double resolution){
            for(int f_idx = 0; f_idx < obj.f.length; f_idx++){
                double[][] vect2 = getProjected(camera, obj, screen, resolution, obj.f[f_idx]);

                int left = Math.min(Math.max((int)Math.min(Math.min(vect2[0][0], vect2[1][0]), vect2[2][0]), 0), screen.width - 1);
                int right = Math.min(Math.max((int)Math.max(Math.max(vect2[0][0], vect2[1][0]), vect2[2][0]), 0), screen.width - 1);
                int top = Math.min(Math.max((int)Math.min(Math.min(vect2[0][1], vect2[1][1]), vect2[2][1]), 0), screen.height - 1);
                int bottom = Math.min(Math.max((int)Math.max(Math.max(vect2[0][1], vect2[1][1]), vect2[2][1]), 0), screen.height - 1);

                double[] color = obj.material.get_Kd(obj.mtl[f_idx]);

                // TODO: multi-thread - partition left to right
                double z;
                for(int i = left; i <= right; i++){
                    for(int j = top; j <= bottom; j++){
                        if(point_in_triangle(i, j, vect2)){
                            z = z_buff(i, j, vect2);
                            if(z < Consts.distance || screen.z_buffed[i][j] && z >= screen.z_buffer[i][j]) continue;
                            // TODO: Surface normal: point out or point in decides color and visibility
                            screen.colo[i][j] =
                                        (int)(color[0] * 255) * (1 << 16)
                                    +   (int)(color[1] * 255) * (1 << 8)
                                    +   (int)(color[2] * 255);
                            screen.z_buffer[i][j] = z;
                            screen.z_buffed[i][j] = true;
                        }
                    }
                }
            }
        }

        public static void scale(Camera camera, Flat_Obj obj, Screen screen){
            Vector pos = camera.T_inverse.dot(obj.pos);
            double z = pos.at(2);

            if(z < Consts.distance) return;

            int width = (int)(obj.img.getWidth() * Consts.distance / z);
            int height = (int)(obj.img.getHeight() * Consts.distance / z);

            double x = screen.width / 2.0 + pos.at(0) * Consts.distance / z;
            double y = screen.height / 2.0 - pos.at(1) * Consts.distance / z;

            int l = (int)x - width / 2;
            int r = (int)x + width / 2 - (width + 1) % 2;
            int t = (int)y - height / 2;
            int b = (int)y + height / 2 - (height + 1) % 2;

            if(r < 0 || l > screen.width || b < 0 || t > screen.height) return;

            BufferedImage img = (BufferedImage) obj.img.getScaledInstance(width, height, Image.SCALE_FAST);

            for(int i = l; i <= r; i++){
                for(int j = t; j <= b; j++){
                    if(i < 0 || j < 0 || i >= screen.width || j >= screen.height ||
                            screen.z_buffed[i][j] && z > screen.z_buffer[i][j]) continue;
                    screen.colo[i][j] = img.getRGB(i - l, j - t);
                    screen.z_buffed[i][j] = true;
                    screen.z_buffer[i][j] = z;
                }
            }
        }
    }


    private Obj[] obj_list = new Obj[0];
    private Flat_Obj[] flat_objs = new Flat_Obj[0];
    private Camera[] cameras = new Camera[0];
    public int view_idx = 0;

    private Screen screen;

    private int width;
    private int height;
    private double resolution;
    public BufferedImage canvas;

    public Scene(int width, int height, double resolution, Camera[] cameras, Obj[] obj_list, Flat_Obj[] flat_objs){
        this.obj_list = obj_list;
        this.flat_objs = flat_objs;
        this.cameras = cameras;
        this.width = width;
        this.height = height;
        this.resolution = resolution; // resolution in pixel per mm

        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 800 pixels = 220 mm
        screen = new Screen((int)(width * 0.275 * resolution), (int)(height * 0.275 * resolution));
    }

    public void render(){
        for(Obj obj : obj_list){
            Algorithm.rasterize(cameras[view_idx], obj, screen, resolution);
        }

        for(Flat_Obj obj : flat_objs){
            Algorithm.scale(cameras[view_idx], obj, screen);
        }

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                canvas.setRGB(x, y, screen.colo[x * screen.width / width][y * screen.height / height]);
            }
        }

        flush();
    }

    public void flush(){
        for(int i = 0; i < screen.width; i++)
            for(int j = 0; j < screen.height; j++)
                screen.colo[i][j] = 0;
        for(int i = 0; i < screen.width; i++)
            for(int j = 0; j < screen.height; j++)
                screen.z_buffed[i][j] = false;
    }
}
