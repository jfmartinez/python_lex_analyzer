package icom4036.assignment1.pythonanalyzer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import icom4036.assignment1.nodestack.NodeStack;


/**
 * Python lexical analyzer, it prints out Tokens with their respective keywords.
 * 
 * 
 * @author Jose F. Martinez Rivera
 * 
 *
 */
public class PythonLexicalAnalyzer {


	/**
	 * Python Keywords lexical definition.
	 */
	private final String pythonKeywords = "(and)|(as)|(assert)|(break)|(class)|(continue)|(def)|(del)|(elif)|(else)|(except)|(exec)" +
			"|(finally)|(for)|(from)|(global)|(if)|(import)|(in)|(is)|(lambda)|(not)|(or)|(pass)|(print)|(raise)|(return)|(try)|" +
			"(while)|(with)|(yield)";

	/**
	 * Python Delimiters lexical definition.
	 */
	private final String pythonDelimiters = "[\\{]|[\\}]|[(]|[)]|[\\]]|[\\[]|[@]|[,]|[:]|[.]|"+
			"(\\`)|(=)|(;)|(\\+=)|(-=)|(\\*=)|(/=)|(//=)|(%=)|(&=)|(!=)|(\\^=)|(>>=)|(<<=)|(\\*\\*=)";

	/**
	 * Python Identifier lexical definition.
	 */
	private final String pythonIdentifier = "([a-zA-Z]|_)([a-zA-Z]|[0-9]|_)*";

	/**
	 * Python, Floating point literals lexical definition.
	 */
	private final static String exponent = "(e|E)[\\+|-]?[0-9]+";
	private final static String fraction = "\\.[0-9]+";
	private final static String intpart = "[0-9]+";
	private final static String pointfloat = "["+intpart+"]?" +fraction +"|("+ intpart +")\\.";
	private final static String exponentfloat = "("+intpart +"|" + pointfloat +")" + exponent;
	private final static String floatnumber = pointfloat + "|"+ exponentfloat;

	/**
	 * Imaginary literal lexical definition.
	 */
	private final static String imagnumber ="("+floatnumber+"|"+ intpart+")(j|J)";


	/**
	 * Integer literal lexical definition.
	 */
	private final static String hexdigit = "0-9|a-f|A-F";
	private final static String bindigit = "0|1";
	private final static String octdigit = "0-7";
	private final static String nonzerodigit = "1-9";

	private final static String bininteger = "0(b|B)["+bindigit+"]+";
	private final static String hexinteger = "0(x|X)["+hexdigit+"]+";
	private final static String octinteger = "0(o|O)["+octdigit+"]+";
	private final static String decimalinteger = "["+nonzerodigit +"][0-9]*|0";

	private final static String integer = decimalinteger +"|"+octinteger+"|" +hexinteger+"|"+bininteger;
	private final static String longinteger = "("+integer+")(l|L)";

	/**
	 * Operators lexical definition.
	 */
	private final String pythonOperators = "[+]|[-]|(\\*\\*)|(\\*)|[/]|(//)|[%]|(<<)|(>>)|[&]|[\\|]|[\\^]|[~]|(<)|(>)|" +
			"(<=)|(>=)|(==)|(\\|=)|(<>)";

	/**
	 * Header encoding lexical definition.
	 */
	private final String pythonEncoding = "coding[=:]\\s*([-\\w.]+)";

	/**
	 * String literal lexical definition.
	 */
	private final static String stringprefix = "r|u|ur|R|U|UR|Ur|uR|b|B|br|Br|bR|BR";
	private final static String escapeseq = "(\\\\.)";
	private final static String longstringchar = "[^\\\\]";
	private final static String shortstringchar = "[^\\\\\"]";

	private final static String shortstringitem = shortstringchar + "|" + escapeseq ;
	private final static String longstringitem = longstringchar + "|" + escapeseq;

	private final static String shortstring = "\'[" + shortstringitem + "]*\'"+"|"+"\"[" + shortstringitem + "]*\"";
	private final static String longstring = "\'\'\'[" + longstringitem + "]* \'\'\'" +"|"+"\"\"\"[" + longstringitem + "]* \"\"\"";

	private final static String stringliteral = "["+stringprefix+ "]?("+shortstring+"|"+longstring+")";


	/**
	 * Source code scanner.
	 */
	private Scanner pythonScanner = new Scanner(System.in);

	/**
	 * Physical line scanner.
	 */
	private Scanner pythonTokenScanner;

	/**
	 * Python code flags.
	 */
	//Checks if an encoding exists.
	private boolean encodingFlag = false;

	//Implicit line joining.
	private boolean implicitJoining = false;

	//Logical Line flag.
	private boolean newLineFlag = false;

	//Flag for characters found
	private boolean characterFlag = false;

	//Flag for literal strings
	private boolean literalFlag = false;
	
