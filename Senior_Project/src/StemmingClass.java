import StateMachine.StateMachine;

public class StemmingClass {

	public static boolean checkWord(String w)
	{
		w = w.replaceAll(System.lineSeparator(), "").replaceAll("\\.", "").replaceAll(",", "").replaceAll(";", "").replaceAll("\\?", "").replaceAll("\\!", "").replaceAll("\"", "").replaceAll(":", "");
		w = w.replaceAll("\\(", "").replaceAll("\\)","");
		
		//try {
		if(StateMachine.getFSM().verifyWord(w))
			return true;
		//}catch(Exception e) {return false;}
		
		w = w.toLowerCase();
		
		try {
		if(!checkRoots(w).equals(""))
		{
			StateMachine.getFSM().addWord(w);
			StateMachine.getFSM().buildCharacterList();
			StateMachine.getFSM().buildState(w);
			return true;
		}}catch(Exception e)
		{
			return false;
		}
		
		return false;

	}
	
	
	private static String checkRoots(String w) throws Exception
	{
		String newWord = "";
		
		if(w.substring(w.length()-4, w.length()).equalsIgnoreCase("ness"))
		{
			newWord += w.substring(0, w.length()-5);
			
			if(w.charAt(w.length()-5) == 'i')
			{
				newWord += 'y';
				
				if(verify(newWord));
					return newWord;
			}else
			{
				newWord = w.substring(0, w.length()-4);
				if(verify(newWord))
					return newWord;
			}
		}
		
		
		if(w.substring(w.length()-4, w.length()).equalsIgnoreCase("less"))
		{
			newWord += w.substring(0, w.length()-5);
			
			if(w.charAt(w.length()-5) == 'i')
			{
				newWord += 'y';
				
				if(verify(newWord));
					return newWord;
			}else
			{
				newWord = w.substring(0, w.length()-4);
				if(verify(newWord))
					return newWord;
			} 
		}
		
		if(w.substring(w.length()-2, w.length()).equalsIgnoreCase("er"))
		{
			newWord += w.substring(0, w.length()-3);
			
			if(w.charAt(w.length()-3) == 'i')
			{
				newWord += 'y';
				
				if(verify(newWord));
					return newWord;
			}else
			{
				newWord = w.substring(0, w.length()-2);
				if(verify(newWord))
					return newWord;
			} 
		}
		
		if(w.substring(w.length()-1, w.length()).equalsIgnoreCase("s"))
		{
			newWord += w.substring(0, w.length()-1);
			
		
			if(verify(newWord));
				return newWord;
			
		}
		
		return "";
	}
	
	private static boolean verify(String w)
	{
		if(StateMachine.getFSM().verifyWord(w))
			return true;
		return false;
	}
	
}
