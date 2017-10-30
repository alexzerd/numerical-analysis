
public final class Func1 extends Equation implements Assumable { 


    public Func1(double [] vars){
        super(vars, vars.length);
    }
    @Override
    public double getValue(double [] vars){

    	return vars[0] + vars[1] - 3;
    }
}