	private boolean indentationFlag = true;

	/**
	 * Represents python's identations
	 */
	private NodeStack<Integer> indentationStack;
	
	private PrintWriter out;
	/**
	 * Python source code to be analyzed.
	 */
	private String sourceCode;
	
	
	public PythonLexicalAnalyzer(String sourceCode) throws IOException
	{
		indentationStack = new NodeStack<Integer>();
		out = new PrintWriter("/Users/Frankenheimer/Desktop/pyAnalyzerJava.txt");
		indentationStack.push(0);
		this.sourceCode = sourceCode;
		this.replaceTabs();
		this.readTokens();
		out.close();
	}

	/**
	 * Main function of the Lexical Analyzer, reads the source code and prints out the
	 * python lexemes found.
	 * @param sourceCode Python source file
	 * @throws IOException 
	 */
	public void readTokens() throws IOException
	{
		System.out.println("TOKENS:\t\tLEXEME:");
		out.println("TOKENS:\t\tLEXEME:");
		pythonScanner = new Scanner(sourceCode);
		while(pythonScanner.hasNextLine())
		{
			pythonTokenScanner = new Scanner(pythonScanner.nextLine());
			pythonTokenScanner.useDelimiter("");



			characterFlag = false;
			literalFlag = false;
			indentationFlag = true;

			String pyChar = "";



			while(pythonTokenScanner.hasNext())
			{	
				if(!pyChar.isEmpty())
				{
					newLineFlag = true;
				}

				if(indentationFlag)
				{	
					String next = "";
					int counter = 1;

					while(pythonTokenScanner.hasNext())
					{	next = pythonTokenScanner.next();
					if(next.equals(" "))
					{
						counter++;
					}


					else{
						pyChar = next;
						characterFlag = true;
						break;
					}



					}
					int level = (counter-1)/4;


					if(level  > indentationStack.top())
					{
						System.out.println("INDENT\t\t");
						out.println("INDENT\t\t");
						indentationStack.push(level);
					}

					else if(level < indentationStack.top())
					{
						System.out.println("DEDENT\t\t");
						out.println("DEDENT\t\t");
						indentationStack.pop();
					}
					indentationFlag = false;



				}
				if(pyChar.matches("#"))
				{			
					if(pythonScanner.hasNextLine())
					{
						pythonScanner.nextLine();
						newLineFlag = true;
						break;
					}
					else
						return;
				}


				if(!characterFlag)
				{
					pyChar = pythonTokenScanner.next();

				}
				else
				{
					characterFlag = false;
				}

				//Unconditional error characters
				if(pyChar.matches("[$?]"))
				{
					//throw new IOException("Bad Syntax:" + pyChar);
					System.out.println("ERROR_UNKNOWNKEY\t" + pyChar);
					out.println("ERROR_UNKNOWNKEY\t" + pyChar);
				}




				//Possible Identifier/Keyword
				if(pyChar.matches("[A-Za-z|_]"))
				{
					String nextChar;
					String candidateLexeme = pyChar;
					while(pythonTokenScanner.hasNext())
					{	
						nextChar = pythonTokenScanner.next();

						if(nextChar.matches("[A-Za-z|0-9|_]"))
						{
							candidateLexeme += nextChar;
						}
						else
						{
							pyChar = nextChar;
							characterFlag = true;
							break;
						}
					}


					if(candidateLexeme.matches(pythonKeywords))
					{
						System.out.println("KEYWORD\t\t" + candidateLexeme);
						out.println("KEYWORD\t\t" + candidateLexeme);
					}

					else if(candidateLexeme.matches(pythonIdentifier))
					{
						System.out.println("IDENTIFIER\t" + candidateLexeme);
						out.println("IDENTIFIER\t" + candidateLexeme);
					}

				}

				//Possible number literal
				if(pyChar.matches("[0-9]"))
				{	String nextChar;
				String candidateLexeme = pyChar;
				while(pythonTokenScanner.hasNext())
				{	
					nextChar = pythonTokenScanner.next();
					if(nextChar.matches("[0-9]|[x|X|o|O|b|B|j|J|e|E|l|L|[\\.?]]|[\\+|-]?"))
					{
						candidateLexeme += nextChar;
					}
					else
					{	
						pyChar = nextChar;
						characterFlag = true;

						break;
					}
				}		
				if(candidateLexeme.matches(integer))
				{
					System.out.println("INTEGER\t\t" + candidateLexeme);
					out.println("INTEGER\t\t" + candidateLexeme);
				}

				else if(candidateLexeme.matches(longinteger))
				{
					System.out.println("LONGINTEGER\t" + candidateLexeme);
					out.println("LONGINTEGER\t" + candidateLexeme);
				}

				else if(candidateLexeme.matches(imagnumber))
				{
					System.out.println("IMAGNUMBER\t" + candidateLexeme);
					out.println("IMAGNUMBER\t" + candidateLexeme);
				}

				else if(candidateLexeme.matches(floatnumber))
				{
					System.out.println("FLOATNUMBER\t" + candidateLexeme);
					out.println("FLOATNUMBER\t" + candidateLexeme);
				}


				}

				if(pyChar.matches("(\')|(\")"))
				{	
					if(!literalFlag){
						pyChar = getStringLiteral(pyChar);
						characterFlag = true;
						literalFlag= true;
					}


					else
					{
						literalFlag =false;

					}

				}


				if(pyChar.matches("["+pythonDelimiters +"]|[" + pythonOperators+"]"))
				{	
					String nextChar;
					String candidateLexeme = pyChar;
					while(pythonTokenScanner.hasNext())
					{	
						nextChar = pythonTokenScanner.next();
						if(!nextChar.matches("[A-Za-z|0-9|_|\'|\"]")){
							candidateLexeme += nextChar;
						}

						else
						{	
							pyChar = nextChar;
							characterFlag = true;
							break;
						}
					}

					if(candidateLexeme.matches(pythonDelimiters))
					{	
						if(candidateLexeme.matches("\\{|\\(|\\["))
						{
							implicitJoining = false;
						}

						if(candidateLexeme.matches("(\\}|\\)|\\])"))
						{	
							newLineFlag = true;
							implicitJoining = true;
						}
						System.out.println("DELIMITER\t" + candidateLexeme);
						out.println("DELIMITER\t" + candidateLexeme);
					}

					else if(candidateLexeme.matches(pythonOperators))
					{
						System.out.println("OPERATOR\t" + candidateLexeme);
						out.println("OPERATOR\t" + candidateLexeme);
					}


					else
					{	
						String candidate;
						for(int i = 0; i < candidateLexeme.length(); i ++)
						{	candidate = candidateLexeme.substring(i, i +1);


						if(candidate.matches(pythonDelimiters))
						{
							if(candidate.matches("\\{|\\(|\\["))
							{
								implicitJoining = false;
							}

							if(candidate.matches("(\\}|\\)|\\])"))
							{	
								newLineFlag = true;
								implicitJoining = true;
							}
							System.out.println("DELIMITER\t" + candidate);
							out.println("DELIMITER\t" + candidate);

						}
						if(candidate.matches(pythonOperators))
						{
							System.out.println("OPERATOR\t" + candidate);
							out.println("OPERATOR\t" + candidate);

						}


						}
					}


				}
				if(pyChar.matches("\\\\"))
				{
					implicitJoining = false;

				}
				else{
					implicitJoining = true;
				}
			}
			if(newLineFlag && implicitJoining){

				System.out.println("NEWLINE\t\t\\n");
				out.println("NEWLINE\t\t\\n");
				newLineFlag = false;
				implicitJoining = true;
			}
		}
	}

