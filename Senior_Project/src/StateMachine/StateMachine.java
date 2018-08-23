package StateMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class StateMachine {
	
	ArrayList<String> dictionary = new ArrayList<String>();
	ArrayList<ArrayList<Integer>> transList = new ArrayList<ArrayList<Integer>>();
	ArrayList<Character> transChars = new ArrayList<Character>();
	ArrayList<Integer> acceptingStates = new ArrayList<Integer>();
	int numStates = 0;
	
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
		
		//for(int i = 0; i < dictionary.size(); i++)
		//	System.out.println(dictionary.get(i));
		
		for(int i = 0; i < this.transList.size(); i++)
		{
			System.out.println(this.transList.get(i).toString());
		}
		return true;
	}
	
	public boolean buildFSM()
	{
		if(this.dictionary.size() <= 0)
		{
			this.dictionary.add("cat");
			//this.dictionary.add("rat");
			this.dictionary.add("cattering");
			
			for(int i = 0; i < this.dictionary.size(); i++)
				System.out.println(this.dictionary.get(i));
		}
		
		this.buildCharacterList();
		
		for(int i = 0; i < this.dictionary.size(); i++)
		{
			this.buildState(this.dictionary.get(i));
		}
		for(int i = 0; i < this.transList.size(); i ++)
		{
			System.out.println(this.transList.get(i) + " ");
		}
		
		return true;
	}
	
	private void buildCharacterList()
	{
		for(int i = 0; i < this.dictionary.size(); i++)
		{
			for(int j = 0; j < this.dictionary.get(i).length(); j++)
			{
				if(!this.transChars.contains(this.dictionary.get(i).charAt(j)))
				{
					if(this.dictionary.get(i).charAt(j) >= 'A' && this.dictionary.get(i).charAt(j) <= 'z' || this.dictionary.get(i).charAt(j) == '-')
					this.transChars.add(this.dictionary.get(i).charAt(j));
				}
			}
		}
		System.out.println(this.transChars.size());
		System.out.println((this.transChars.toString()));

	}
	
	private void buildState(String word)
	{
		if(this.transList.size() <= 0)
		{
			this.transList.add(new ArrayList<Integer>());
			for(int i = 0; i < this.transChars.size(); i++)
			{
				this.transList.get(0).add(0);
			}
		}
		
		for(int i = 0; i < word.length(); i++)
		{
			
		}

	}	
}
