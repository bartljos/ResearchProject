
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
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
		
	]
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
	 * @throws InterruptedException 
	 * @throws IOException 
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
	
	/*@Test
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
		
	}*/
	
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
	
	/*@Test
	public void testEditDistance()
	{
		EditDistance ed = new EditDistance();
		ArrayList<String> dictionary = StateMachine.getFSM().readDictionary();
		System.out.println("dictionary read");
		
		for(int i = 0; i < dictionary.size(); i++)
			ed.getEditDistance("shoxs", dictionary.get(i));
		
		System.out.println(ed.getCandidates().toString());
	
	}*/
	

	@Before
	public void createStateMachine()
	{

		StateMachine.getFSM().readDictionaryFile();
		StateMachine.getFSM().buildFSM();
		System.out.println("\nFSM built");
	}
	
	
	/*@Test
	public void testStemmingClass()
	{
		System.out.println(StateMachine.getFSM().verifyWord("happiness"));
		System.out.println("word: " + StemmingClass.checkWord("happiness"));
		System.out.println(StateMachine.getFSM().verifyWord("happiness"));
		
		System.out.println(StateMachine.getFSM().verifyWord("coolness"));
		System.out.println("word: " + StemmingClass.checkWord("coolness"));
		System.out.println(StateMachine.getFSM().verifyWord("coolness"));
		
		System.out.println(StateMachine.getFSM().verifyWord("fanciness"));
		System.out.println("word: " + StemmingClass.checkWord("fanciness"));
		System.out.println(StateMachine.getFSM().verifyWord("fanciness"));
		
		System.out.println(StateMachine.getFSM().verifyWord("penniless"));
		System.out.println("word: " + StemmingClass.checkWord("penniless"));
		System.out.println(StateMachine.getFSM().verifyWord("penniless"));
		
		System.out.println(StateMachine.getFSM().verifyWord("happier"));
		System.out.println("word: " + StemmingClass.checkWord("happier"));
		System.out.println(StateMachine.getFSM().verifyWord("happier"));
		
		System.out.println(StateMachine.getFSM().verifyWord("funnier"));
		System.out.println("word: " + StemmingClass.checkWord("funnier"));
		System.out.println(StateMachine.getFSM().verifyWord("funnier"));
		
		System.out.println(StateMachine.getFSM().verifyWord("hats"));
		System.out.println("word: " + StemmingClass.checkWord("hats"));
		System.out.println(StateMachine.getFSM().verifyWord("hats"));
		

		System.out.println(StateMachine.getFSM().verifyWord("siblings"));
		System.out.println("word: " + StemmingClass.checkWord("siblings"));
		System.out.println(StateMachine.getFSM().verifyWord("siblings"));
		
		System.out.println("\nxxxten");
		System.out.println(StateMachine.getFSM().verifyWord("xxxten"));
		System.out.println("word: " + StemmingClass.checkWord("xxxten"));
		System.out.println(StateMachine.getFSM().verifyWord("xxxten"));
		
		System.out.println("\nxxxous");
		System.out.println(StateMachine.getFSM().verifyWord("xxxous"));
		System.out.println("word: " + StemmingClass.checkWord("xxxous"));
		System.out.println(StateMachine.getFSM().verifyWord("xxxous"));
		
		System.out.println("\nxxx's");
		System.out.println(StateMachine.getFSM().verifyWord("xxx's"));
		System.out.println("word: " + StemmingClass.checkWord("xxx's"));
		System.out.println(StateMachine.getFSM().verifyWord("xxx's"));
		
		System.out.println("\nxxxious");
		System.out.println(StateMachine.getFSM().verifyWord("xxxious"));
		System.out.println("word: " + StemmingClass.checkWord("xxxious"));
		System.out.println(StateMachine.getFSM().verifyWord("xxxious"));
	
		
		System.out.println("\nxxxious");
		System.out.println(StateMachine.getFSM().verifyWord("xxx's"));
		System.out.println("word: " + StemmingClass.checkWord("xxx's"));
		System.out.println(StateMachine.getFSM().verifyWord("xxx's"));
		
		System.out.println("\nxxxd");
		System.out.println(StateMachine.getFSM().verifyWord("xxxd"));
		System.out.println("word: " + StemmingClass.checkWord("xxxd"));
		System.out.println(StateMachine.getFSM().verifyWord("xxxd"));
		
		System.out.println("\nxxxed");
		System.out.println(StateMachine.getFSM().verifyWord("xxxed"));
		System.out.println("word: " + StemmingClass.checkWord("xxxed"));
		System.out.println(StateMachine.getFSM().verifyWord("xxxed"));
	}*/
	
	
	/*@Test
	public void testEditDistanceAtZero()
	{
		EditDistance ed = new EditDistance();
		ArrayList<String> dictionary = StateMachine.getFSM().readDictionary();
		
		for(int i = 0; i < dictionary.size(); i++)
			ed.getEditDistance("cat", dictionary.get(i));
		
		System.out.println(ed.getCandidates().toString() + " contains: " + ed.getCandidates().contains("cat"));
	}*/
	
	
	@Test
	public void testLiveDemo() throws InterruptedException, IOException
	{
		DemoClass demo = new DemoClass();
		demo.startDemo();
	}
	
	/*@Test
	public void testDictionary() throws IOException
	{
	
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		String[] fileList = getFileSystem();
		
		
		Algorithms alg = new Algorithms();

		alg.setSplit(100);
	
		for(int i = 0; i < fileList.length; i++)
		{
			System.out.println(fileList[i]);
			alg.addFilePath(fileList[i]);
		}
		
		System.out.println("create files");
		int max_n = 8;
		int trials = 15;
		
		
		
		String test[] = alg.runFiles();

		FileWriter w = new FileWriter("test1");
		w.write(test[0]);
		w.close();
		
		w = new FileWriter("test2");
		w.write(test[1]);
		w.close();
		
		alg.createList(max_n);
		for(int i = 1; i <= max_n; i++)
		{
			alg.setN(i);
			alg.createNGram(i);
			alg.getList(i).printAll(0, i, true);
		}

		
		String filename =  "RESULTS-2";
		for(int i = 2; i < trials; i++)
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
			bw.write(System.lineSeparator() + "TRIAL #" + (i+1) + "" + System.lineSeparator());
			bw.close();
			alg.createTemporyTestText("test2", .03);
			
			for(int n = 0; n <= max_n; n++)
			{
				
			
				bw = new BufferedWriter(new FileWriter(filename, true));
				
				alg.setN(n);
			
				
				System.out.println("n and text set for analysis");
		
				
				int[] results = alg.comparTwoDocuments("modifiedTestText", "test2");
				bw.write("n = " + n + "" + System.lineSeparator());
				bw.write(System.lineSeparator() + "BEFORE CHANGES" + System.lineSeparator());
				bw.write("similarities: " + results[0] + "    differences:  " + results[1] + System.lineSeparator());
				bw.write("Percent Errors in Text: " + ((float)results[1]/(results[1] + results[0]) * 100));
				bw.close();
				
				
				alg.makeCorrectionToTestText("modifiedTestText");
				alg.writeCorrectedTextFile("modifiedTestText", "correctedText");
		
				bw = new BufferedWriter(new FileWriter(filename, true));
				results = alg.comparTwoDocuments("correctedText", "test2");
				bw.write(System.lineSeparator() + "AFTER CHANGES");
				bw.write(System.lineSeparator() + "similarities: " + results[0] + "    differences:  " + results[1]);
				bw.write(System.lineSeparator() + "Percent Errors in Text: " + ((float)results[1]/(results[1] + results[0]) * 100));
				bw.write(System.lineSeparator() + "-----------------------------" + System.lineSeparator());
				
				bw.close();
			}
		}
	}*?

	/*@Test
	public void testComparingDocs()
	{
		Algorithms alg = new Algorithms();
		
	}*/
	
	/*@Test
	public void testWordReplacement()
	{
		Algorithms alg = new Algorithms();
		assertEquals(alg.placeWord("helo.", "hello"), "hello.");
		assertEquals(alg.placeWord("\"helo.", "hello"), "\"hello.");
		assertEquals(alg.placeWord("\"helo.\"", "hello"), "\"hello.\"");
		assertEquals(alg.placeWord("\"gf?\"", "hello"), "\"hello?\"");
		assertEquals(alg.placeWord("fried!\"", "friend"), "friend!\"");
	
	}*/
	
	/*@Test
	public void test()
	{
		Algorithms alg = new Algorithms();
		alg.setN(2);
		alg.createList(2);
		alg.readListFromFile(2);
		
		System.out.println(Collections.binarySearch(alg.getList(2).getWordList(), "not have"));
		System.out.println(alg.getCorrectWord(new String[] {"not", "nfve"}));
	}*/
	
	public String[] getFileSystem() throws IOException
	{
		ArrayList<String> tmp = new ArrayList<String>();
		File fs = new File("310 corpus");
		System.out.println("is dir: " + fs.isDirectory());
		
		String[] files = fs.list();
		
		for(int i = 0; i < files.length; i++)
		{
			fs = new File("310 corpus/" + files[i]);
			System.out.println(fs.isDirectory());
			String[] leaves = fs.list();
			
			for(int j = 0; j < leaves.length; j++)
			{
				System.out.println("file: " + leaves[j]);
				tmp.add("310 corpus/" + files[i] + "/" + leaves[j]);
			}
		}
		
		String[] tmp2 = new String[tmp.size()];
		tmp.toArray(tmp2);
		return tmp2;
		
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
