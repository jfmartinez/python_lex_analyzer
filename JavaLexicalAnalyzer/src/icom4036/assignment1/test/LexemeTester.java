package icom4036.assignment1.test;
import java.io.IOException;
import java.util.Scanner;
import icom4036.assignment1.nodestack.NodeStack;
import icom4036.assignment1.pythonanalyzer.*;
public class LexemeTester {

	/**
	 * Tests lexemes every line input.
	 * @Jose F. Martinez Rivera
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		PythonLexicalAnalyzer LA;
		String input;
		while(true)
		{	
			input = sc.nextLine();
			System.out.println(input);
			try {
				long startTime = System.nanoTime();
				
				LA = new PythonLexicalAnalyzer(input);
				long endTime = System.nanoTime();


				long duration = endTime - startTime;
				System.out.println("TIME TAKEN: " +duration+ "ns");
				System.out.println("TIME TAKEN: " +duration/Math.pow(10,9)+ "s");
				System.out.println("TIME TAKEN: " +(duration/Math.pow(10,9))/60 + "min");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}

}
