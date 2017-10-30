
public class Equation {

    protected double [] vars;

    public Equation(int size){
        vars =  new double[size];
        for (double x : vars){
            x = 0;
        }
    }
    
    public Equation (double [] vars, int size){
        this.vars = new double[size];
        for (int i = 0; i < size; i++){
            this.vars[i] = vars[i];
        }

    }
    public void setX(double [] vars, int i){
        this.vars[i] = vars[i];
    }

    
}
