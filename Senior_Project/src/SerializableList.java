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
		int result = Collections.binarySearch(wordList, w);
		
		if(w.length() > 1)
		{
			if(w.charAt(w.length()-1) == '\n')
				w = w.substring(0, w.length()-2);
		}
		
		if(result < 0)
		{
			wordList.add((result+1) * -1, w);
			occurences.add((result +1) * -1, 1);
			
		}else
		{
		
	
			occurences.set(result, occurences.get(result) + 1);
		}
		
		
	}
	
	public void printAll()
	{
		System.out.println(wordList.toString() + " ---> "	+ wordList.size());
		System.out.println(occurences.toString() + " ---> "	+ occurences.size());
	}
	

}
