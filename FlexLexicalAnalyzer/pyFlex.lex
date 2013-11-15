/*
* Author: Jose F. Martinez Rivera
* Python Lexical Analyzer in Flex
* ICOM4036 -Programming Languages
* Prof: Dr. Wilson Rivera
*/
%{
         #include <time.h>
%}
KEYWORD 	(and)|(as)|(assert)|(break)|(class)|(continue)|(def)|(del)|(elif)|(else)|(except)|(exec)|(finally)|(for)|(from)|(global)|(if)|(import)|(in)|(is)|(lambda)|(not)|(or)|(pass)|(print)|(raise)|(return)|(try)|(while)|(with)|(yield)


DELIMITER 	"{"|"}"|"("|")"|"["|"]"|"@"|"+="|":"|"."|"`"|"="|";"|"+="|"-="|"*="|"/="|"//="|"%="|"&="|"|="|"^="|">>="|"<<="|"**="


IDENTIFIER	([a-zA-Z]|_)([a-zA-Z]|[0-9]|_)*


OPERATOR	"+"|"-"|"**"|"*"|"/"|"//"|"%"|"<<"|">>"|"&"|"|"|"^"|"~"|"<"|">"|"<="|">="|"=="|"|="|"<>"


/* INTEGER LITERAL*/
digit		[0-9]
hexdigit	{digit}|[a-f]|[A-F]
hexinteger	"0"("x"|"X"){hexdigit}+

bindigit	"0"|"1"
bininteger	"0"("b"|"B"){bindigit}+

octdigit	[0-7]
octinteger	"0"("o"|O"){octdigit}+

nonzerodigit	[1-9]
decimalinteger	({nonzerodigit})({digit}*)|"0"

INTEGER	{decimalinteger}|{octdigit}|{hexinteger}|{bininteger}
LONGINTEGER {INTEGER}("l"|"L")

/**********************************************/

/* Floating point Literals & Imaginary Literals */

exponent ("e"|"E")("+"|"-")?{digit}+
fraction	("."{digit}+)
intpart		{digit}+
pointfloat		{intpart}?{fraction}|{intpart}"."
exponentfloat	({intpart}|{pointfloat}){exponent}
floatnumber		{pointfloat}|{exponentfloat}

imagnumber		({floatnumber}|{intpart})("j"|"J")
/*********************************************/



/* String Literals */

escapeseq			[\\].

shortstringchar		[^(\\|\"|\')]
shortstringitem		{shortstringchar}|{escapeseq}
shortstring 		\"{shortstringitem}*\"|(\'){shortstringitem}*(\')

longstringchar		[^(\\)]
longstringitem		{longstringchar}|{escapeseq}
longstring 			(\"){3}{longstringitem}*(\"){3}



stringprefix		"r"|"u"|"ur"|"R"|"U"|"UR"|"Ur"|"uR"|"b"|"B"|"br"|"Br"|"bR"|"BR"
stringliteral 		({shortstring}|{longstring})


/* INDENT */

indent				("        ")|(\t)


comment				("#".*)
%%

{comment}			printf(""); 

{indent}			printf("INDENT\n");
{shortstring}		printf("STRING_LITERAL:\t %s\n", yytext);
{floatnumber}		printf("FLOATNUMBER:\t %s\n", yytext);
{imagnumber}		printf("IMAG_NUMBER:\t %s\n", yytext);
{DELIMITER}			printf("DELIMETER:\t %s\n", yytext);
{OPERATOR}			printf("OPERATOR:\t %s\n", yytext);
{KEYWORD}			printf("KEYWORD:\t %s\n", yytext);
{IDENTIFIER}		printf("IDENTIFIER:\t %s\n", yytext);
{INTEGER}			printf("INTEGER:\t %s\n", yytext);
{LONGINTEGER}		printf("LONG_INTEGER:\t %s\n", yytext);






     
%%
main()
{

  	printf("TOKENS:\t\tLEXEMES:\n");


  	yyin = fopen("/Users/Frankenheimer/Desktop/200000Lexemes.txt", "r");
  	clock_t begin, end;
	double time_spent;

	begin = clock();
	yylex();
	end = clock();
	time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
	printf("Time spent:\t %lf\n", time_spent);


}
