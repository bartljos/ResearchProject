import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Test;

import EditDistance.EditDistance;
import StateMachine.StateMachine;

public class TestClass {

	
	/**
	 * JUnit test for checking that verifying words with the FSM class works
	 */
	@Test
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
	}
	
	/**
	 * JUnit test for testing that the words from the dictionary can be retrieved with the FSM
	 */
	@Test
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
	}
	
	/**
	 * JUnit test for the edit distance algorithm (EditDistance class)
	 */
	@Test
	public void testEditDistanceAlgorithm()
	{
		EditDistance ed = new EditDistance();
		assertEquals(ed.getEditDistance("hilol", "hello"), 2);
		
		assertEquals(ed.getEditDistance("ctas", "cats"), 1);
		
		assertEquals(ed.getEditDistance("ctras", "carts"), 2);
		
		assertEquals(ed.getEditDistance("dadection", "addiction"), 2);
		
		assertEquals(ed.getEditDistance("ha", "hat"), 1);
	}
	
	@Test
	public void testNGram()
	{
		StateMachine.getFSM();
		String s = "The cat climbed up a tree tea the cat is good the boy likes pizza with mushrooms";
		
		String words[] = s.split(" ");
		for(int i = 0; i < words.length; i++)
		{
			StateMachine.getFSM().addWord(words[i]);
			
		}
	
		StateMachine.getFSM().buildFSM();
		
		Algorithms alg = new Algorithms();
		
		String[] choices = {"tea", "tree"};
		double percentage[] = new double[choices.length];
		int[] count = new int[choices.length];
		String corrected = "";
		alg.setText("The cat climbed up a tree The cat climbed up a tea climbed up a tree climbed up a tree climbed up a tree up a tree up a tea");
		
		for(int i = 0; i < 10000; i++)
		{
			alg.setN(2);
			corrected = alg.useEditDistance("The cat climbed up a tee.");
			if(corrected.contains("a tea"))
				count[0]++;
			if(corrected.contains("a tree"))
				count[1]++;
				
		}
		
		for(int i = 0; i < choices.length; i++)
		{
			percentage[i] = (double)count[i]/10000;
			System.out.println(choices[i] + ": " + percentage[i]);
		}
		
		assertEquals(percentage[0], (double)2/7, 0.01);
		assertEquals(percentage[1], (double)5/7, 0.01);
		
	}
	
	/*@Test
	public void testSentence()
	{
		StateMachine.getFSM();
		String s = "The cat climbed up a tree tea the cat is good the boy likes pizza with mushrooms";
		
		String words[] = s.split(" ");
		for(int i = 0; i < words.length; i++)
		{
			StateMachine.getFSM().addWord(words[i]);
			
		}
	
		StateMachine.getFSM().buildFSM();
		
		Algorithms alg = new Algorithms();
		
		String corrected = "";
		alg.setText("the boy likes pizza up a tree the cat likes pizza i like pizza like a");
	
		alg.setN(3);
		corrected = alg.useEditDistance("The cat climbed up a tee. the ct is good. the boy likes pza with mushooms.");
		System.out.println(corrected);
	}*/
	
	
	@Test
	public void testDictionary() throws IOException
	{
		StateMachine m = new StateMachine();
		m.readDictionaryFile();
		m.buildFSM();
		System.out.println("\nFSM built");
		
		Algorithms alg = new Algorithms();
		
		alg.createList(2);
		alg.setN(2);
		alg.setSplit(10);
		alg.addFilePath("susan.txt");
		alg.addFilePath("professor.txt");
		alg.addFilePath("moonstone.txt");
		
		System.out.println("create files");
		String test[] = alg.runFiles();
		
		
		System.out.println("n and text set for analysis");
		//System.out.println(m.readDictionary() + " ---> " + m.readDictionary().size());
		
		
	
		//System.out.println("create N =" + 2);
		
		
		
		
		FileWriter w = new FileWriter("test1");
		w.write(test[0]);
		w.close();
		
		w = new FileWriter("test2");
		w.write(test[1]);
		w.close();
		
		alg.getList(2).printAll();
	
	}


	/*@Test
	public void preProcessNGram()
	{
		StateMachine.getFSM();
		String s = "the cat climbed up a tree tea the cat is good the boy likes pizza with mushrooms likes pizza";
		
		
	
		
		
		String words[] = s.split(" ");
		for(int i = 0; i < words.length; i++)
		{
			//StateMachine.getFSM().addWord(words[i]);
			
		}
		
		//StateMachine.getFSM().buildFSM();
		
		Algorithms alg = new Algorithms();
		
		alg.setText(s);
		for(int i = 1; i <= 3; i++)
		{	
			alg.setN(i);
			alg.createNGram();
		}
		
	}*/
	
}
