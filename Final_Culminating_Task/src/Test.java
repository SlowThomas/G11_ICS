import algebra.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;

public class Test{

    public static void main(String[] args) throws IOException{
        BufferedImage img = ImageIO.read(new File("img/crosshair.png"));
        for(int i = 0; i < img.getWidth(); i++){
            for(int j = 0; j < img.getHeight(); j++){
                int color = img.getRGB(i, j);
                System.out.printf("%d %d %d %d\n", color >>> 24, (color >> 16) - (color >> 24 << 8),
                        (color >> 8) - (color >> 16 << 8), color - (color >> 8 << 8));
            }
        }

        System.out.println();
    }
}
