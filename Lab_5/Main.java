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
    	

    	/*int N = 20;
        double [] x = {0.0,	5.0, 10.0,	15.0,	20.0,	25.0,	30.0,	35.0, 40.0,	45.0,	50.0,	55.0,	60.0,	70.0,	75.0,	80.0, 85.0, 90.0, 95.0, 100.0}; 
        double [] y = {1.00762, 1.00392, 1.00153, 1.00000, 0.99907, 0.99852, 0.99826, 0.99818, 0.99828, 0.99849, 0.99878, 0.99919, 0.99967, 1.00024, 1.00091, 1.00167, 1.00253, 1.00351, 1.00461, 1.00586, 1.00721};
        int m = 3;
    	
        MinSquare.MinSquare(x,y,N,m);*/
    
    	Func func = new Func();
    	DoubleFunc func1 = new DoubleFunc();
    	
    	Integrals.Trapeze(func, 0.8, 1.762, 1e-5);
    	Integrals.Simpson(func, 0.8, 1.762, 1e-5);
    	Integrals.CubeSimpson(func1, 0, 2.0, 0, 1.0, 1e-5);
    }


}
