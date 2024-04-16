package engine;
import algebra.*;

public class Camera {

    private static class Consts{
        public static double epsilon = 1E-10; // another choice: 1E-14
    }

    public Vector pos = new Vector(0, 0, 0, 1);
    // T: the net transformation experienced by the camera
    public Matrix T = new Matrix(new double[][]{
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 1}
    });
}
