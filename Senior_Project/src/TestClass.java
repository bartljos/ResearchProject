import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import EditDistance.EditDistance;
import StateMachine.StateMachine;

public class TestClass {

	
	/**
	 * JUnit test for checking that verifying words with the FSM class works
	 */
	/*@Test
	public void testFSMDictionaryCheck() 
	{
		
		StateMachine fsm = new StateMachine();
		ArrayList<String> testWords = new ArrayList<String>();
		
		testWords.add("cat");
		testWords.add("fat");
		testWords.add("can");
		testWords.add("dip");
		
		System.out.println("building state machine");
	
		
		for(int i = 0; i < testWords.size(); i++)
		{
			fsm.addWord(testWords.get(i));
			
		}
		fsm.buildFSM();
		
		for(char x = 'a'; x <= 'z'; x++)
			for(char y = 'a'; y <= 'z'; y++)
				for(char z = 'a'; z <= 'z'; z++)
				{
					String word = x + "" + y + "" + z + "";
					//System.out.println(word);
					if(testWords.contains(word))
					{
						assertTrue(fsm.verifyWord(word));
						System.out.println(word);
					}
					else
						assertFalse(fsm.verifyWord(word));
				}
		
	
		fsm.printTransitionTable();
	}*/
	
	/**
	 * JUnit test for testing that the words from the dictionary can be retrieved with the FSM
	 */
	/*@Test
	public void runThroughDictionary()
	{
		StateMachine fsm = new StateMachine();
		ArrayList<String> testWords = new ArrayList<String>();
		
		testWords.add("cat");
		testWords.add("fat");
		testWords.add("can");
		testWords.add("dip");
		
		System.out.println("building state machine");
	
		
		for(int i = 0; i < testWords.size(); i++)
		{
			fsm.addWord(testWords.get(i));
			
		}
		fsm.buildFSM();
		
		ArrayList<String> words = fsm.readDictionary();
		for(int i = 0; i < words.size(); i++)
			assertTrue(testWords.contains(words.get(i)));
	}*/
	
	/**
	 * JUnit test for the edit distance algorithm (EditDistance class)
	 */
	/*@Test
	public void testEditDistanceAlgorithm()
	{
		EditDistance ed = new EditDistance();
		assertEquals(ed.getEditDistance("hilol", "hello"), 2);
		
		assertEquals(ed.getEditDistance("ctas", "cats"), 1);
		
		assertEquals(ed.getEditDistance("ctras", "carts"), 2);
		
		assertEquals(ed.getEditDistance("dadection", "addiction"), 2);
		
		assertEquals(ed.getEditDistance("ha", "hat"), 1);
	}*/
	
	@Test
	public void testSentence()
	{
		StateMachine.getFSM();
		String s = "The cat climbed up a tree tea";
		
		String words[] = s.split(" ");
		for(int i = 0; i < words.length; i++)
		{
			StateMachine.getFSM().addWord(words[i]);
			//System.out.println(StateMachine.getFSM().readDictionary().size());
		}
		//StateMachine.getFSM().addWord("climded");
		StateMachine.getFSM().buildFSM();
		
		Algorithms alg = new Algorithms();
		alg.useEditDistance("The cat climbed up a tee");
	}
	
	
}
