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
			fr = new BufferedReader(new FileReader("C:\\Users\\Josh\\Desktop\\SrRsrch Resources\\Dictionary\\en-US.dic"));
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
			System.out.println(i + " " + this.transList.get(i).toString());
		}
		return true;
	}
	
	public void addWord(String word)
	{
		this.dictionary.add(word);
	}
	
	public boolean buildFSM()
	{
		if(this.dictionary.size() <= 0)
		{
			System.out.println("no words");
			return false;
		}
		
		this.buildCharacterList();
		
		for(int i = 0; i < this.dictionary.size(); i++)
		{
			this.buildState(this.dictionary.get(i));
		}
		
		
		//System.out.println("Accepting States: " + this.acceptingStates.toString());
		//System.out.println(this.transChars.size());
		//System.out.println("   "+ (this.transChars.toString()));
		/*for(int i = 0; i < this.transList.size(); i ++)
		{
			if(this.acceptingStates.contains(i))
				System.out.println(i + "A " + this.transList.get(i) + " ");
			else
				System.out.println(i + "  " + this.transList.get(i) + " ");
		}*/
		
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
					//if(this.dictionary.get(i).charAt(j) >= 'A' && this.dictionary.get(i).charAt(j) <= 'z' || this.dictionary.get(i).charAt(j) == '-')
					this.transChars.add(this.dictionary.get(i).charAt(j));
				}
			}
		}
		
	}
	
	private void buildState(String word)
	{
		int currentState = 0;
		if(this.transList.size() <= 0)
			this.createNewState(0);
		// start processing word
		for(int i = 0; i < word.length(); i++)
		{
			for(int j = 0; j < this.transChars.size(); j++)
				if(word.charAt(i) == this.transChars.get(j))
				{
					if(this.transList.get(currentState).get(j) == 0)
					{
						int lastState = currentState;
						currentState = this.transList.size();
						this.createNewState(currentState);
						this.transList.get(lastState).set(j, currentState);
					}else
					{
						currentState = this.transList.get(currentState).get(j);
					}
				}
		}
		if(!this.acceptingStates.contains(currentState))
			this.acceptingStates.add(currentState);
		//end processing word
		
	}	
	
	public boolean verifyWord(String word)
	{
		
		int currentState = 0;
		for(int i = 0; i < word.length(); i++)
		{
			for(int j = 0; j < this.transChars.size(); j++)
			{	
				if(!this.transChars.contains(word.charAt(i)))
					return false;
				if(word.charAt(i) == this.transChars.get(j))
				{
					if(this.transList.get(currentState).get(j) == 0)
					{
						return false;
					}else
					{
						if(i == 0)
							System.out.println();
						System.out.print(currentState + " --" + word.charAt(i) + "-->");
						currentState = this.transList.get(currentState).get(j);
					}
				}
			}
		}
		System.out.println(currentState);
		
		if(this.acceptingStates.contains(currentState))
		{
			return true;
		}
		
		return false;
	}
	
	private void fillTransitionList(int index)
	{
		for(int i = 0; i < this.transChars.size(); i++)
			this.transList.get(index).add(0);
	}
	
	private void createNewState(int index)
	{

		this.transList.add(new ArrayList<Integer>());
		this.fillTransitionList(this.transList.size()-1);
		this.numStates++;
		
	}
	
	
}
