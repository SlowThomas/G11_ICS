package engine;
import algebra.*;

public class Object {
    private final double pi = Math.PI;
    private final double sigma = 0.0001;

    // TODO: surface
    // TODO: collision box
    // TODO: rotation

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

    public void transform(Vector direction, Vector axle){
        // can be multithreading
        double x = axle.at(0);
        double y = axle.at(1);
        double z = axle.at(2);

        // alpha and beta - potentially useless
        double alpha, beta, theta = axle.mag;

        // re-shift to y-axis
        // angle from x-axis: alpha
        // angle from z-axis: beta
        if(Math.abs(x) < sigma) alpha = (y < 0) ? 3 * pi / 2 : pi / 2;
        else alpha = Math.atan(y / x) + ((y < 0)? pi : 0);
        if(Math.abs(z) < sigma) beta = (y < 0) ? 3 * pi / 2 : pi / 2;
        else beta = Math.atan(y / z) + ((y < 0)? pi : 0);

    }

}
