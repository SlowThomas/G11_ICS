package engine;
import algebra.Vector;


public class Face{
    public Vector[] vertecies = new Vector[3];
    public Vector color;

    public Face(Vector[] vertecies, Vector color){
        this.vertecies = vertecies;
    }
}
