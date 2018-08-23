package StateMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class StateMachine {
	
	ArrayList<String> dictionary = new ArrayList<String>();
	
	public boolean readDictionary()
	{
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader("C:\\Users\\Josh\\Desktop\\SrRsrch Resources\\Dictionary\\wordlist.txt"));
			String word = "";
			while((word = fr.readLine()) != null)
			{
					dictionary.add(word);
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		for(int i = 0; i < dictionary.size(); i++)
			System.out.println(dictionary.get(i));
		return true;
	}
}
