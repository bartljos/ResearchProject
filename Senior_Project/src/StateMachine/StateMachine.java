package StateMachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class StateMachine {
	
	ArrayList<String> dictionary = new ArrayList<String>();
	
	public boolean readDictionary()
	{
		FileReader fr = null;
		try {
			 fr = new FileReader("C:\\Users\\Josh\\Desktop\\SrRsrch Resources\\Dictionary\\wordlist.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
