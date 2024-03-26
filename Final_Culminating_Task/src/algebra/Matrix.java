package algebra;

public class Matrix {
    public int[] shape = new int[2];
    protected double[][] body;
    public Matrix(double[][] body){
        this.body = body;
        this.shape[0] = body.length;
        if(body.length > 0)
            this.shape[1] = body[0].length;
    }

    public Matrix dot(Matrix nxt) throws ArithmeticException{
        if(this.shape[1] != nxt.shape[0])
            throw new ArithmeticException(
                    "Performing dot product between a " + this.shape[0] + "x" + this.shape[1] + " Matrix and a " + nxt.shape[0] + "x" + nxt.shape[1] + " Matrix"
            );

        double[][] result = new double[this.shape[0]][nxt.shape[1]];
        for(int i = 0; i < this.shape[0]; i++)
            for(int j = 0; j < nxt.shape[1]; j++)
                for(int k = 0; k < this.shape[1]; k++)
                    result[i][j] += this.body[i][k] * nxt.body[k][j];
        return new Matrix(result);
    }

    public Vector dot(Vector nxt) throws ArithmeticException{
        if(this.shape[1] != nxt.shape)
            throw new ArithmeticException(
                    "Performing dot product between a " + this.shape[0] + "x" + this.shape[1] + " Matrix and a " + nxt.shape + "-d Vector"
            );
        double[] result = new double[this.shape[0]];
        for(int i = 0; i < this.shape[0]; i++)
            for(int j = 0; j < this.shape[1]; j++)
                result[i] += this.body[i][j] * nxt.body[j];
        return new Vector(result);
    }
}
