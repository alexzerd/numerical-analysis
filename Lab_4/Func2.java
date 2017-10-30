
public final class Func2 extends Equation implements Assumable {

    public Func2(double [] vars){
        super(vars, vars.length);
    }

    @Override
    public double getValue( double [] vars){
        
    	return vars[0] + Math.pow(vars[1], 2) - 5;
    }
}
