import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import EditDistance.EditDistance;
import StateMachine.StateMachine;

public class Algorithms {
	
	EditDistance ed = new EditDistance();
	private String text = "";
	private int n = -1; private int splitPoint = 0;
	private ArrayList<String> filePaths = new ArrayList<String>();
	private ArrayList<SerializableList> lists = new ArrayList<SerializableList>();
	private ArrayList<String> dictionary = StateMachine.getFSM().readDictionary();
	
	
	public String getWordStringFromFile(String path)
	{
		String tmp = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String line = "";
			while((line = br.readLine()) != null)
			{
				tmp += line;
			}
			
			
			br.close();
		}catch(Exception e) {	System.out.println ("File " + path + "NOT available");}
		
		
		tmp  = tmp.toLowerCase();
		while(tmp.contains("  "))
		{
			tmp = tmp.replaceAll("  ", " ");
		}
		tmp = tmp.replaceAll(System.lineSeparator(), "").replaceAll("\\.", "").replaceAll(",", "").replaceAll(";", "").replaceAll("\\?", "").replaceAll("\\!", "").replaceAll("\"", "").replaceAll(":", "");

		return tmp;
	}
	
	public void createNGram(int n)
	{

		boolean listAvailable = false;
		
		if(n > 0)
		{
			try {
				FileInputStream f = new FileInputStream("n=" + n);
				ObjectInputStream o = new ObjectInputStream(f);
				this.lists.set(n-1,(SerializableList)o.readObject());
				o.close();
				listAvailable = true;
				System.out.println("A list for n=" + n + " is available");
			}catch(Exception e) {	System.out.println ("A list for n=" + n + " is NOT available");}
	
			if(!listAvailable)
			{
				System.out.println("creating n gram");
				if(this.text.equals(""))
					this.setText(this.getWordStringFromFile("test1"));
				
				this.createNGram();
			}else
				System.out.println("List was read from file");

			if(!listAvailable)
			{
				try {
					FileOutputStream f = new FileOutputStream("n=" + n);
					ObjectOutputStream o = new ObjectOutputStream(f);
					o.writeObject(this.lists.get(n-1));
					o.close();
				}catch(Exception e) {}
				
			}
		}
	}
	
	public String[] runFiles()
	{
		String[] finalSet = {"", ""};
		String[] tmp = {"", ""};
	
		for(int i = 0; i < filePaths.size(); i++)
		{
			System.out.println("now working on: " + filePaths.get(i));
			System.out.println("parsing file");
			tmp = this.setFileAsSource(filePaths.get(i));
			finalSet[0] += tmp[0] + System.lineSeparator();
			finalSet[1] += tmp[1] + System.lineSeparator();
	
		}
		
		this.setText(finalSet[0]);
		return finalSet;
	}	
	
	
	public boolean readListFromFile(int n)
	{
		try {
			FileInputStream f = new FileInputStream("n=" + n);
			ObjectInputStream o = new ObjectInputStream(f);
			this.lists.set(n-1,(SerializableList)o.readObject());
			o.close();
			System.out.println("A list for n=" + n + " is available");
		}catch(Exception e) {	System.out.println ("A list for n=" + n + " is NOT available");}
		
		return true;
	}
	
	public void createList(int n)
	{
		if(lists.size() < n)
			for(int i = lists.size(); i < n; i++)
				lists.add(new SerializableList());

		System.out.println("list size: " + lists.size());
	}
	
	
	public SerializableList getList(int n)
	{
		
		return lists.get(n-1);
	}
	
	public void setSplit(int line)
	{
		splitPoint = line;
	}
	
	public String[] setFileAsSource(String filepath)
	{
		boolean split = false; int line = 0;
		try {
			BufferedReader fr = new BufferedReader(new FileReader(filepath));
			String tmp = ""; String s = ""; String s2 = "";
			
			while((tmp = fr.readLine()) != null)
			{
				//System.out.println(tmp);
				if(tmp.equals(""))
				{
					if(split)
					{
						split = false;
						line = 0;
					}else
					{
						line++;
					}
					
					if(line == splitPoint)
						split = true;
					
				}
				
				if(split == false)
					s += " " + tmp + System.lineSeparator();
				else
					s2 += " " + tmp + System.lineSeparator();
			}
			fr.close();
			
			
			return new String[]{s, s2};
			
		}catch(Exception e) {System.out.println("error reading file"); e.printStackTrace();}
		
		return null;
	}
	
	public void setText(String text)
	{
		this.text = text;
		
		while(this.text.contains("  "))
		{
			this.text = this.text.replaceAll("  ", " ");
		}
		
		this.text = this.text.replaceAll("\n", "").replaceAll("\\.", "").replaceAll("\\?", "").replaceAll("!", "");
		this.text = this.text.replaceAll("\\;", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(System.lineSeparator(), "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\r\n", "");
		this.text = this.text.replaceAll(",", "").replaceAll("\\*", "").replaceAll("  ", " ").replaceAll("\\[", "");
		this.text = this.text.replaceAll("]", "").replaceAll("_", "").replaceAll(",", "").replaceAll("\"", "").replaceAll(" '",  " ");
	
	}
	
	public void setN(int n)
	{
		this.n = n;
	}
	
	public String useEditDistance(String s)
	{
		ed = new EditDistance();
		String words[] = s.split(" ");
		
		ArrayList<String> dic = StateMachine.getFSM().readDictionary();
		System.out.println(StateMachine.getFSM().readDictionary().size());
		for(int i =0; i < words.length; i++)
		{
			
			System.out.print(words[i]);
			
			boolean isWord = false;
			char punc = ' ';
			if(words[i].charAt(words[i].length()-1) == '.')
			{
				isWord = StateMachine.getFSM().verifyWord(words[i].substring(0, words[i].length()-1));
				punc = words[i].charAt(words[i].length()-1);
			}
			else
				isWord = StateMachine.getFSM().verifyWord(words[i]);
			
			if(!isWord)
			{
				System.out.println(": NOT a word");
				
				for(int j = 0; j < dic.size(); j++)
				{
					if(words[i].charAt(words[i].length()-1) == '.')
						ed.getEditDistance(words[i].substring(0, words[i].length()-1), dic.get(j));
					else
						ed.getEditDistance(words[i], dic.get(j));
					
				}
				
				if(ed.getCandidates().size() == 1)
				{
					words[i] = ed.getCandidates().get(0);
				}else
					{
						if(i >= n)
						{
							String pre = "";
							for(int l = i-1; l > i-n && i > 0; l--)
							{
								pre = words[l] + " " +  pre;
							}
							//System.out.println("prestring: " + pre);
							String tmp = "";
							//tmp = this.nGramAnalysis(this.n, text, pre);
							if(punc != ' ')
								tmp += punc;
							if(!tmp.equals(""))
								words[i] = tmp;
						}
					}
				ed.clearCandidates();
			}
			else
				System.out.println(": IS a word");
			ed.clearCandidates();
		}
		
		String corrected = "";
		for(int i = 0; i < words.length; i++)
		{
			corrected += words[i] + " ";
		}
		
		//System.out.println(corrected);
		return corrected;
	}
	
	public void createNGram()
	{
		int i = 0;
		String tmp[] = null;
		
		while(this.text.charAt(i) == ' ')
		{
			i++;
		}
		
		text = text.substring(i, text.length());
		tmp = text.split(" ");
		
		System.out.println("text length: " + tmp.length);
		
		for(i = 0; i < tmp.length; i++)
		{
			
			if(i%100 == 0)
			{
				System.out.println("completed: " + (((float)i/tmp.length) * 100) + " %");
			}
			
			String n_words = "";
		
			for(int length = 0; length < n && i < tmp.length; length++)
			{
				n_words += tmp[i] + " ";
				i++;
				//System.out.println(n_words);
			}
			n_words = n_words.substring(0, n_words.length()-1);
			this.getList(this.n).addWord(n_words);
			
			if(i == tmp.length)
				return;
			i = i - n;
		}
	}


	private String getWord(ArrayList<String> list, ArrayList<Integer> occurences)
	{
		if(list.isEmpty())
			return "";
		
		String word = "";
		int total = 0;
		for(int i = 0; i < occurences.size(); i++)
			total += occurences.get(i);
		
		//System.out.println("total: " + total);
		
		String[] roller = new String[total];
		int index = 0;
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 0; j < occurences.get(i); j++)
			{
				roller[index] = list.get(i);
				index++;
			}
		}
		
		//for(int i = 0; i < roller.length; i++)
		//	System.out.print("  " + roller[i]);
		//System.out.println();
		
		index = (int) (Math.random() * total);
		//System.out.println(index + " -> index");
		word = roller[index];
		//System.out.println(word);
		
		String tmp[] = word.split(" ");
		word = tmp[tmp.length-1];
		//System.out.println("New word is: " + word);
		return word;
	}
	
	public int addFilePath(String path)
	{
		if(!filePaths.contains(path))
			filePaths.add(path);
		return filePaths.indexOf(path);
	}
	
	public void createTemporyTestText(String path, double errorRate)
	{
		String document = "", tmp = ""; String[] split_document;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			BufferedWriter bw = new BufferedWriter(new FileWriter("modifiedTestText"));
			System.out.println("reading test text");
			while((tmp = br.readLine()) != null)
			{
				document += tmp + " " + System.lineSeparator();
			}
				
			split_document = document.split(" ");
			
			System.out.println("document was split. . . checking dictionary");
			tmp = "";
			int wordIndex = 0;
			for(int i = 0; i < split_document.length; i++)
			{
				if(i %50 == 0)
				{
					System.out.println("completed: " + (((float)i/split_document.length) * 100) + " %");
				}
				//System.out.println("split word " + split_document[i]);
				if(!split_document[i].equals(" ") && !split_document[i].equals(System.lineSeparator()) && !split_document[i].equals("\t") && !split_document[i].equals(""))
				{
					if(StemmingClass.checkWord(split_document[i].toLowerCase()))
					{
						//System.out.println("word: " + split_document[i]);
						tmp += split_document[i] + " ";
						wordIndex++;
					}else
					{
						tmp += "[" + wordIndex + "] ";
						wordIndex++;
					}
				}else
				{
					if(split_document[i].equals(System.lineSeparator()))
						tmp += split_document[i]+ " ";
					else
						tmp += split_document[i] + " ";
				}
				
			}
			System.out.println("end");
			tmp = insertErrors(tmp, errorRate);
			
			
			bw.write(tmp);
			br.close();
			bw.close();
			
			//System.out.print(tmp);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	
	private String insertErrors(String s, double errorRate)
	{
		System.out.println("inserting errors");
		String tmp = "";
		
		for(int i = 0; i < s.length(); i++)
		{
			
			if(i%200==0)
			{
				System.out.println("error insert loop");
				System.out.println("completed: " + (((float)i/s.length()) * 100) + " %");
				
			
			}
			
			if(s.charAt(i) <= 'z' && s.charAt(i) >= 'a' || s.charAt(i) <= 'Z' && s.charAt(i) >= 'A')
			{
				if(i > 1 && i < s.length()-1)
				{	
					if((s.charAt(i-1) <= 'z' && s.charAt(i-1) >= 'a' || s.charAt(i-1) <= 'Z' && s.charAt(i-1) >= 'A') || (s.charAt(i+1) <= 'z' && s.charAt(i+1) >= 'a' || s.charAt(i+1) <= 'Z' && s.charAt(i+1) >= 'A'))
						tmp += changeCharacter(s.charAt(i), errorRate, true);
					else
						tmp += changeCharacter(s.charAt(i), errorRate, false);
				}else
					tmp += changeCharacter(s.charAt(i), errorRate, false);
			}else
				tmp += s.charAt(i);
			
		}
		return tmp;
	}
	
	private String changeCharacter(char original, double errorRate, boolean delete)
	{
		String newChar = original + "";
		double val = Math.random();
	
		if(val <= errorRate)
		{
			int letter = (int)(Math.random() * (double)27);
			
			if(letter >= 26 && delete)
				return "";
			else
			{	
				if(letter >= 26)
					letter = letter -1;
				newChar = (char)((int)(letter + 97)) + "";
				
			}
		}
		
		return newChar;
	}
	
	private String correctedText = "";
	private void appendWordToText(String word)
	{
		this.correctedText += word;
	}
	
	public String getCorrectedText()
	{
		return this.correctedText;
	}
	
	public void makeCorrectionToTestText(String path)
	{
		String document = "", tmp = ""; String[] split_document;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			BufferedWriter bw = new BufferedWriter(new FileWriter("sample"));
			System.out.println("reading test text");
			
			int lineNum = 0;
			while((tmp = br.readLine()) != null)
			{
				lineNum++;
				//System.out.println("reading line: " + lineNum);
				document += tmp + " ";
			}
			
            this.correctedText = "";
			
			
			document = document.replaceAll(System.lineSeparator(), "").replaceAll("\\.", "").replaceAll(",", "").replaceAll(";", "").replaceAll("\\?", "").replaceAll("\\!", "").replaceAll("\"", "").replaceAll(":", "");
			
			while(document.contains("  "))
			{
				document = document.replaceAll("  ", " ");
			}
			
			int i = 0;
			while(document.charAt(i) == ' ')
			{
				i++;
			}
			
			document = document.substring(i, document.length());
			
			split_document = document.split(" ");
			System.out.println("document length: " + split_document.length);
			String nPhrase = "";
			
			for(i = 0; i < n-1; i++)
			{
				this.appendWordToText(split_document[i] + " ");
			}
			
			for(i = 0; i < split_document.length; i++)
			{	
	
				if(i%200 == 0)
				{
					System.out.println("completed: " + (((float)i/split_document.length) * 100) + " %");
				}
				
				int length = 0; 
				if(n==0) 
					nPhrase += split_document[i] + " ";
				
				for(length = 0; length < n; i++)
				{
					nPhrase += split_document[i] + " ";
					length++;		
				}
				
				nPhrase = nPhrase.substring(0, nPhrase.length()-1);
				
				//System.out.println("nPhrase: " + nPhrase);
				String[] n_words = nPhrase.split(" ");
				
				i = i - n;

					if(StateMachine.getFSM().verifyWord(n_words[n_words.length-1].toLowerCase()))
					{
					
						this.appendWordToText(n_words[n_words.length-1] + " ");
					
					}else
					{
						if(n_words[n_words.length-1].charAt(n_words[n_words.length-1].length()-1) != ']' && n_words[n_words.length-1].charAt(0) != '[')
						{
				
							split_document[i + n - 1] = getCorrectWord(n_words);
							this.appendWordToText(split_document[i + n - 1] + " ");
						}
						else
						{
						
							this.appendWordToText(n_words[n_words.length-1] + " ");
						}

					}
				
				nPhrase = "";
			}
			bw.write(correctedText);
			br.close();
			bw.close();
		}catch(Exception e) {
		
			System.out.println("Exception");
			return;
		}
	}	
	
	public String getCorrectWord(String[] list)
	{
		ed = new EditDistance();
		String tmp = "";
		
		if(list[list.length-1].charAt(list[list.length-1].length()-1) == '.' || list[list.length-1].charAt(list[list.length-1].length()-1) == ',' || list[list.length-1].charAt(list[list.length-1].length()-1) == '!' || list[list.length-1].charAt(list[list.length-1].length()-1) == '?' || list[list.length-1].charAt(list[list.length-1].length()-1) == ';')
			tmp = list[list.length-1].substring(0, list[list.length-1].length()-1);
		else 
			tmp = list[list.length-1];
		
		for(int i = 0; i < dictionary.size(); i++)
		{
			if(tmp.charAt(tmp.length()-1) == ',' || tmp.charAt(tmp.length()-1) == ';' || tmp.charAt(tmp.length()-1) == '.' || tmp.charAt(tmp.length()-1) == '?' || tmp.charAt(tmp.length()-1) == '!')
			{	
				ed.getEditDistance(tmp.substring(0, tmp.length()-1).toLowerCase(), dictionary.get(i));
				tmp = tmp.substring(0, tmp.length()-1);
			}
			else
				ed.getEditDistance(tmp, dictionary.get(i));
		}
		//System.out.println(ed.getCandidates().toString());
		
		if(ed.getCandidates().size() <= 0)
			return list[list.length-1];
		
		if(ed.getCandidates().size() <= 1)
		{
			//System.out.println("returning with 1 candidate");
			return ed.getCandidates().get(0);
		}
		
		ArrayList<String> candidates = new ArrayList<String>();
		ArrayList<Integer> occurences = new ArrayList<Integer>();
		
		//test using a different method (ed dist gets to randomly choose multiple candidation if n == 0)
		if(n == 0)
		{
			//return list[list.length-1];
			for(int i = 0; i < ed.getCandidates().size(); i++)
			{
				occurences.add(1);
			}
			return this.getWord(ed.getCandidates(), occurences);
		}
			
		for(int j = 0; j < ed.getCandidates().size(); j++)
		{
			tmp = "";
			for(int i = 0; i < list.length-1; i++)
			{
				tmp += list[i] + " ";
			}
			tmp += ed.getCandidates().get(j);
			//System.out.println("word: " + tmp);
			//System.out.println("tmp: " + tmp);
			
			//System.out.println("word list size: " + this.getList(n).getWordList().size());
			int result = Collections.binarySearch(this.getList(n).getWordList(), tmp.toLowerCase());
			
			
			if(result >= 0)
			{
				//System.out.println("tmp:  " + tmp);
				//System.out.print("\n [" + j + "] \n");
				candidates.add(this.getList(this.n).getWordList().get(result));
				occurences.add(this.getList(this.n).getOccurences().get(result));
			}
			
		}
		
		//System.out.println(ed.getCandidates().toString());
		//System.out.println();
		String s = this.getWord(candidates, occurences);
		if(s.equals(""))
			return list[list.length-1];
		return s;
	}
	
	public String placeWord(String oldWord, String newWord)
	{
		
		int start = 0; int end = oldWord.length();
		char cs = oldWord.charAt(0);
		char ce = oldWord.charAt(oldWord.length()-1);
		int end2 = oldWord.length();
		
		//System.out.println(oldWord);
		if(cs == '"' || cs == '(')
			start++;
		
		if(ce == '"' || ce == ')')
		{	
			end--;
			ce = oldWord.charAt(oldWord.length()-2);
			//System.out.println(oldWord.charAt(oldWord.length()-2));
		}
			
		if(ce == '.' || ce == ',' || ce == '?' || ce == ',' || ce == '!' || ce == ':' || ce == ';')
			end--;
		
		//System.out.println(end);
		newWord = oldWord.substring(0, start) + newWord + oldWord.substring(end, end2);
		//System.out.println(newWord);
		return newWord;
	}
	
	public void writeCorrectedTextFile(String pathToOriginal, String outputPath)
	{
	
		System.out.println("write new text");
		BufferedReader br = null; BufferedWriter bw = null;
		String textToWrite = "";
		String[] correctedWords = this.correctedText.split(" ");
	
		try {
			bw = new BufferedWriter(new FileWriter(outputPath));
			br = new BufferedReader(new FileReader(pathToOriginal));

			int index = 0;
			String tmp = "";

			while((tmp = br.readLine()) != null)
			{
				String[] oldWords = tmp.split(" ");
				
				for(int i = 0; i < oldWords.length; i++)
				{	
					if(index < correctedWords.length)
					{	
					
						if(!oldWords[i].equals(System.lineSeparator()) && !oldWords[i].equals("") && !oldWords[i].equals(" "))
						{	
							char cList[] = {'.', '!',':','?'};
							boolean b = true;
							
							//System.out.println(oldWords[i] + " --> " + correctedWords[index]);
							for(int j = 0; j < cList.length; j++)
							{
								if(oldWords[i].equals("" + cList[j]) || oldWords[i].charAt(0) == cList[j] && oldWords[i].charAt(oldWords[i].length()-1) == cList[j])
								{
								
									textToWrite += oldWords[i] + " ";	
									b = false;
								}
							}
							
							if(b)
							{
								textToWrite += this.placeWord(oldWords[i], correctedWords[index]) + " ";
					
								index++;
							}
						}else
						{	
								textToWrite += oldWords[i] + " "; 
						}
					}
				}
				
				textToWrite += System.lineSeparator() + " ";
			}
			 
			System.out.println();
			System.out.println(correctedWords.length);

			bw.write(textToWrite);
			br.close(); bw.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}

	public int[] comparTwoDocuments(String path1, String path2)
	{
		BufferedReader br = null, br2 = null;
		int differences = 0; int similarities = 0;
		
		String[] words2, words1;
		try {
			br2 = new BufferedReader(new FileReader(path2));
			br = new BufferedReader(new FileReader(path1));
			
			String tmp = "", tmp2;
			String list1 = "";
			String list2 = "";

			while((tmp = br.readLine()) != null)
			{
				words1 = tmp.split(" ");
				
				for(int i = 0; i < words1.length; i++)
				{
					if((!words1[i].equals(" ") && !words1[i].equals("") && !words1[i].equals(System.lineSeparator())))
					{
						list1 += words1[i] + " ";
					}
				}
	
			}
			 
			while((tmp2 = br2.readLine()) != null)
			{
				words2 = tmp2.split(" ");
				
				for(int i = 0; i < words2.length; i++)
				{
					if((!words2[i].equals(" ") && !words2[i].equals("") && !words2[i].equals(System.lineSeparator())))
					{
						list2 += words2[i] + " ";
					}
				}
	
			}
			
			words1 = list1.split(" ");
			words2 = list2.split(" ");
			
			for(int i = 0; i < words1.length && i < words2.length; i++)
			{
				System.out.println(words1[i] + "   " + words2[i]);
				if(words1[i].equals(words2[i]))
				{
					similarities++;
				}else
				{
					if((words1[i].charAt(0) == '[' && words1[i].charAt(words1[i].length()-1) == ']') || (words2[i].charAt(0) == '[' && words2[i].charAt(words2[i].length()-1) == ']'))
					{
						similarities++;
					}else
					{
						differences++;
					}
				}
			}
			br.close(); br2.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new int[] {similarities, differences};
	}
}
