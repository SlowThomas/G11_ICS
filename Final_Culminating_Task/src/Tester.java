import java.util.function.Function;

import algebra.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tester {

    public static double dot(double[] a, double[] b){
        double n = 0;
        for(int i = 0; i < a.length; i++){
            n += a[i] * b[i];
        }
        return n;
    }

    public static void main(String[] args){
        long start, end;
        double t1 = 0, t2 = 0;
        double avg = 0;
        int trial = 1;
        // dot product - object about 0.5 - 1 times slower
        // array declaration - 7 times faster to clear manually
        // BufferedImage vs array - > 50 times faster with arrays
        // BufferedImage: range vs manual update - no difference


        BufferedImage img = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        int[] arr = new int[1000000];

        start = System.nanoTime();
        for(int t = 0; t < 100; t++){
            img.setRGB(0, 0, 1000, 1000, arr, 0, 1000);
        }
        end = System.nanoTime();
        t2 += end - start;

        start = System.nanoTime();
        for(int t = 0; t < 100; t++){
        }
        end = System.nanoTime();
        t1 += end - start;

        System.out.println(t1 / t2);
    }
}
