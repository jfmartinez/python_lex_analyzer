package icom4036.assignment1.pythonanalyzer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class is in charge of reading the file and calling the lexical analyzer.
 * 
 * @author Jose F. Martinez Rivera
 *
 */
public class PythonSourceFileReader {

	/**
	 * @param args args[0] filename
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File pythonSourceCode = new File("");
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter("/Users/Frankenheimer/Desktop/pyAnalyzerJava.txt");
		if(!(args.length == 0)){
			try
			{
				pythonSourceCode = new File(args[0]);
			}
			catch(Exception e)
			{
				System.out.println("File was not found");
			}
		}
		
		
			
		else{
		pythonSourceCode = new File(in.nextLine());
		}

		try {
			Scanner pyLAnalyzer = new Scanner(pythonSourceCode);
			StringBuffer pyCode = new StringBuffer("");

			while(pyLAnalyzer.hasNextLine())	

			{	
				pyCode.append(pyLAnalyzer.nextLine()+"\n");

			}
			long startTime = System.nanoTime();

			PythonLexicalAnalyzer LA = new PythonLexicalAnalyzer(pyCode.toString());

			long endTime = System.nanoTime();

			long duration = endTime - startTime;
			
			System.out.println("TIME TAKEN: " +duration/Math.pow(10,9)+ "s");
			System.out.println("TIME TAKEN: " +(duration/Math.pow(10,9))/60 + "min");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		


	}

}
