import java.util.ArrayList;

import EditDistance.EditDistance;
import StateMachine.StateMachine;

public class Algorithms {
	
	public String useEditDistance(String s)
	{
		EditDistance ed = new EditDistance();
		String words[] = s.split(" ");
		
		ArrayList<String> dic = StateMachine.getFSM().readDictionary();
		System.out.println(StateMachine.getFSM().readDictionary().size());
		for(int i =0; i < words.length; i++)
		{
			
			System.out.print(words[i]);
			
			boolean isWord = false;
			if(words[i].charAt(words[i].length()-1) == '.')
				isWord = StateMachine.getFSM().verifyWord(words[i].substring(0, words[i].length()-1));
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
						if(i >= 1)
							words[i] = this.nGramAnalysis(1);
					}
			}
			else
				System.out.println(": IS a word");
		}
		
		String corrected = "";
		for(int i = 0; i < words.length; i++)
		{
			corrected += words[i] + " ";
		}
		
		System.out.println(corrected);
		return corrected;
	}
	
	private String nGramAnalysis(int n)
	{
		
		System.out.println("Too many choices! Running N-Gram analysis. . . ");
		
		
		ArrayList<String> wordList = new ArrayList<String>();
		ArrayList<Integer> occurences= new ArrayList<Integer>();
		
		String text = "tree tea tree tree tea tree tree";
		
	
		int marker = 0, shiftmarker = 0;
		int start = 0;
		for(int i = 0; i < text.length(); i++)
		{
			if(text.charAt(i) == ' ')
			{
				if(shiftmarker == 0)
					shiftmarker = i;
				
				marker++;
				//System.out.println("marker" + shiftmarker);
				if(marker == n)
				{
					marker = 0;
					if(!wordList.contains(text.substring(start, i)))
					{
						wordList.add(text.substring(start, i));
						occurences.add(1);
					}
					else
					{
						int index = 0;
						index = wordList.indexOf(text.substring(start,i));
						occurences.set(index, occurences.get(index) + 1);
					}
					i = shiftmarker + 1;
					start = i;
					shiftmarker = 0;
				}

			}
		}
		
		if(!wordList.contains(text.substring(start)))
		{
			wordList.add(text.substring(start));
			occurences.add(1);
		}
		else
		{
			int index = 0;
			index = wordList.indexOf(text.substring(start));
			occurences.set(index, occurences.get(index) + 1);
		}

		
		System.out.println();
		System.out.println(wordList.toString());
		System.out.println(occurences.toString());
		
		return this.getWord(wordList, occurences);
		
	}
	
	private String getWord(ArrayList<String> list, ArrayList<Integer> occurences)
	{
		String word = "";
		int total = 0;
		for(int i = 0; i < occurences.size(); i++)
			total += occurences.get(i);
		
		System.out.println("total: " + total);
		
		int tmp[] = new int[100];
		int tmp_percent[] = new int [list.size()];
		for(int i = 0; i < list.size(); i++)
		{
			double test = (double)occurences.get(i)/(double)total;
			System.out.println("value: " + test);
			
			int count = (int)Math.round((100 * test));
			System.out.println("count: " + count);
			tmp_percent[i] = count;
			
		}
		
		for(int i = 0; i < tmp.length; i++)
		{
			
		}
		return word;
	}
}
