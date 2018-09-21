package StateMachine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Joshua Bartle
 *
 * This class contains the methods for building a Finite State machine for defining words
 * within a dictionary
 */
public class StateMachine {
	
	ArrayList<String> dictionary = new ArrayList<String>(); // words in the dictionary
	ArrayList<ArrayList<int[]>> transList = new ArrayList<ArrayList<int[]>>(); // list of transitions
	ArrayList<Character> transChars = new ArrayList<Character>(); // characters that are part of the transition table
	ArrayList<Integer> acceptingStates = new ArrayList<Integer>(); // a list of accepting states (ends of words)
	int numStates = 0; // current number of states
	
	/**
	 * Function for reading in text file of words
	 * @return true (successfully read), false (failed to read file)
	 */
	public boolean readDictionaryFile()
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
		
		for(int i = 0; i < this.transList.size(); i++)
		{
			System.out.println(i + " " + this.transList.get(i).toString());
		}
		return true;
	}
	
	/**
	 * Function for adding a word to the dictionary
	 * @param word (the word to add to the dictionary)
	 */
	public void addWord(String word)
	{
		this.dictionary.add(word);
	}
	
	/**
	 * Function that takes each word in the dictionary and builds onto the transition
	 * table in order to add the word to the FSM
	 * @return false (no words in dictionary), true (dictionary has been converted to FSM)
	 */
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
		
		return true;
	}
	
	/**
	 * This function builds the character list for the transitions by evaluating the words in the dictionary and adding any
	 * not yet seen characters to the list
	 */
	private void buildCharacterList()
	{
		for(int i = 0; i < this.dictionary.size(); i++)
		{
			for(int j = 0; j < this.dictionary.get(i).length(); j++)
			{
				if(!this.transChars.contains(this.dictionary.get(i).charAt(j))) // if the character is not in the list already
				{
					this.transChars.add(this.dictionary.get(i).charAt(j));
				}
			}
		}
		
	}
	
	/**
	 * This function build the necessary states for a word, from the start state to an accepting  state
	 * @param word, the word to add to the FSM
	 */
	private void buildState(String word)
	{
		int currentState = 0;
		int lastState = 0;
		
		if(this.transList.size() <= 0) // if there are no states, add one
			this.createNewState(0);
		
		// start processing word
		for(int i = 0; i < word.length(); i++) // for the length of the word
		{
			for(int j = 0; j < this.transChars.size(); j++) // For all of the characters in the transition table
				if(word.charAt(i) == this.transChars.get(j)) // if the character (j) is equal to the character  (i) in the word 
				{
					int statelist[] = new int[2]; // array to store the current state and the previous state
					statelist[1] = lastState;
					lastState = currentState;
					
					if(this.transList.get(currentState).get(j)[0] == -1) // if the state is unused or not set set that state
					{
						currentState = this.transList.size();
						this.createNewState(currentState);
					
					}else // the state is already initialized (shared by another word) so just follow that path and use that state
						currentState = this.transList.get(currentState).get(j)[0];
	

					statelist[0] = currentState;
					this.transList.get(lastState).set(j, statelist); // set the value in the transition table for the current state
				}
		}
		
		int i = 0;
		while(word.charAt(word.length()-1) != this.transChars.get(i))
		{
			i++;
		}
		this.transList.get(currentState).get(i)[1] = lastState;
			
		if(!this.acceptingStates.contains(currentState))
			this.acceptingStates.add(currentState);
		//end processing word
		
	}	
	
	/**
	 * Function for verifying words (are the words in the dictionary?)
	 * @param word, the word in question
	 * @return true 'word' is in the dictionary, false if it is not
	 */
	public boolean verifyWord(String word)
	{
		
		int currentState = 0; // start at the start state
		for(int i = 0; i < word.length(); i++)
		{
			for(int j = 0; j < this.transChars.size(); j++)
			{	
				if(!this.transChars.contains(word.charAt(i))) // if the character is not in the transition table, return false
					return false;
				if(word.charAt(i) == this.transChars.get(j)) // if the character (i) in the word is equal to the jth character in the table
				{
					if(this.transList.get(currentState).get(j)[0] == -1) // if the state has no transition, return false
					{
						return false;
					}else // else, set the current state to the state associated with the transition
					{
				
						currentState = this.transList.get(currentState).get(j)[0];
					}
				}
			}
		}

		// if the word has been processed and the transition ends on an accepting state, return true (is a word)
		if(this.acceptingStates.contains(currentState))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * When a new state is created fill that state with initial values of -1 for each character transition
	 * @param index, the state to be initialized
	 */
	private void fillTransitionList(int index)
	{
		for(int i = 0; i < this.transChars.size(); i++)
			this.transList.get(index).add(new int[] {-1, -1});
	}
	
	/**
	 * Function for creating a new state
	 * @param index, the index of the state to be created
	 */
	private void createNewState(int index)
	{

		this.transList.add(new ArrayList<int[]>()); // add a new state to the transition table
		this.fillTransitionList(this.transList.size()-1); // initialize the state
		this.numStates++;
		
	}
	
	public void printTransitionTable()
	{
		for(int i = 0; i < this.transChars.size(); i++)
			System.out.print("    " + this.transChars.get(i) + "   ");
		System.out.println();
		
		for(int i = 0; i < this.transList.size(); i++)
		{
			for(int j = 0; j < this.transList.get(i).size(); j++)
				System.out.print(" (" + this.transList.get(i).get(j)[0] + "," + this.transList.get(i).get(j)[1] + ") ");
			System.out.println();
		}
	}
	
	/**
	 * Function that reads all the words in the dictionary from the FSM
	 * @return, an arraylist of all words in the dictionary
	 */
	public ArrayList<String> readDictionary()
	{
		System.out.println("begin dictionary read");
		ArrayList<String> dictionary = new ArrayList<String>();
		
		
		int currentState = 0; int lastState = 0;
		for(int i = 0; i < this.acceptingStates.size(); i++) // Go through all accepting states
		{
			String w = "";
			System.out.println("\nAccepting States:");
			System.out.println(this.acceptingStates.get(i));
			
			currentState = this.acceptingStates.get(i);
			
			int j = 0;
			while(this.transList.get(currentState).get(j)[1] == -1){j++;}
			
			while(currentState != 0) // run backwards from accepting state until the start state is reached
			{
				lastState = currentState;
				
				
				currentState = this.transList.get(currentState).get(j)[1];
				
				j = 0;
				while(this.transList.get(currentState).get(j)[0] != lastState){j++;}
				
				w = this.transChars.get(j) + w;
				
			}
			
			System.out.println("Word associated with state:");
			System.out.println(w);
		}
		
		//System.out.println(dictionary.size());
		return dictionary;
	}
}
