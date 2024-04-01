package engine;
import algebra.*;

// TODO: collision box

public class Object {
    private final double pi = Math.PI;
    private final double epsilon = 1E-10; // another choice: 1E-14

    // Consider:
    // Barycentric coordinates
    // rgb as fractions
    // https://www.youtube.com/watch?v=C8YtdC8mxTU

    public Matrix[] faces;
    public Vector[] colo;
    // Note: the last dimension of colo is the first index of the vertex of the corresponding face's
    public Vector[] normal;
    public Vector pos = new Vector(0, 0, 0, 1);
    public Object(Matrix[] faces, Vector[] colo){
        /*
        this.faces = new Matrix[faces.length];
        for(int i = 0; i < faces.length; this.faces[i] = faces[i++]);
        this.colo = new Vector[colo.length];
        for(int i = 0; i < colo.length; this.colo[i] = colo[i++]);
         */

        this.faces = faces;
        this.colo = colo;
        normal = new Vector[faces.length];
        for(int i = 0; i < faces.length; i++){
            normal[i] = faces[i].at(i + 1).subtract(faces[i].at(i)).cross(faces[i].at(i + 2).subtract(faces[i].at(i)));
        }
    }

    public void transform(Vector trail, Ray axle){
        // Note: length of the axle's direction vector determines the angle

        // theta - angle in radian counter-clockwise around the axle
        // u - normalized axle of rotation
        // c - position of the axle
        // d - displacement after the rotation
        double theta = axle.dir.mag;

        if(Math.abs(theta) < epsilon){
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

        // Translation matrix
        // use Rodrigues’ rotation formula combined with translations
        Matrix T = new Matrix(new double[][]{
                {Math.cos(theta) + ux*ux*(1 - Math.cos(theta)), ux*uy*(1 - Math.cos(theta)) - uz*Math.sin(theta), ux*uz*(1 - Math.cos(theta)) + uy*Math.sin(theta), (1 - Math.cos(theta)) * (ux*uz*cy - uy*uz*cx) + uz*Math.sin(theta)*cy - uy*Math.sin(theta)*cz + cx + dx},
                {uy*ux*(1 - Math.cos(theta)) + uz*Math.sin(theta), Math.cos(theta) + uy*uy*(1 - Math.cos(theta)), uy*uz*(1 - Math.cos(theta)) - ux*Math.sin(theta), (1 - Math.cos(theta)) * (uy*uz*cx - ux*uz*cy) + uz*Math.sin(theta)*cx - ux*Math.sin(theta)*cz + cy + dy},
                {uz*ux*(1 - Math.cos(theta)) - uy*Math.sin(theta), uz*uy*(1 - Math.cos(theta)) + ux*Math.sin(theta), Math.cos(theta) + uz*uz*(1 - Math.cos(theta)), (1 - Math.cos(theta)) * (ux*uy*cz - ux*uz*cy) + uy*Math.sin(theta)*cz - uz*Math.sin(theta)*cx + cz + dz},
                {0, 0, 0, 1}
        });
        Matrix R = new Matrix(new double[][]{
                {Math.cos(theta) + ux*ux*(1 - Math.cos(theta)), ux*uy*(1 - Math.cos(theta)) - uz*Math.sin(theta), ux*uz*(1 - Math.cos(theta)) + uy*Math.sin(theta), 0},
                {uy*ux*(1 - Math.cos(theta)) + uz*Math.sin(theta), Math.cos(theta) + uy*uy*(1 - Math.cos(theta)), uy*uz*(1 - Math.cos(theta)) - ux*Math.sin(theta), 0},
                {uz*ux*(1 - Math.cos(theta)) - uy*Math.sin(theta), uz*uy*(1 - Math.cos(theta)) + ux*Math.sin(theta), Math.cos(theta) + uz*uz*(1 - Math.cos(theta)), 0},
                {0, 0, 0, 1}
        });

        for(int i = 0; i < normal.length; i++)
            normal[i] = R.dot(normal[i]);
        for(int i = 0; i < faces.length; i++)
            faces[i] = T.dot(faces[i]);
        pos = T.dot(pos);
    }

    public void translate(Vector trail){
        Matrix T = new Matrix(new double[][]{
                {1, 0, 0, trail.at(0)},
                {0, 1, 0, trail.at(1)},
                {0, 0, 1, trail.at(2)},
                {0, 0, 0, 1}
        });

        for(int i = 0; i < faces.length; i++)
            faces[i] = T.dot(faces[i]);
        pos = T.dot(pos);
    }

}
