package engine;

import algebra.Vector;

public class Ray {
    Vector pos, dir;

    public Ray(Vector pos, Vector dir){
        this.pos = pos;
        this.dir = dir;
    }
}
