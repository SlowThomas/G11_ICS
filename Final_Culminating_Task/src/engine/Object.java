package engine;
import algebra.*;

public class Object {
    private final double pi = Math.PI;
    private final double epsilon = 1E-10; // another choice: 1E-14

    // TODO: surface
    // TODO: collision box

    // TODO: constructor

    // Consider:
    // Barycentric coordinates
    // rgb as fractions
    // https://www.youtube.com/watch?v=C8YtdC8mxTU

    private Face[] faces;
    private Vector pos;
    public Object(Face[] faces, Vector pos){
        this.faces = new Face[faces.length];
        for(int i = 0; i < faces.length; this.faces[i] = faces[i++]);
        this.pos = pos;
    }

    public void transform(Vector trail, Ray axle){
        // TODO: store faces into a single matrix
        // TODO: apply transformation to all faces
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
    }

    public void translate(Vector trail){
        // TODO: store faces into a single matrix
        // TODO: apply transformation to all faces
        Matrix T = new Matrix(new double[][]{
                {1, 0, 0, trail.at(0)},
                {0, 1, 0, trail.at(1)},
                {0, 0, 1, trail.at(2)},
                {0, 0, 0, 1}
        });
    }

}
