
public class Gauss {
    public static void Gauss(Matrix A, Matrix B, double [] x){
    	
    	
        Matrix Ac = new Matrix(A);
        Matrix Bc = new Matrix(B);
        
        int AColumnSize = A.getColumnSize();
        int ALineSize = A.getLineSize();
        
        for (int i = 0; i < ALineSize; i++){
        	
            int baseLineIndex = A.findMaxInColumnElem(i);
            
            if (baseLineIndex > i){
            	
                A.swapLines(baseLineIndex,i);
                double BTemp = B.getElem(0, baseLineIndex);//swapping b
                B.setElem(0, baseLineIndex, B.getElem(0, i));
                B.setElem(0, i, BTemp);
                
            }
            
            
            double curElement = A.getElem(i,i);

            for (int z = i; z < AColumnSize; z++ ){
            	
                A.setElem(i,z, A.getElem(i,z)/curElement);
            }
            
            B.setElem(0,i, B.getElem(0,i)/curElement);
            
            if (i+1 < ALineSize) {
            	
                int k = i + 1;
                
                for (; k < ALineSize; k++) {
                	
                    double multiplier = A.getElem(k,i);
                    
                    for (int l = i; l < AColumnSize; l++) {
                    	
                        A.setElem(k, l, A.getElem(k,l) - A.getElem(i, l)* multiplier);
                    }
                    
                    B.setElem(0, k, B.getElem(0, k) - B.getElem(0, i)*multiplier);
                }

            }
        }
        
        
        for (int i = AColumnSize - 1; i >= 0; i--){
        	
            double sum = 0;
            
            for (int k = AColumnSize-1; k >= 0; k--) {
            	
                sum += A.getElem(i,k)*x[k];
            }
            
            x[i] = B.getElem(0,i) - sum;
           // System.out.println("x["+i+ "]:" + x[i]);
        }

    }
}
