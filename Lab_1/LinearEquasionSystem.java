import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class LinearEquasionSystem {
	
	private float[][] matrix;
	private float[] b;
	private float[][] firstMatrix;
	private float[] firstB;
	int rows = 0;
	int columns = 0;
	
	LinearEquasionSystem(String fileName) throws IOException{
		
		int i = 0, j = 0;
		
		Scanner input = new Scanner(new File(fileName));
		rows = input.nextInt();
		columns = input.nextInt();
		
		matrix = new float[rows][columns+1];
		b = new float[rows];
		firstMatrix = new float[rows][columns+1];
		firstB = new float[rows];
		
		for(i = 0; i < rows; ++i)
		{
		    for(j = 0; j < columns+1; ++j)
		    {
		        if(input.hasNextFloat())
		        {
		            matrix[i][j] = input.nextFloat();
		            firstMatrix[i][j] = matrix[i][j];
		        }
		    }
		    b[i] = matrix[i][columns];
		    firstB[i] = firstMatrix[i][columns];
		    
		}
		
		input.close();
	}
	
	public int getRows() {
		return rows;
	}
	public void showEquasion(){
		
		
		for (int i = 0; i < rows; i++) 
		  {
		    for (int j = 0; j < columns; j++) 
		    {
		      System.out.print(matrix[i][j]+"*x"+(j+1));
		      if (j < columns-1)
		        System.out.print(" + ");
		    }
		    System.out.println(" = " + b[i]);
		  }
		System.out.println(" ");
	}
	
	public void swap(int i1, int i2){
		
	      float[] temp;
	      temp=Arrays.copyOf(matrix[i1], matrix[i1].length);
	      matrix[i1]=Arrays.copyOf(matrix[i2], matrix[i2].length);
	      matrix[i2]=Arrays.copyOf(temp, temp.length);
	      
	      float temp1 = b[i1];
	      b[i1] = b[i2];
	      b[i2] = temp1;
	}
	
	public float[] gauss() {
		
		float[] x = new float[rows];
		
		float  max,  eps = 1e-5f; 
		int k, index;
		
		boolean isEmpty = false;
		
		k = 0;
		//Прямой ход
		  while (k < columns) 
		  {
		    // Поиск строки с максимальным matrix[i][k]
		    max = Math.abs(matrix[k][k]);
		    index = k;
		    for (int i = k + 1; i < rows; i++) 
		    {
		      if (Math.abs(matrix[i][k]) > max)
		      {
		        max = Math.abs(matrix[i][k]);
		        index = i;
		      }
		    }
		    //Проверка на вырожденность
		    if (max < eps) 
		    {
		      isEmpty = true;
		      k++;
		      continue;
		    }
		    //Перстановка строк
		    this.swap(k, index);
		    this.showEquasion();
		    System.out.println(" ");
		    // Нормализация уравнений
		    for (int i = k; i < rows; i++) 
		    {
		      float temp = matrix[i][k];
		      
		      if (Math.abs(temp) < eps) continue; // для нулевого коэффициента пропустить
		      
		      for (int j = 0; j < columns; j++) 
		        matrix[i][j] = matrix[i][j] / temp;
		      b[i] = b[i] / temp;
		      if (i == k)  continue; // уравнение не вычитать само из себя
		      for (int j = 0; j < columns; j++)
		        matrix[i][j] = matrix[i][j] - matrix[k][j];
		      b[i] = b[i] - b[k];
		    }
		    k++;
		    this.showEquasion();
		    System.out.println(" ");
		  }
		  /*Сюда впихнуть проверку на совместность*/
		  boolean isEx = true;
		  int num = 0;
		  for(int i = 0; i < rows; i++) {
			  num = 0;
			  for(int j = 0; j < columns-1; j++) {
				  if(matrix[i][j] != 0) break;
				  else {
					  num++;
				  }
			  }
			  if(num == columns && b[i] != 0) {
				  System.out.println("No solutions");
				  isEx = false;
				  return null;
			  }
			  else if(num == columns && b[i] == 0) {
				  System.out.println("Infinity solutions");
				  return null;
			  }
			  
		  } 
		  /*Если совместна и isEmpty == true то бесконечность*/
		  /*Иначе перейти к обратному ходу*/
			
		  if(isEmpty == true && isEx == true) {
			  System.out.println("Infinity solutions");
			  return null;
		  }
		  else {
			  
			  for (k = rows - 1; k >= 0; k--)
			  {
			    x[k] = b[k];
			    for (int i = 0; i < k; i++)
			      b[i] = b[i] - matrix[i][k] * x[k];
			  }
		  }
		  
	
		return x;
	}
	
	public void findVector(float[] x) {
		
		float[] vect = new float[rows];
		float max;
		
		for(int i = 0; i < x.length; i++) {
			for(int j = 0; j < rows; j++) {
				vect[i] += firstMatrix[i][j]*x[j];
			}
			vect[i] -= firstB[i];
		}
		
		max = vect[0];
		System.out.print(vect[0]+" ");
		for(int i = 1; i < vect.length; i++) {
			if(Math.abs(max) < Math.abs(vect[i])) max = vect[i];
			System.out.print(vect[i]+" ");
		}
		System.out.println(" ");
		System.out.println(Math.abs(max));
	
	}
	
}
