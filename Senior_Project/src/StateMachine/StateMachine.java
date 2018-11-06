package StateMachine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Joshua Bartle
 *
 * This class contains the methods for building a Finite State machine for defining words
 * within a dictionary
 */
public class StateMachine {
	
	private ArrayList<String> dictionary = new ArrayList<String>(); // words in the dictionary
	private ArrayList<ArrayList<int[]>> transList = new ArrayList<ArrayList<int[]>>(); // list of transitions
	private ArrayList<Character> transChars = new ArrayList<Character>(); // characters that are part of the transition table
	private ArrayList<Integer> acceptingStates = new ArrayList<Integer>(); // a list of accepting states (ends of words)
	int numStates = 0; // current number of states
	private static StateMachine fsm = null;
	
	
	public static StateMachine getFSM()
	{
		if(fsm == null)
		{
			fsm = new StateMachine();
			System.out.println("create FSM");
		}
		return fsm;
	}
	
	/**
	 * Function for reading in text file of words
	 * @return true (successfully read), false (failed to read file)
	 */
	public boolean readDictionaryFile()
	{
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader("C:\\Users\\Josh\\Desktop\\SrRsrch Resources\\Dictionary Info\\dictionary.txt"));
			String word = "";
			while((word = fr.readLine()) != null)
			{
					word = parseDictionary(word);
					
					if(!word.equals(""))
					{
						System.out.println("word to add: " + word);
						fsm.addWord(word);
					}
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
		return true;
	}
	
	public String parseDictionary(String word)
	{
				if(word.equals(""))
					return "";
				String tmp = "";
				
				if(word.charAt(0) >= 'A' && word.charAt(0) <= 'Z')
				{
					for(int i = 0; i < word.length() && word.charAt(i) != ' ' && word.charAt(i) != '\t'; i++)
					{
						tmp += word.charAt(i);
					}
					
					
				}else
					return "";
				
		for(int i = 1; i < tmp.length(); i++)
		{
			if(tmp.charAt(i) < 'A' || tmp.charAt(i) > 'Z')
			{
				break;
			}else
				if(i == tmp.length()-1)
				{
					return "";
				}
		}
		return tmp.toLowerCase();
		
	}
	
	/**
	 * Function for adding a word to the dictionary
	 * @param word (the word to add to the dictionary)
	 */
	public void addWord(String word)
	{
		if(word.length() <= 0)
			return;
	
		if(Collections.binarySearch(fsm.dictionary, word) < 0)
			fsm.dictionary.add(word);
		//System.out.println("dic: " + StateMachine.getFSM().dictionary.size());
	}
	
	
	/**
	 * Function that takes each word in the dictionary and builds onto the transition
	 * table in order to add the word to the FSM
	 * @return false (no words in dictionary), true (dictionary has been converted to FSM)
	 */
	public boolean buildFSM()
	{
		if(fsm.dictionary.size() <= 0)
		{
			System.out.println("no words");
			return false;
		}
		
		this.buildCharacterList();
		
		for(int i = 0; i < fsm.dictionary.size(); i++)
		{
			fsm.buildState(fsm.dictionary.get(i));
		}
		
		return true;
	}
	
