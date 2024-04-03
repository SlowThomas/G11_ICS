package engine;
import algebra.*;

public class Algorithms {
    private static void floodFill_dfs(int x, int y, Vector v1, Vector v2, Vector v3, byte[][][] screen){
        Vector a = v1.subtract(v2), b = v2.subtract(v3), c = v3.subtract(v1);
    }
    public static void floodFill(Matrix face, Vector color, byte[][][] screen){
        Vector v1 = face.at(0), v2 = face.at(1), v3 = face.at(2);
        floodFill_dfs(x1, y1, v1, v2, v3, screen);



    }
}
