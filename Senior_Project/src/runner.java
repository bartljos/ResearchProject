import java.util.Scanner;

import StateMachine.StateMachine;

public class runner {
	public static void main(String[] args)
	{
		StateMachine fsm = new StateMachine();
		System.out.println("reading dictionary");
		fsm.readDictionary();
		System.out.println("building state machine");
		fsm.buildFSM();
		System.out.println();
		Scanner in = null;
		/// will be adding junit testing here instead
		while(true)
		{
			System.out.println("Enter word to verify: ");
			in = new Scanner(System.in);
			boolean valid = fsm.verifyWord(in.nextLine());
			
			if(valid)
				System.out.println("IS A WORD");
			else
			{
				System.out.println();
				System.out.println("NOT A WORD");
			}
		
			
			System.out.println("EXIT? (enter 'exit' to quit)");
			String running = in.nextLine();
			if(running.equals("exit"))
				break;
			
		}
		in.close();
	}
}