	/**
	 * This function builds the character list for the transitions by evaluating the words in the dictionary and adding any
	 * not yet seen characters to the list
	 */
	private void buildCharacterList()
	{
		for(int i = 0; i < fsm.dictionary.size(); i++)
		{
			for(int j = 0; j < fsm.dictionary.get(i).length(); j++)
			{
				if(!fsm.transChars.contains(fsm.dictionary.get(i).charAt(j))) // if the character is not in the list already
				{
					fsm.transChars.add(fsm.dictionary.get(i).charAt(j));
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
		
		//System.out.println("word " + word);
		if(fsm.transList.size() <= 0) // if there are no states, add one
			fsm.createNewState(0);
		
		// start processing word
		for(int i = 0; i < word.length(); i++) // for the length of the word
		{
			for(int j = 0; j < fsm.transChars.size(); j++) // For all of the characters in the transition table
				if(word.charAt(i) == this.transChars.get(j)) // if the character (j) is equal to the character  (i) in the word 
				{
					int statelist[] = new int[2]; // array to store the current state and the previous state
					statelist[1] = lastState;
					lastState = currentState;
					//System.out.println(fsm.transChars.get(j));
					//System.out.println("tlist: " + fsm.transChars.toString());
					//System.out.println(word.charAt(i));
					if(fsm.transList.get(currentState).get(j)[0] == -1) // if the state is unused or not set set that state
					{
						currentState = fsm.transList.size();
						this.createNewState(currentState);
					
					}else // the state is already initialized (shared by another word) so just follow that path and use that state
						currentState = fsm.transList.get(currentState).get(j)[0];
	

					statelist[0] = currentState;
					fsm.transList.get(lastState).set(j, statelist); // set the value in the transition table for the current state
					//System.out.println("done");
				}
		}
		
		int i = 0;
		while(word.charAt(word.length()-1) != fsm.transChars.get(i))
		{
			i++;
		}
		fsm.transList.get(currentState).get(i)[1] = lastState;
			
		if(!fsm.acceptingStates.contains(currentState))
			fsm.acceptingStates.add(currentState);
		//end processing word
		
	}	
	
	/**
	 * Function for verifying words (are the words in the dictionary?)
	 * @param word, the word in question
	 * @return true 'word' is in the dictionary, false if it is not
	 */
	public boolean verifyWord(String word)
	{
		char c = word.charAt(word.length()-1);
		
		if(c == '\"')
			word = word.substring(0, word.length()-1);
		
		c = word.charAt(word.length()-1);
		if(c =='.' || c== ';' || c ==',' || c==':' || c == '?' || c == '!')
			word = word.substring(0, word.length()-1);
		
		c = word.charAt(0);
		if(c == '\"')
			word = word.substring(1, word.length());
	
		
		int currentState = 0; // start at the start state
		for(int i = 0; i < word.length(); i++)
		{
			for(int j = 0; j < fsm.transChars.size(); j++)
			{	
				if(!fsm.transChars.contains(word.charAt(i))) // if the character is not in the transition table, return false
					return false;
				if(word.charAt(i) == fsm.transChars.get(j)) // if the character (i) in the word is equal to the jth character in the table
				{
					if(fsm.transList.get(currentState).get(j)[0] == -1) // if the state has no transition, return false
					{
						return false;
					}else // else, set the current state to the state associated with the transition
					{
				
						currentState = fsm.transList.get(currentState).get(j)[0];
					}
				}
			}
		}

		// if the word has been processed and the transition ends on an accepting state, return true (is a word)
		if(fsm.acceptingStates.contains(currentState))
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
			fsm.transList.get(index).add(new int[] {-1, -1});
	}
	
	/**
	 * Function for creating a new state
	 * @param index, the index of the state to be created
	 */
	private void createNewState(int index)
	{

		fsm.transList.add(new ArrayList<int[]>()); // add a new state to the transition table
		fsm.fillTransitionList(fsm.transList.size()-1); // initialize the state
		fsm.numStates++;
		
	}
	
	public void printTransitionTable()
	{
		for(int i = 0; i < fsm.transChars.size(); i++)
			System.out.print("    " + fsm.transChars.get(i) + "   ");
		System.out.println();
		
		for(int i = 0; i < fsm.transList.size(); i++)
		{
			for(int j = 0; j < fsm.transList.get(i).size(); j++)
				System.out.print(" (" + fsm.transList.get(i).get(j)[0] + "," + fsm.transList.get(i).get(j)[1] + ") ");
			System.out.println();
		}
	}
	
	/**
	 * Function that reads all the words in the dictionary from the FSM
	 * @return, an arraylist of all words in the dictionary
	 */
	public ArrayList<String> readDictionary()
	{
		//System.out.println("begin dictionary read");
		ArrayList<String> dictionary = new ArrayList<String>();
		
		int currentState = 0; int lastState = 0;
		for(int i = 0; i < fsm.acceptingStates.size(); i++) // Go through all accepting states
		{
			String w = "";
			//System.out.println("\nAccepting States:");
			//System.out.println(this.acceptingStates.get(i));
			
			currentState = fsm.acceptingStates.get(i);
			
			int j = 0;
			while(fsm.transList.get(currentState).get(j)[1] == -1){j++;}
			
			while(currentState != 0) // run backwards from accepting state until the start state is reached
			{
				lastState = currentState;
				
				
				currentState = fsm.transList.get(currentState).get(j)[1];
				
				j = 0;
				while(fsm.transList.get(currentState).get(j)[0] != lastState){j++;}
				
				w = fsm.transChars.get(j) + w;
				
			}
			dictionary.add(w);
			//System.out.println("Word associated with state:");
			//System.out.println(w);
		}
		
		//System.out.println(dictionary.size());
		return dictionary;
	}
}
