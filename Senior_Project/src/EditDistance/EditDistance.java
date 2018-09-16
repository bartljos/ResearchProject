package EditDistance;

import java.util.ArrayList;

public class EditDistance {
	
	ArrayList<String> candidates = new ArrayList<String>();
	
	public int getEditDistance(String w1, String w2)
	{
		
		System.out.println("\nrunning edit distance check:");
		int ed = 0;
		w1 = " " + w1;
		w2 = " " + w2;
		
		int matrix[][] = new int[w1.length()][w2.length()];
		
		
		for(int i = 0; i < w1.length(); i++)
			matrix[i][0] = i;
		for(int j = 0; j < w2.length(); j++)
			matrix[0][j] = j;
		
		
		for(int i = 0; i < w1.length(); i++)
		{	for(int j = 0; j < w2.length(); j++)
			{
				System.out.print("  " + matrix[i][j]);
			}
			System.out.println();
		}
		
		for(int i = 1; i < w1.length(); i++)
		{	for(int j = 1; j < w2.length(); j++)
			{
				if(w1.charAt(i) == w2.charAt(j))
					matrix[i][j] = this.getMin(new int[] {matrix[i-1][j-1]});
				else
					if(w1.charAt(i-1) == w2.charAt(j) && w1.charAt(i) == w2.charAt(j-1))
						matrix[i][j] = 1 + this.getMin(new int[] {matrix[i-2][j-2], matrix[i-1][j], matrix[i][j-1]});
					else
						if(w1.charAt(i) != w2.charAt(j))
							matrix[i][j] = 1 + this.getMin(new int[] {matrix[i-1][j-1], matrix[i-1][j], matrix[i][j-1]});
							
						
				
			}
			System.out.println();
		}
		
		System.out.print("\n ");
		for(int i = 0; i < w2.length(); i++)
		{	
			System.out.print("  " + w2.charAt(i));
		}
		System.out.println();
		
		for(int i = 0; i < w1.length(); i++)
		{	
			System.out.print(w1.charAt(i));
			for(int j = 0; j < w2.length(); j++)
			{
				System.out.print("  " + matrix[i][j]);
			}
			System.out.println();
		}
		ed = matrix[w1.length()-1][w2.length()-1];
		
		System.out.println(w1 + " ----> " + w2 + " = " + ed + "\n\n");
		return ed;
	}
	
	
	private int getMin(int[] array)
	{
		int min = array[0];
		
		for(int i = 1; i < array.length; i++)
			if(array[i] < min)
				min = array[i];
		
		return min;
	}
}
