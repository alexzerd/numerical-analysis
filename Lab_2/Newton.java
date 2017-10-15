
public class Newton {

    public static void Newton(Assumable [] funcArray, double [] vars){
    	
        double delta1 = 10;
        double delta2 = 10;
        double eps1 = 1e-9;
        double eps2 = eps1;


        System.out.println("k        delta1                          delta2");
        int iterNum = 0;
        
        do {
        	
        
            double[] deltaX = new double[vars.length];
            
          //Вычисление векора невязки
            
            Matrix F = new Matrix(1, 2);
            
            for (int i = 0; i < vars.length; i++) {
            	
                F.setElem(0, i, -funcArray[i].getValue(vars));
            }
            
            //Решение СЛАУ
            
            Gauss.Gauss(JacobiAssuming(vars, funcArray), F, deltaX);
            
            for (int i = 0; i < vars.length; i++){
            	
                vars[i]+=deltaX[i];
            }
            
            //Вычисление дельта-1
            
            {
                double max = 0;
                for (int i = 0; i < vars.length; i++) {
                	
                    if (Math.abs(funcArray[i].getValue(vars)) > max) {
                    	
                       max = Math.abs(funcArray[i].getValue(vars));
                    }
                }
                delta1 = max;
            }
            
            //Вычисление дельта-2
            
            {
                double max = 0;
                for (int i = 0; i < vars.length; i++) {
                	
                    if (Math.abs(deltaX[i]) < 1 && max < Math.abs(deltaX[i])) {
                    	
                        max = Math.abs(deltaX[i]);
                    }
                   else if (Math.abs(deltaX[i]) >= 1 && max < Math.abs((deltaX[i])/vars[i] )){
                	   
                        max = Math.abs(deltaX[i]/vars[i]);
                    }
                }
                delta2 = max;
            }
            
            
            iterNum++;
            
            System.out.println(iterNum + "       " + delta1+"         "+delta2);
            
            if(iterNum == 20) System.out.println("ERROR");
            
        } while ( (delta1 > eps1) && (delta2 > eps2) && iterNum < 20);

    }
    
    //Вычисление матрицы Якоби
    
    public static Matrix JacobiAssuming(double [] varVector, Assumable [] funcArray){
    	
        Matrix JacobiMatrix = new Matrix(2,2);
        
        for (int i = 0; i < JacobiMatrix.getLineSize(); i++ ){
        	
            for (int j = 0; j < JacobiMatrix.getColumnSize(); j++){
            	
                JacobiMatrix.setElem(i,j,partialDerivative(varVector, j,funcArray[i]));
            }
        }
        
        return JacobiMatrix;

    }
    
    //Вычисление производной
    
    public static double partialDerivative(double [] varVector, int position, Assumable func){
    	
        double delta = 1e-6;
        
        if (position >= varVector.length){
        	
            return -1;
        }
        
        double [] varVectorDelta = new double[varVector.length];
        
        for (int i = 0; i < varVector.length; i++){
        	
            varVectorDelta[i] = varVector[i];
        }
        
        varVectorDelta[position] += delta;
        
        return ((func.getValue(varVectorDelta) - func.getValue(varVector)) / delta);
    }
}