	public String getStringLiteral(String pyChar)
	{	String stringLiteral = pyChar+"";
	String next;

	while(pythonTokenScanner.hasNext()){

		next = pythonTokenScanner.next();
		stringLiteral += next;
		if(stringLiteral.matches("(\'[^\'])|(\"[^\"])"))
		{
			return getShortString(stringLiteral);

		}

		else if(stringLiteral.matches("(\'\'\')|(\"\"\")"))
		{
			return getLongString(stringLiteral);

		}

	}
	return "";


	}

	public void replaceTabs()
	{
		this.sourceCode = sourceCode.replaceAll("\t","    ");
	}

	public String getShortString(String literal)
	{
		String next;
		String stringLiteral = literal +"";
		while(pythonTokenScanner.hasNext())
		{	
			next = pythonTokenScanner.next();



			if(next.matches("(\')|(\")"))
			{
				
				System.out.println("STRING_LITERAL\t"+stringLiteral +next);
				out.println("STRING_LITERAL\t"+stringLiteral +next);
				return next;

			}


			else
				stringLiteral += next;
		}

		return "";

	}

	public String getLongString(String literal)
	{						

		String next;
		String stringLiteral = literal +"";

		while(pythonScanner.hasNextLine()){

			while(pythonTokenScanner.hasNext())
			{	
				next = pythonTokenScanner.next();



				if(next.matches("\'|\""))
				{	
					while(pythonTokenScanner.hasNext())
					{
						next += pythonTokenScanner.next();
						if(next.matches("\'\'\'|\"\"\""))
						{
							System.out.println("STRING_LITERAL\t"+stringLiteral +next);
							out.println("STRING_LITERAL\t"+stringLiteral +next);
							return "";

						}
					}

				}


				else
					stringLiteral += next;
			}
			pythonTokenScanner = new Scanner(pythonScanner.nextLine());
			pythonTokenScanner.useDelimiter("");
		}


		return "";
	}
}
