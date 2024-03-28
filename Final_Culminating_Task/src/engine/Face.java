package engine;
import algebra.Vector;


public class Face{
    public Vector[] vertices;
    public Vector color;

    public Face(Vector[] vertices, Vector color){
        this.vertices = new Vector[vertices.length];
        for(int i = 0; i < this.vertices.length; this.vertices[i] = vertices[i++]);
        this.color = color;
    }
}
