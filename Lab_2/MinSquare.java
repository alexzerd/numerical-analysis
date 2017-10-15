import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class MinSquare {
	
    public static void MinSquare(double [] x, double [] y, int N, int m) throws FileNotFoundException{
    	
        double [] POWERX = new double[2*m+1];

        for (int k = 0; k < 2*m+1; k++){ 
        	
            POWERX[k] = 0;
            
            for (int i = 0; i < N; i++){
            	
                POWERX[k] += Math.pow(x[i],k);
            }
        }
        
        Matrix SUMX = new Matrix(m+1, m+1);
        
        SUMX.setElem(0,0, N);
        
        for (int l = 0;  l <= m; l++ ){
        	
            for (int j = 0; j <= m; j++){
            	
                SUMX.setElem(l,j,POWERX[l+j]);
            }
        }
        Matrix PRAW = new Matrix(1,m+1);

        for (int l = 0; l <= m; l++){

            for (int i = 0; i < N; i++){
            	
                PRAW.setElem(0,l, (PRAW.getElem(0,l)+(y[i] * Math.pow(x[i],l))));
            }
        }
        
        double [] A = new double[m+1];
        
        Gauss.Gauss(SUMX, PRAW, A);
        
        double sPow2 = 0;
        
        for (int i = 0; i < N; i++){
        	
            double curValue = y[i] - A[0];
            
            for (int k = 1; k <=m; k++) {
            	
                curValue -= A[k]*POWERX[k-1];
            }
            
            sPow2+=curValue;
        }
        
        sPow2 = sPow2/(N-m-1);
        double sigma = Math.sqrt(sPow2);

        
        for (int i = 0; i < A.length; i++){
            System.out.println(A[i]+"; ");
        }

        /*File dataFile = new File("D:\\work\\MNK.csv");
        StringBuilder sb = new StringBuilder();*/


        //PrintWriter outWriter = new PrintWriter(dataFile);

        /*for (int i = 0; i < A.length; i++){
            sb.append(A[i]+";");
        }*/
        
        /*outWriter.write(sb.toString());
        outWriter.close();*/


    }

}
