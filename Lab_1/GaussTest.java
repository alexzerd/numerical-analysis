import java.io.IOException;
import java.util.Scanner;


public class GaussTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		Scanner in = new Scanner(System.in);
        System.out.print("File path: ");
        String str = in.nextLine();
        in.close();
        
		LinearEquasionSystem A = new LinearEquasionSystem(str);
		
		A.showEquasion();
		
		float[] sol = new float[A.getRows()]; 
		sol = A.gauss();
		
		for(int i = 0; i < A.getRows(); i++) {
			if(sol != null) {
			System.out.print(sol[i]+" ");
			}
		}
		System.out.println(" ");
		
		A.findVector(sol);
	}

}
