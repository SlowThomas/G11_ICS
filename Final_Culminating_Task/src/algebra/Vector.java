package algebra;

public class Vector {
    public int shape;
    public double mag;
    protected double[] body;

    public Vector(double[] body){
        this.body = body;
        this.shape = body.length;
        for(int i = 0; i < this.shape; this.mag += body[i] * body[i++]);
        this.mag = Math.sqrt(this.mag);
    }

    public double dot(Vector nxt) throws ArithmeticException{
        if(this.shape != nxt.shape)
            throw new ArithmeticException(
                    "Performing dot product between a " + this.shape + "-d Vector and a " + nxt.shape + "-d Vector"
            );
        double result = 0;
        for(int i = 0; i < this.shape; i++)
            result += this.body[i] * nxt.body[i];
        return result;
    }

    public Vector cross(Vector nxt){
        if(this.shape != 3 || nxt.shape != 3)
            throw new ArithmeticException("Performing cross product for Vectors that are not 3-d");
        return new Vector(new double[]{
                this.body[1] * nxt.body[2] - this.body[2] * nxt.body[1],
                this.body[2] * nxt.body[0] - this.body[0] * nxt.body[2],
                this.body[0] * nxt.body[1] - this.body[1] * nxt.body[0]
        });
    }
}
