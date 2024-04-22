package engine;
import algebra.*;

public class Camera {

    private static class Consts{
        public static double epsilon = 1E-10; // another choice: 1E-14
    }

    public Vector pos = new Vector(0, 0, 0, 1);
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

    public Camera(Vector pos){
        cd(pos);
    }

    public void transform(Vector trail, Ray axle){
        // Note: length of the axle's direction vector determines the angle

        // theta - angle in radian counter-clockwise around the axle
        // u - normalized axle of rotation
        // c - position of the axle
        // d - displacement after the rotation
        double theta = axle.dir.mag;

        if(Math.abs(theta) < Consts.epsilon){
            translate(trail);
            return;
        }

        double ux = axle.dir.at(0) / theta;
        double uy = axle.dir.at(1) / theta;
        double uz = axle.dir.at(2) / theta;
        double cx = axle.pos.at(0);
        double cy = axle.pos.at(1);
        double cz = axle.pos.at(2);
        double dx = trail.at(0);
        double dy = trail.at(1);
        double dz = trail.at(2);

        double x11 = Math.cos(theta) + ux*ux*(1 - Math.cos(theta));
        double x12 = ux*uy*(1 - Math.cos(theta)) - uz*Math.sin(theta);
        double x13 = ux*uz*(1 - Math.cos(theta)) + uy*Math.sin(theta);
        double x21 = uy*ux*(1 - Math.cos(theta)) + uz*Math.sin(theta);
        double x22 = Math.cos(theta) + uy*uy*(1 - Math.cos(theta));
        double x23 = uy*uz*(1 - Math.cos(theta)) - ux*Math.sin(theta);
        double x31 = uz*ux*(1 - Math.cos(theta)) - uy*Math.sin(theta);
        double x32 = uz*uy*(1 - Math.cos(theta)) + ux*Math.sin(theta);
        double x33 = Math.cos(theta) + uz*uz*(1 - Math.cos(theta));

        // Translation matrix
        // use Rodrigues’ rotation formula combined with translations
        Matrix Trans = new Matrix(new double[][]{
                {x11, x12, x13, (1 - Math.cos(theta)) * (ux*uz*cy - uy*uz*cx) + uz*Math.sin(theta)*cy - uy*Math.sin(theta)*cz + cx + dx},
                {x21, x22, x23, (1 - Math.cos(theta)) * (uy*uz*cx - ux*uz*cy) + uz*Math.sin(theta)*cx - ux*Math.sin(theta)*cz + cy + dy},
                {x31, x32, x33, (1 - Math.cos(theta)) * (ux*uy*cz - ux*uz*cy) + uy*Math.sin(theta)*cz - uz*Math.sin(theta)*cx + cz + dz},
                {0, 0, 0, 1}
        });

        T = Trans.dot(T);
        pos = T.dot(pos);

        T_inverse = T.inverse();
    }

    public void translate(Vector trail){
        Matrix Trans = new Matrix(new double[][]{
                {1, 0, 0, trail.at(0)},
                {0, 1, 0, trail.at(1)},
                {0, 0, 1, trail.at(2)},
                {0, 0, 0, 1}
        });

        T = Trans.dot(T);
        pos = T.dot(pos);

        T_inverse = T.inverse();
    }

    public void cd(Vector destination){
        translate(destination.subtract(pos));
    }

}
