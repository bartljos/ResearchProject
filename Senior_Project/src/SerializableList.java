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
		
		
		
		if(w.length() > 1)
		{
			if(w.charAt(w.length()-1) == '\n')
				w = w.substring(0, w.length()-2);
		}
		
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
	
	public void printAll(int printDelay)
	{
		//System.out.println(wordList.toString() + " ---> "	+ wordList.size());
		//System.out.println(occurences.toString() + " ---> "	+ occurences.size());
		
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
