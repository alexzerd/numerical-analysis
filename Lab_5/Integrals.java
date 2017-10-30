
public class Integrals {

	public static void Trapeze(Func func, double a, double b, double e) {
		
		double delta = 0, eps = 3*e, n = 2.0, firstInt = 0, secondInt = 0, h = 0;
		
		h = (b - a)/n;
		
		 do {
			
			double sum = 0;
			
			for(int i = 1; i <= n-1; i++) {
				
				sum += func.getValue(a + i*h);
				
			}
			
			firstInt = (h/2)*(func.getValue(a) + 2*sum + func.getValue(b));
			
			delta = Math.abs(firstInt - secondInt);
			
			n *= 2;
			h /= 2;
			
			secondInt = firstInt;
			
		} while(delta > eps);
		
		System.out.println(firstInt);
	}
	
	public static void Simpson(Func func, double a, double b, double e) {
		
		double delta = 0, eps = 15*e, m = 1.0, firstInt = 0, secondInt = 0, h = 0, n = 2*m;
		
		n = 2*m;
		h = (b - a)/n;
		
		
		do {
			
			double sum1 = 0;
			
			for(int i = 1; i <= m; i++) {
				
				sum1 += func.getValue(a + (2*i-1)*h);
			}
			
			double sum2 = 0;
			
			for(int i = 1; i <= m-1; i++) {
				
				sum2 += func.getValue(a + (2*i)*h);
			}
			
			firstInt = (h/3)*(func.getValue(a) + 4*sum1 + 2*sum2  + func.getValue(b));
			
			delta = Math.abs(firstInt - secondInt);
			
			m *= 2;
			h /= 2;
			
			secondInt = firstInt;
			
		} while(delta > eps);
		
		System.out.println(firstInt);
	}
	
	public static void CubeSimpson(DoubleFunc func, double a, double b, double c, double d , double e) {
		
		double delta = 0, eps = 15*e, n = 1.0, m = 1.0,  firstInt = 0, secondInt = 0, hx = 0, hy = 0;
		
		hx = (b - a)/2*n;
		hy = (d - c)/2*m;
		
		do {
		
			double sum = 0;
			
			
			for(int i = 0; i <= n-1; i++) {
				
				for(int j = 0; j <= m-1; j++) {
						
					sum += func.getValue(a+2*i*hx, c+hy*2*j) + 4*func.getValue(a+(2*i+1)*hx, c+hy*2*j) + func.getValue(a+(2*i+2)*hx, c+2*j*hy)+ 4*func.getValue(a+2*i*hx, c+(2*j+1)*hy)+
							16*func.getValue(a+(2*i+1)*hx, c+(2*j+1)*hy) + 4*func.getValue(a+(2*i+2)*hx, c+(2*j+1)*hy) + func.getValue(a+2*i*hx, c+(2*j+2)*hy)+
							4*func.getValue(a+(2*i+1)*hx, c+(2*j+2)*hy) + func.getValue(a+(2*i+2)*hx, c+(2*j+2)*hy);
				}
			}
			
			firstInt = (hx*hy/9)*sum;
			
			delta = Math.abs(firstInt - secondInt);
			
			secondInt = firstInt;
			
			n *= 2; m *= 2 ;
			
			hx /= 2;
			hy /= 2;
		
		} while(delta > eps);
		
		System.out.println(firstInt);
	}
	
	
}
