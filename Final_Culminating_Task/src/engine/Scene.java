package engine;
import java.awt.image.BufferedImage;

import algebra.*;


public class Scene {

    private static class Consts {
        public static float distance = 590; // in mm
        // taken the midpoint in the range of recommended eye-screen distance
        // 800 pixels = 220 mm

        // Consts for operation
        public static float epsilon = 1E-10F; // another choice: 1E-14
    }

    private static class Screen{
        public int[][] colo;
        public float[][] z_buffer;
        public boolean[][] z_buffed;
        public int width;
        public int height;

        public Screen(int width, int height){
            this.width = width;
            this.height = height;
            colo = new int[this.width][this.height];
            z_buffer = new float[this.width][this.height];
            z_buffed = new boolean[this.width][this.height];
        }
    }


    private static class Algorithm {
        private static final float epsilon = Consts.epsilon;


        private static float[] barycentric(float x, float y, float[][] triangle){
            float t = Math.max(epsilon, Math.abs((triangle[0][0] - triangle[2][0]) * (triangle[1][1] - triangle[2][1]) - (triangle[0][1] - triangle[2][1]) * (triangle[1][0] - triangle[2][0])));
            float l1 = Math.abs((triangle[0][0] - x) * (triangle[1][1] - y) - (triangle[0][1] - y) * (triangle[1][0] - x)) / t;
            float l2 = Math.abs((triangle[1][0] - x) * (triangle[2][1] - y) - (triangle[1][1] - y) * (triangle[2][0] - x)) / t;
            float l3 = Math.abs((triangle[2][0] - x) * (triangle[0][1] - y) - (triangle[2][1] - y) * (triangle[0][0] - x)) / t;
            return new float[]{l1, l2, l3};
        }

        public static boolean edgeFunction(float ax, float ay, float bx, float by, float px, float py) {
            return (px - ax) * (by - ay) - (py - ay) * (bx - ax) >= 0;
        }

        public static boolean point_in_triangle(float px, float py, float[][] triangle){
            boolean a = edgeFunction(triangle[0][0], triangle[0][1], triangle[1][0], triangle[1][1], px, py);
            boolean b = edgeFunction(triangle[1][0], triangle[1][1], triangle[2][0], triangle[2][1], px, py);
            boolean c = edgeFunction(triangle[2][0], triangle[2][1], triangle[0][0], triangle[0][1], px, py);
            return !((a ^ b) | (b ^ c));
        }

        public static float z_buff(float x, float y, float[][] triangle){
            // barycentric coordinate using the inverses. z_buffer's are linear to each other in terms of their inverses
            // TODO: if t is too small, use a linear model instead
            float t = Math.max(epsilon, Math.abs((triangle[0][0] - triangle[2][0]) * (triangle[1][1] - triangle[2][1]) - (triangle[0][1] - triangle[2][1]) * (triangle[1][0] - triangle[2][0])));
            return 1 / Math.max(epsilon, (Math.abs((triangle[0][0] - x) * (triangle[1][1] - y) - (triangle[0][1] - y) * (triangle[1][0] - x)) / t) / triangle[2][2]
                    + (Math.abs((triangle[1][0] - x) * (triangle[2][1] - y) - (triangle[1][1] - y) * (triangle[2][0] - x)) / t) / triangle[0][2]
                    + (Math.abs((triangle[2][0] - x) * (triangle[0][1] - y) - (triangle[2][1] - y) * (triangle[0][0] - x)) / t) / triangle[1][2]);


        }

