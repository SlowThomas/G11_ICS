package algebra;

public class Matrix {
    private double epsilon = 1E-10; // another choice: 1E-14
    public int[] shape = new int[2];
    protected double[][] body;
    public Matrix(double[][] body){
        this.shape[0] = body.length;
        if(body.length > 0)
            this.shape[1] = body[0].length;
        /*
        this.body = new double[this.shape[0]][this.shape[1]];
        for(int i = 0; i < this.shape[0]; i++)
            for(int j = 0; j < this.shape[1]; this.body[i][j] = body[i][j++]);
         */
        this.body = body;
    }

    public Vector at(int col) {
        double[] v = new double[shape[0]];
        for (int i = 0; i < shape[0]; i++)
            v[i] = body[col][i];
        return new Vector(v);
    }

    public double at(int i, int j){
        return body[i][j];
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

    // Method to compute the inverse of a 4x4 matrix
    public Matrix inverse() {
        // Compute the determinant of the matrix
        double det = det();

        // Check if the matrix is invertible
        if (Math.abs(det) < epsilon) {
            throw new ArithmeticException("Matrix is not invertible");
        }

        // Compute the adj of the matrix, divided by the det
        double[][] matrix = new double[shape[0]][shape[1]];
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                matrix[i][j] = cofactor(j, i) / det;
            }
        }
        return new Matrix(matrix);
    }

    // Method to compute the determinant
    public double det() throws ArithmeticException{
        if(shape[0] != shape[1]) throw new ArithmeticException("Matrix is not square");

        if(shape[0] == 1){
            return body[0][0];
        }

        double det = 0;
        for(int i = 0; i < shape[0]; i++){
            if(i % 2 == 0) det += body[0][i] * minor(0, i).det();
            else det -= body[0][i] * minor(0, i).det();
        }

        return det;

        // Implementation of the determinant formula for a 4x4 matrix
        /*
        return matrix[0][0] * (matrix[1][1] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2])
                - matrix[1][2] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1])
                + matrix[1][3] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1]))
                - matrix[0][1] * (matrix[1][0] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2])
                - matrix[1][2] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0])
                + matrix[1][3] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0]))
                + matrix[0][2] * (matrix[1][0] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1])
                - matrix[1][1] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0])
                + matrix[1][3] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]))
                - matrix[0][3] * (matrix[1][0] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1])
                - matrix[1][1] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0])
                + matrix[1][2] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]));
         */
    }

    // Method to compute the matrix's adj
    public Matrix adj() {
        double[][] matrix = new double[shape[0]][shape[1]];
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                matrix[i][j] = cofactor(j, i);
            }
        }
        return new Matrix(matrix);
    }

    // Method to compute the cofactor of a specific element in a matrix
    public double cofactor(int row, int col) {
        if((row + col) % 2 == 0) return minor(row, col).det();
        return -minor(row, col).det();
    }

    // Method to compute the minor of a matrix with a specific row and column removed
    public Matrix minor(int row, int col) {
        double[][] minor = new double[shape[0] - 1][shape[1] - 1];
        for(int x = 0, i = 0; x < shape[0]; x++){
            if(x == row) continue;
            for(int y = 0, j = 0; y < shape[1]; y++){
                if(y == col) continue;
                minor[i][j++] = body[x][y];
            }
            i++;
        }
        return new Matrix(minor);
    }

    public void print(){
        for(int i = 0; i < shape[0]; i++){
            for(int j = 0; j < shape[1]; j++){
                System.out.printf("%f ", body[i][j]);
            }
            System.out.println();
        }
    }
}
