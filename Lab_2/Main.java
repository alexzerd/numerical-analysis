import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

       /* double u0 = 0;
        double u1 = 0;
        double [] vars = new double[2];
        
        vars [0] = u0;
        vars [1] = u1;
        
        Assumable [] funcArray = new Assumable[vars.length];
        
        Func1 func1 = new Func1(vars);
        Func2 func2 = new Func2(vars);
        
        funcArray[0] = func1;
        funcArray[1] = func2;
        
        
        Newton.Newton(funcArray, vars);
        
        System.out.println(" ");
        
        for (int i = 0; i < vars.length; i++){
        	
            System.out.println("X"+i +": "+vars[i]);
        }*/
    	
    	
    	int N = 16;
        double [] x = {37.8,	40.6,	43.3,	46.1,	49.4,	55.6,	62.2,	67.2, 72.8,	81.7,	88.3,	95.0,	100.0,	107.8,	113.9,	121.7}; 
        double [] y = {3.71	, 3.81,	3.86,	3.93,	3.96,	4.20,	4.34,	4.51, 4.73,	5.35,	5.74,	6.14,	6.51,	6.98,	7.44,	7.76};
        int m = 3;
    	

        try {
           MinSquare.MinSquare(x,y,N,m);
        } catch (FileNotFoundException e){
            e.printStackTrace();
}

    }


}
