import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerializableList implements Serializable{

	private static final long serialVersionUID = 1L;
	List<String> wordList = new ArrayList<String>();
	List<Integer> occurences = new ArrayList<Integer>();

	
	public void addWord(String w)
	{
		
		
		w = w.toLowerCase();
		int result = Collections.binarySearch(wordList, w);
		
		if(result < 0)
		{
			wordList.add((result+1) * -1, w);
			occurences.add((result +1) * -1, 1);
			
		}else
		{
		
	
			occurences.set(result, occurences.get(result) + 1);
		}
		
		
	}
	
	public List<Integer> getOccurences()
	{
		return this.occurences;
	}
	
	public List<String> getWordList()
	{
		return this.wordList;
	}
	
	public void printAll(int printDelay, int n, boolean writeToFile)
	{
		if(writeToFile)
		{
			try {
			BufferedWriter br = new BufferedWriter(new FileWriter("occurences_n=" + n));
			for(int i = 0; i < wordList.size(); i++)
			{
				br.write(wordList.get(i) + " ---> " + occurences.get(i) + System.lineSeparator());
			}
			br.close();
			} catch (IOException e1) {}
		}else {
		
			for(int i = 0; i < wordList.size(); i++)
			{
				try {
					Thread.sleep(printDelay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(" " + wordList.get(i) +  " --->  " + occurences.get(i));
			}
		
		}
			
	}
}