        private static void getProjected(Camera camera, Real_Obj obj, Screen screen, float pixel_per_mm, int[] vertex_idx, float[][] vect2) {
            Vector v0 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[0] - 1])));
            Vector v1 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[1] - 1])));
            Vector v2 = camera.T_inverse.dot(obj.T_world.dot(obj.T_model.dot(obj.v[vertex_idx[2] - 1])));

            float z0 = v0.at(2);
            float z1 = v1.at(2);
            float z2 = v2.at(2);

            vect2[0][0] = vect2[1][0] = vect2[2][0] = (float)screen.width / 2;
            vect2[0][1] = vect2[1][1] = vect2[2][1] = (float)screen.height / 2;
            if(z0 > epsilon){
                vect2[0][0] += Consts.distance * v0.at(0) / z0 * pixel_per_mm;
                vect2[0][1] -= Consts.distance * v0.at(1) / z0 * pixel_per_mm;
            }
            else{
                vect2[0][0] += v0.at(0) / epsilon;
                vect2[0][1] -= v0.at(1) / epsilon;
            }
            vect2[0][2] = z0;
            if(z1 > epsilon) {
                vect2[1][0] += Consts.distance * v1.at(0) / z1 * pixel_per_mm;
                vect2[1][1] -= Consts.distance * v1.at(1) / z1 * pixel_per_mm;
            }
            else{
                vect2[1][0] += v1.at(0) / epsilon;
                vect2[1][1] -= v1.at(1) / epsilon;
            }
            vect2[1][2] = z1;
            if(z2 > epsilon) {
                vect2[2][0] += Consts.distance * v2.at(0) / z2 * pixel_per_mm;
                vect2[2][1] -= Consts.distance * v2.at(1) / z2 * pixel_per_mm;
            }
            else{
                vect2[2][0] += v2.at(0) / epsilon;
                vect2[2][1] -= v2.at(1) / epsilon;
            }
            vect2[2][2] = z2;

        }

        public static void rasterize(Camera camera, Real_Obj obj, Screen screen, float resolution){
            float[][] vect2 = new float[3][3];
            for (int f_idx = 0; f_idx < obj.f.length; f_idx++) {
                getProjected(camera, obj, screen, resolution, obj.f[f_idx], vect2);

                int color = obj.material.get_Kd(obj.mtl[f_idx]);


                int left = Math.min(Math.max((int)Math.min(Math.min(vect2[0][0], vect2[1][0]), vect2[2][0]), 0), screen.width - 1);
                int right = Math.min(Math.max((int)Math.max(Math.max(vect2[0][0], vect2[1][0]), vect2[2][0]), 0), screen.width - 1);
                int top = Math.min(Math.max((int)Math.min(Math.min(vect2[0][1], vect2[1][1]), vect2[2][1]), 0), screen.height - 1);
                int bottom = Math.min(Math.max((int)Math.max(Math.max(vect2[0][1], vect2[1][1]), vect2[2][1]), 0), screen.height - 1);

                float z;
                for (int i = left; i <= right; i++) {
                    for (int j = top; j <= bottom; j++) {
                        if (point_in_triangle(i, j, vect2)) {
                            z = z_buff(i, j, vect2);
                            if (z < Consts.distance || screen.z_buffed[i][j] && z >= screen.z_buffer[i][j])
                                continue;
                            screen.colo[i][j] = color;
                            screen.z_buffer[i][j] = z;
                            screen.z_buffed[i][j] = true;
                        }
                    }
                }
            }
        }

        public static void rasterize(Camera camera, Flat_Obj obj, Screen screen, float resolution){
            Vector pos = camera.T_inverse.dot(obj.pos);
            float z = pos.at(2);

            if(z < Consts.distance) return;

            // take 5 as the default resolution
            int width = (int)(obj.img.getWidth() * obj.scale * resolution / 5 * Consts.distance / z);
            int height = (int)(obj.img.getHeight() * obj.scale * resolution / 5 * Consts.distance / z);

            float x = screen.width / 2.0f + pos.at(0) * Consts.distance / z * resolution;
            float y = screen.height / 2.0f - pos.at(1) * Consts.distance / z * resolution;

            int l = (int)x - width / 2;
            int r = (int)x + width / 2 - (width + 1) % 2;
            int t = (int)y - height / 2;
            int b = (int)y + height / 2 - (height + 1) % 2;

            if(r < 0 || l > screen.width || b < 0 || t > screen.height) return;

            for(int i = l; i <= r; i++){
                for(int j = t; j <= b; j++){
                    int color = ImgFunc.adjustedColor(obj.img_buffer, r - l + 1, b - t + 1, i - l, j - t);
                    if(i < 0 || j < 0 || i >= screen.width || j >= screen.height ||
                            color / (1 << 24) == 0 ||
                            screen.z_buffed[i][j] && z > screen.z_buffer[i][j]) continue;
                    screen.colo[i][j] = color;
                    screen.z_buffed[i][j] = true;
                    screen.z_buffer[i][j] = z;
                }
            }
        }

        public static void rasterize(Camera camera, Label_Obj obj, Screen screen, float resolution){
            Vector pos = camera.T_inverse.dot(obj.pos);
            float z = pos.at(2);

            if(z < Consts.distance) return;

            int width = (int)(obj.img.getWidth() * obj.scale * resolution / 5);
            int height = (int)(obj.img.getHeight() * obj.scale * resolution / 5);

            float x = screen.width / 2.0f + pos.at(0) * Consts.distance / z * resolution;
            float y = screen.height / 2.0f - pos.at(1) * Consts.distance / z * resolution;

            int l = (int)x - width / 2;
            int r = (int)x + width / 2 - (width + 1) % 2;
            int t = (int)y - height / 2;
            int b = (int)y + height / 2 - (height + 1) % 2;

            if(r < 0 || l > screen.width || b < 0 || t > screen.height) return;

            for(int i = l; i <= r; i++){
                for(int j = t; j <= b; j++){
                    int color = ImgFunc.adjustedColor(obj.img_buffer, r - l + 1, b - t + 1, i - l, j - t);
                    if(i < 0 || j < 0 || i >= screen.width || j >= screen.height || color / (1 << 24) == 0) continue;
                    screen.colo[i][j] = color;
                    screen.z_buffed[i][j] = true;
                    screen.z_buffer[i][j] = 0;
                }
            }
        }
    }

    public double[] get_angles(Vector v){
        double x, y;
        if(Math.abs(v.at(2)) > Consts.epsilon){
            x = Math.abs(Math.atan(v.at(0) / v.at(2)));
            y = Math.abs(Math.atan(v.at(1) / v.at(2)));
        }
        else{
            x = Math.PI / 2;
            y = Math.PI / 2;
        }
        if(v.at(0) < 0){
            if(v.at(2) < 0){
                x -= Math.PI;
            }
            else{
                x = -x;
            }
        }
        else if(v.at(2) < 0){
            x = Math.PI - x;
        }
        if(v.at(1) < 0){
            if(v.at(2) < 0){
                y -= Math.PI;
            }
            else{
                y = -y;
            }
        }
        else if(v.at(2) < 0){
            y = Math.PI - y;
        }
        return new double[]{x, y};
    }

    public void rasterize_bg(){
        BufferedImage img = bg_img;
        if(img == null) return;
        double[] angles;

        angles = get_angles(camera.z_norm);

        int[] mid = {(int)((angles[0] / 2 / Math.PI + 0.5) * img.getWidth()), (int)((angles[1] / 2 / Math.PI + 0.5) * img.getHeight())};

        // use top / right position
        // Slant vector to the top = h * y_norm + d * z_norm

        Vector r_slant = camera.x_norm.mult(screen.width).add(camera.z_norm.mult(Consts.distance * resolution));
        angles = get_angles(r_slant);
        int[] right = {(int)((angles[0] / 2 / Math.PI + 0.5) * img.getWidth()), (int)((angles[1] / 2 / Math.PI + 0.5) * img.getHeight())};
        Vector t_slant = camera.y_norm.mult(screen.height).add(camera.z_norm.mult(Consts.distance * resolution));
        angles = get_angles(t_slant);
        int[] top = {(int)((angles[0] / 2 / Math.PI + 0.5) * img.getWidth()), (int)((angles[1] / 2 / Math.PI + 0.5) * img.getHeight())};

        int[] tr = {top[0] + right[0] - mid[0], top[1] + right[1] - mid[1]};
        int[] tl = {top[0] - right[0] + mid[0], top[1] - right[1] + mid[1]};
        int[] br = {-top[0] + right[0] + mid[0], -top[1] + right[1] + mid[1]};
        double[] ri = {tr[0] - tl[0], tr[1] - tl[1]};
        double[] rj = {br[0] - tr[0], br[1] - tr[1]};

        double mag_ri = Math.sqrt(ri[0] * ri[0] + ri[1] * ri[1]);
        double mag_rj = Math.sqrt(rj[0] * rj[0] + rj[1] * rj[1]);

        ri[0] /= mag_ri; ri[1] /= mag_ri;
        rj[0] /= mag_rj; rj[1] /= mag_rj;

        for(int i = 0; i < screen.width; i++){
            for(int j = 0; j < screen.height; j++){
                if(screen.z_buffed[i][j]) continue;
                screen.colo[i][j] = img.getRGB(((int)(tl[0] + i * ri[0] + j * rj[0]) + img.getWidth()) % img.getWidth(), ((int)(tl[1] + i * ri[1] + j * rj[1]) + img.getHeight()) % img.getHeight());
            }
        }
    }

    private Camera camera;

    private Screen screen;

    public BufferedImage bg_img;

    private int width;
    private int height;
    private float resolution;
    public BufferedImage canvas;

    public Scene(int width, int height, float resolution){
        this.width = width;
        this.height = height;
        this.resolution = resolution; // resolution in pixel per mm

        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 800 pixels = 220 mm
        screen = new Screen((int)(width * 0.275 * resolution), (int)(height * 0.275 * resolution));
    }

    public void mount_camera(Camera camera){
        this.camera = camera;
    }

    public void mount_background(BufferedImage img){
        bg_img = img;
    }

    public void rasterize(Real_Obj obj){
        Algorithm.rasterize(camera, obj, screen, resolution);
    }

    public void rasterize(Flat_Obj obj){
        Algorithm.rasterize(camera, obj, screen, resolution);
    }

    public void rasterize(Label_Obj obj){
        Algorithm.rasterize(camera, obj, screen, resolution);
    }

    public void render(){
        // TODO: SSAA with ImgFunc.adjustedColor()
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
