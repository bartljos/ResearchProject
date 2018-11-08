package EditDistance;

import java.util.ArrayList;

public class EditDistance {
	
	ArrayList<String> candidates = new ArrayList<String>();
	private int threshold = -1;
	
	
	public EditDistance()
	{
		threshold = 2;
	}
	
	public ArrayList<String> getCandidates()
	{
		return this.candidates;
	}
	/**
	 * 
	 * @param w1, the first word (incorrect spelling)
	 * @param w2, the second word (the word to be compared)
	 * @return the edit distance between w1 and w2
	 */
	public int getEditDistance(String w1, String w2)
	{
		w1 = w1.toLowerCase();
		w2 = w2.toLowerCase();
		//System.out.println("\nrunning edit distance check:");
		int ed = 0;
		w1 = " " + w1;
		w2 = " " + w2;
		
		int matrix[][] = new int[w1.length()][w2.length()];
		
		
		for(int i = 0; i < w1.length(); i++)
			matrix[i][0] = i;
		for(int j = 0; j < w2.length(); j++)
			matrix[0][j] = j;
		
		
		/*for(int i = 0; i < w1.length(); i++)
		{	for(int j = 0; j < w2.length(); j++)
			{
				System.out.print("  " + matrix[i][j]);
			}
			System.out.println();
		}*/
		
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
			//System.out.println();
		}
		
		//System.out.print("\n");
		/*for(int i = 0; i < w2.length(); i++)
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
		}*/
		ed = matrix[w1.length()-1][w2.length()-1];
		
		//System.out.println(w1 + " ----> " + w2 + " = " + ed + "\n\n");
		//System.out.println("threshold: " + this.threshold);
		if (ed == threshold)
		{
			this.candidates.add(w2.substring(1));
		}else
			if(ed < threshold && ed != 0)
			{
				threshold = ed; //System.out.println(candidates.toString());
				this.candidates.clear();
				this.candidates.add(w2.substring(1));
			}
		//System.out.println("candidates: ");
		//for(int i = 0; i < this.candidates.size(); i++)
		//	System.out.println(this.candidates.get(i));
			
		return ed;
	}
	
	/**
	 * @return the current threshold, or max edit distance allowed
	 */
	public int getThreshold()
	{
		return this.threshold;
	}
	
	/**
	 * Function for getting the min value in an array
	 * @param array, an array of integer values
	 * @return, the minimum value in the array
	 */
	private int getMin(int[] array)
	{
		int min = array[0];
		
		for(int i = 1; i < array.length; i++)
			if(array[i] < min)
				min = array[i];
		
		return min;
	}
	
	public boolean clearCandidates()
	{
		this.candidates.clear();
		//this.threshold = 5;
		if(this.candidates.isEmpty())
			return true;
		else
			return false;
	}
}
