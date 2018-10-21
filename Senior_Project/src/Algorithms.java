import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import EditDistance.EditDistance;
import StateMachine.StateMachine;
import sun.awt.FwDispatcher;

public class Algorithms {
	
	EditDistance ed = null;
	private String text = "";
	private int n = -1; private int splitPoint = 0;
	private ArrayList<String> filePaths = new ArrayList<String>();
	private ArrayList<SerializableList> lists = new ArrayList<SerializableList>();
	
	public String[] runFiles()
	{
		String[] finalSet = {" ", " "};
		String[] tmp = {" ", " "};
		for(int i = 0; i < filePaths.size(); i++)
		{
			System.out.println("now working on: " + filePaths.get(i));
			System.out.println("parsing file");
			tmp = this.setFileAsSource(filePaths.get(i));
			finalSet[0] += tmp[0] + '\n';
			finalSet[1] += tmp[1] +'\n';
			
			
			this.setText(tmp[0]);
			System.out.println("creating n gram");
			this.createNGram();
		}
		return finalSet;
	}	
	
	public void createList(int n)
	{
		if(lists.size() <= n)
			for(int i  = 0; i < n; i++)
				lists.add(new SerializableList());
		else
			lists.set(n-1, new SerializableList());
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
					s += " " + tmp + "";
				else
					s2 += " " + tmp +"";
			}
			fr.close();
			
			System.out.println("fix s");
			s = s.replaceAll("\\.", "").replaceAll("\\?", "").replaceAll("!", "").replaceAll("\\;", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("[\"'\u2018\u2019\u201c\u201d]", "").replaceAll(",", "").replaceAll("\\*", "");
			
			System.out.println("fix s2");
			s2 = s2.replaceAll("\\.", "").replaceAll("\\?", "").replaceAll("!", "").replaceAll("\\;", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("[\"'\u2018\u2019\u201c\u201d]", "").replaceAll(",", "").replaceAll("\\*", "");
			
			
			
			return new String[]{s, s2};
			
		}catch(Exception e) {System.out.println("error reading file"); e.printStackTrace();}
		
		return null;
	}
	
	public void setText(String text)
	{
		this.text = text;
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
		/*try {
			FileInputStream f = new FileInputStream("n=" + n);
			ObjectInputStream o = new ObjectInputStream(f);
			sl = (SerializableList)o.readObject();
			o.close();
			return sl;
		}catch(Exception e) {}*/
		
	
		int i = 0;
		while(text.charAt(i) == ' ')
			i++;
		
		
		int marker = 0, shiftmarker = 0;
		int start = i;
		for(; i < text.length(); i++)
		{
			
			
			if(text.charAt(i) == ' ')
			{
				
				while(i < text.length() && text.charAt(i) == ' ')
					i++;
				i--;
				
				if(shiftmarker == 0)
 					shiftmarker = i;
				
				marker++;
			
				if(marker == n)
				{
					marker = 0;
					
					String tmp = text.substring(start, i).replace("\n", "");
					System.out.println(" ---> " + tmp);
					lists.get(n-1).addWord(tmp);
						
					i = shiftmarker + 1;
					start = i;
					shiftmarker = 0;
				}

			}
		}
		
		try {
			FileOutputStream f = new FileOutputStream("n=" + n);
			ObjectOutputStream o = new ObjectOutputStream(f);
			//o.writeObject(sl);
			o.close();
		}catch(Exception e) {}
		
		//sl.printAll();
	}


	private String getWord(ArrayList<String> list, ArrayList<Integer> occurences)
	{
		if(list.isEmpty())
			return "";
		
		String word = "";
		int total = 0;
		for(int i = 0; i < occurences.size(); i++)
			total += occurences.get(i);
		
		System.out.println("total: " + total);
		
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
		
		for(int i = 0; i < roller.length; i++)
			System.out.print("  " + roller[i]);
		System.out.println();
		
		index = (int) (Math.random() * total);
		System.out.println(index + " -> index");
		word = roller[index];
		System.out.println(word);
		
		String tmp[] = word.split(" ");
		word = tmp[tmp.length-1];
		return word;
	}
	
	public int addFilePath(String path)
	{
		if(!filePaths.contains(path))
			filePaths.add(path);
		return filePaths.indexOf(path);
	}
}
