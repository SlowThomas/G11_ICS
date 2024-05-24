package engine;
import algebra.*;

public class Camera {

    private static class Consts{
        public static double epsilon = 1E-10; // another choice: 1E-14
    }

    public Vector pos = new Vector(0, 0, 0, 1);
    public Vector x_norm = new Vector(1, 0, 0);
    public Vector y_norm = new Vector(0, 1, 0);
    public Vector z_norm = new Vector(0, 0, 1);

    public Matrix T = new Matrix(new double[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    }); // the net transformation experienced by the camera
    public Matrix T_inverse = new Matrix(new double[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    }); // inverse of T for back-tracking

    public Camera(Vector pos){ cd(pos); }
    public Camera(double x, double y, double z){ this(new Vector(x, y, z, 1)); }

    public void rotate(Vector axis, double angle){
        if(axis.mag < Consts.epsilon) return;

        // pre-compute common terms
        double ux = axis.at(0) / axis.mag;
        double uy = axis.at(1) / axis.mag;
        double uz = axis.at(2) / axis.mag;
        double x = pos.at(0);
        double y = pos.at(1);
        double z = pos.at(2);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double component_product = x*ux + y*uy + z*uz;

        // Rotation matrix
        // https://en.wikipedia.org/wiki/Rotation_matrix
        Matrix T_rotate = new Matrix(new double[][]{
                {cos + ux*ux*(1-cos),           ux*uy*(1-cos)-uz*sin,       ux*uz*(1-cos) + uy*sin,     sin*(y*uz - z*uy) - (1 - cos) * ux * component_product + x * (1 - cos)},
                {uy*ux*(1 - cos) + uz * sin,    cos + uy*uy*(1 - cos),      uy*uz*(1 - cos) - ux*sin,   sin*(z*ux - x*uz) - (1 - cos) * uy * component_product + y * (1 - cos)},
                {uz*ux*(1 - cos) - uy*sin,      uz*uy*(1 - cos) + ux*sin,   cos + uz*uz*(1 - cos),      sin*(x*uy - y*ux) - (1 - cos) * uz * component_product + z * (1 - cos)},
                {0, 0, 0, 1}
        });

        T = T_rotate.dot(T);
        T_inverse = T.inverse();
        x_norm = new Vector(T.at(0, 0), T.at(1, 0), T.at(2, 0));
        y_norm = new Vector(T.at(0, 1), T.at(1, 1), T.at(2, 1));
        z_norm = new Vector(T.at(0, 2), T.at(1, 2), T.at(2, 2));
    }


    public void move(Vector trail){
        Matrix Trans = new Matrix(new double[][]{
                {1, 0, 0, trail.at(0)},
                {0, 1, 0, trail.at(1)},
                {0, 0, 1, trail.at(2)},
                {0, 0, 0, 1}
        });

        T = Trans.dot(T);
        pos = Trans.dot(pos);

        T_inverse = T.inverse();
    }

    public void move(double x, double y, double z){
        Matrix Trans = new Matrix(new double[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        });

        T = Trans.dot(T);
        pos = Trans.dot(pos);

        T_inverse = T.inverse();
    }

    public void cd(Vector destination){
        move(destination.subtract(pos));
    }

}
