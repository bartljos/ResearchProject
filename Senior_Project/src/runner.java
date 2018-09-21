import java.util.Scanner;

import StateMachine.StateMachine;

/**
 * @author Joshua Bartle
 *
 * This is the class containing the main method. This will be used for running the entire experiment
 * after everything is verified to word using JUnit Tests
 */
public class runner {
	
	/**
	 * main method 
	 * This will be used with the final project but is currently not used while
	 * code is developed using JUnit testing
	 */
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
