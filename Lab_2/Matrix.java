import java.util.Scanner;

public class Matrix {
    private int lineSize;
    private int columnSize;
    private double [][] coefficients;

    public int getLineSize() {
        return lineSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public Matrix(){
        lineSize = 0;

        columnSize = 0;
    }
    public Matrix(double [][] coefficients){
        this.coefficients = coefficients;
    }
    public Matrix(int lineSize, int columnSize){
        this.lineSize = lineSize;
        this.columnSize = columnSize;
        this.coefficients = new double[lineSize][columnSize];
    }
    public Matrix (Matrix M){
        this(M.getLineSize(), M.getColumnSize());
        this.coefficients = new double[lineSize][columnSize];
        for (int i = 0; i < lineSize; i++){
            for (int j = 0; j < columnSize; j++){
                this.coefficients[i][j] = M.coefficients[i][j];
            }
        }

    }
    public void MatrixInput(){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < lineSize; i++){
            for (int j = 0; j < columnSize; j++){
                setElem(i, j, scanner.nextDouble());
            }
        }

    }
    public void setElem(int i, int j, double coefficient){
        this.coefficients[i][j] = coefficient;
    }
    public double getElem(int lineNumber, int columnNumber){
        return this.coefficients[lineNumber][columnNumber];
    }
    public int findMaxInLineElem(int lineNumber){
        int index = 0;
        for (int j = 0; j < this.lineSize; j++){
            if (getElem(lineNumber,j) > getElem(lineNumber, index)){
                index = j;
            }
        }
        return index;
    }
    public int findMaxInColumnElem(int columnNumber){
        int index = 0;
        for (int i = 0; i < this.columnSize; i++){
            if (getElem(i,columnNumber) > getElem(index, columnNumber)){
                index = i;
            }
        }
        return index;
    }
    public void swapLines(int line1, int line2){
        double [] temp = coefficients[line1];
        coefficients[line1] = coefficients[line2];
        coefficients[line2] = temp;
    }

    public Matrix matrixMultiply(Matrix B) {
        int n = this.getLineSize();
        int m = this.getColumnSize();
        int k = B.getColumnSize();
        Matrix res = new Matrix(n,k);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                for (int p = 0; p < m; p++) {
                    //res[i][j] = res[i][j] + a[i][p] * b[p][j];
                    res.setElem(i,j, res.getElem(i,j) + (this.getElem(i,p)*B.getElem(p,j)));
                }
            }
        }
        return res;
    }
}
