package engine;

import algebra.Vector;

import java.awt.image.BufferedImage;

public class Indicator {
    public ImageBuffer img;
    public int width;
    public int height;
    public double scale = 1;

    public Indicator(BufferedImage img){
        this.img = new ImageBuffer(img);
        width = img.getWidth();
        height = img.getHeight();
    }

    public Vector edgeFunction(int x, int y){
        return new Vector(x, y);
    }

    public void label(Vector pos, Scene scene){
        Vector visual = pos.subtract(scene.camera.getPos());
        double mag = visual.dot(scene.camera.z_norm);
        // TODO: out of screen
        if(mag < 1e-10 || visual.mag < 1e-10) return;
        mag = 2145 * visual.mag / mag;

        int x = (int)(scene.canvas.getWidth() / 2.0 + visual.dot(scene.camera.x_norm) / visual.mag * mag);
        int y = (int)(scene.canvas.getHeight() / 2.0 - visual.dot(scene.camera.y_norm) / visual.mag * mag);

        int margin = 20;

        Vector pixel_pos = edgeFunction(x, y);

        int l = (int)(pixel_pos.at(0) - width * scale / 2.0);
        int r = l + (int)(width * scale);
        int t = (int)(pixel_pos.at(1) - height * scale / 2.0);
        int b = t + (int)(height * scale);

        if(r < 0 || l >= screen.width || b < 0 || t >= screen.height) return;

        for(int i = l; i < r; i++){
            for(int j = t; j < b; j++){
                int color = ImgFunc.adjustedColor(img, r - l, b - t, i - l, j - t);
                if(i < 0 || j < 0 || i >= screen.width || j >= screen.height || color >>> 24 == 0) continue;
                screen.colo[i][j] = color;
                screen.z_buffed[i][j] = true;
                screen.z_buffer[i][j] = 0;
            }
        }
    }
}
