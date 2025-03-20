/*
 * * DO NOT MAKE CHANGES TO THIS FILE 
 * Making changes to this file may not properly allow you to check your format for the autograder.
 * 
 * This class checks if your format is as specified in the lab instructions.
 * You should run this to ensure your work is gradable.
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CheckFormat_A6 {
	
	public static void main(String[] args)
	{
		System.out.println("The output from your function with the argument \"polls/dempres_20190718_3.csv\" is used for checking\n\n");
		
		
		String[] argForMain = new String[]{"polls/dempres_20190718_3.csv"};
		String studentAns = getOutput(argForMain);
		String correctAns = "Tree:\n"+
							// pre order
							"Pre:\tJoseph R. Biden Jr.:27.0 Michael F. Bennet:0.0 Bernard Sanders:20.0 Kamala D. Harris:12.0 Pete Buttigieg:7.0 Cory A. Booker:1.0 Steve Bullock:0.0 Tulsi Gabbard:2.0 John K. Delaney:1.0 Julián Castro:1.0 Kirsten E. Gillibrand:1.0 Mike Gravel:0.0 Beto O'Rourke:2.0 Amy Klobuchar:1.0 John Hickenlooper:1.0 Jay Robert Inslee:0.0 Seth Moulton:0.0 Wayne Messam:0.0 Tim Ryan:0.0 Elizabeth Warren:18.0 Tom Steyer:1.0 Joe Sestak:0.0 Andrew Yang:1.0 Marianne Williamson:0.0 Bill de Blasio:0.0\n" +
							// In order
							"In:\tMichael F. Bennet:0.0 Joseph R. Biden Jr.:27.0 Cory A. Booker:1.0 Steve Bullock:0.0 Pete Buttigieg:7.0 Julián Castro:1.0 John K. Delaney:1.0 Tulsi Gabbard:2.0 Kirsten E. Gillibrand:1.0 Mike Gravel:0.0 Kamala D. Harris:12.0 John Hickenlooper:1.0 Jay Robert Inslee:0.0 Amy Klobuchar:1.0 Wayne Messam:0.0 Seth Moulton:0.0 Beto O'Rourke:2.0 Tim Ryan:0.0 Bernard Sanders:20.0 Joe Sestak:0.0 Tom Steyer:1.0 Elizabeth Warren:18.0 Marianne Williamson:0.0 Andrew Yang:1.0 Bill de Blasio:0.0\n" +		
							// post order:
							"Post:\tMichael F. Bennet:0.0 Steve Bullock:0.0 Cory A. Booker:1.0 Julián Castro:1.0 John K. Delaney:1.0 Mike Gravel:0.0 Kirsten E. Gillibrand:1.0 Tulsi Gabbard:2.0 Pete Buttigieg:7.0 Jay Robert Inslee:0.0 John Hickenlooper:1.0 Wayne Messam:0.0 Seth Moulton:0.0 Amy Klobuchar:1.0 Tim Ryan:0.0 Beto O'Rourke:2.0 Kamala D. Harris:12.0 Joe Sestak:0.0 Tom Steyer:1.0 Marianne Williamson:0.0 Bill de Blasio:0.0 Andrew Yang:1.0 Elizabeth Warren:18.0 Bernard Sanders:20.0 Joseph R. Biden Jr.:27.0";
		
		System.out.println("********************Your output start********************\n" + studentAns + "\n********************Your output end********************\n\n");
		System.out.println("\n********************Correct output start********************\n" + correctAns + "\n********************Correct output end********************\n");
		
		if(!(studentAns.length() > 0))
		{
			System.out.println("\nIt seems that your output is empty... Please make sure you are printing something and try again.");
			return;
		}
		
		System.out.println("\n\n============================format checker messages start============================");
		if(studentAns.indexOf(correctAns) != -1)
			System.out.println("The format seems to be correct!");
		
		
		else
		{
			System.out.println("\n------------Checking the presence of \"Tree:\"------------");
			if(studentAns.indexOf("Tree") != -1)
			{
				if(studentAns.indexOf("Tree:") == -1)
					System.out.println("You are either:\n\t- missing a colon after the word \"Tree\" or\n\t- have \"Tree\" printing multiple times before your output.\nPlease make the appropriate changes and try again.\n");
				else // "Tree:" has been correctly found!
					System.out.println("\"Tree:\" has been found!");
			}
			else
			{
				System.out.print("In the output, ");
				printlnErr("could not find \"Tree:\"");
				System.out.println("Please make the appropriate changes and try again.");
			}
			
			System.out.println("\n\n------------Checking for the presence of prefixes------------");
			if(!checkForPrefixes(studentAns))
			{
				printlnErr("The format of candidate info was not unable to be checked.");
				System.out.println("\nPlease try again after making appropriate changes as indicated above.");
			}
			else
			{
				System.out.println("\n\n------------Checking the order of the prefixes------------");
				if(!checkOrder(studentAns))
				{
					System.out.println("Please make appropriate changes and try running again.");
					return;
				}
				
				System.out.println("\n\n------------Checking the number of lines in your output------------");
				studentAns = studentAns.substring(studentAns.indexOf("Pre:")); // ignore Tree
				String[] stuArr = getStudentLines(studentAns);
				String[] corArr = correctAns.split("\n");
				
				if(stuArr.length +1 != corArr.length)
				{
					System.out.print("Your output seems to have printed ");
					if(stuArr.length +1 < corArr.length)
						printErr("fewer");
					else
						printErr("more");
					System.out.println(" lines than it should have.\n");
				}
				else
					System.out.println("The number of lines seem to be correct.");
				int stuLine = 0;
				int corLine = 1; // ignore "Tree:"
				
				System.out.println("\n\n------------Checking the tree traversal outputs------------");
				while((stuLine != -1 && stuLine < stuArr.length) || corLine < corArr.length)
				{
					String prefixWanted = "";
					if(corLine == 1)
						prefixWanted = "Pre";
					else if(corLine == 2)
						prefixWanted = "In";
					else if(corLine == 3)
						prefixWanted = "Post";
//					else
//						prefixWanted = "... output should have ended";
					if(corLine == 1 || corLine == 2 || corLine == 3)
						System.out.println("Trying to check the line containing \""+ prefixWanted +":\"");
					else
						System.out.println("Output should have ended... (It's ok to have miscellaneous output after your tree completely prints out)\n\tYour output has an extra line as follows:");
										
					if((stuLine != -1 && stuLine < stuArr.length) && corLine < corArr.length)
					{
//						System.out.println("corLine: "+ corArr[corLine] + "\nstuLine: " + stuArr[stuLine] + "\nPrefix: " + prefixWanted);
						checkLineFormat(corArr[corLine], stuArr[stuLine], prefixWanted);
						corLine++;
//						System.out.print("StuLine is " + stuArr[stuLine]);
						stuLine = getNextNonEmpty(stuArr, stuLine);
//						System.out.println("\tNew stuLine after getNextNonEmpty is " + stuArr[stuLine]);
					}
					else if(corLine < corArr.length)
					{
						printlnErr("Your output seems to have ended without having following lines:");
						System.out.println("\n\t" + corArr[corLine] + "\n\n");
						corLine++;
					}
					else if(stuLine < stuArr.length)
					{
						System.out.println("\t" + stuArr[stuLine] + "\n");
						stuLine = getNextNonEmpty(stuArr, stuLine);
					}
				}
				System.out.println("Please make appropriate corrections and try again.");
			}
		}
		System.out.println("============================format checker messages end============================");
	}
	
	
	
	
	
	
	
	
	// helper methods
	public static void checkLineFormat(String corLine, String stuLine, String prefixToCheck)
	{
		stuLine = removeRN(stuLine);
		System.out.println("\tThe line should have been:\t\"" + corLine + "\"");
		System.out.println("\tYour output has printed:\t\"" + stuLine + "\"\n");
		
		if(corLine.length() != stuLine.length())
		{
			System.out.print("\t\tYour output printed ");
			if(stuLine.length() > corLine.length())
				printErr("more");
			else
				printErr("less");
			System.out.println(" characters than it should have.\n\t\t\tWhat the length should be: " + corLine.length() + "\n\t\t\tThe length of your line:  " + stuLine.length());
				
		}
		
		System.out.println("");
		
		if(stuLine.equals(corLine))
			System.out.println("\tThe traversal output seems to be correct.");
		
		else
		{
			//if(stuLine.indexOf(")") == -1)
			//	printlnErr("\t\tPlease make sure you have a closing parenthesis \")\"");
			
//			System.out.println("stuLine:" + stuLine);
			
			if(stuLine.indexOf(prefixToCheck) == -1)
				System.out.println("\t\tCould not find \"" + prefixToCheck + "\" in the line.");
			else if(stuLine.substring(stuLine.indexOf(prefixToCheck)).length() < prefixToCheck.length()+1)
				System.out.println("\t\tThe number of characters after \"" + prefixToCheck + "\" seems too few.");
			else if(stuLine.substring(stuLine.indexOf(prefixToCheck)+prefixToCheck.length()+1).indexOf(":") == -1)
			{
				System.out.print("\t\tPlease ");
				printErr("make sure you have a colon");
				System.out.println(" being printed between the name and the number");
			}
			else
			{
				if(stuLine.indexOf(": ")!=-1 || stuLine.indexOf(" :")!=-1)
				{
					System.out.print("\t\tPlease ");
					printlnErr("make sure you do not have a space printing before or after the colon.");
				}
			}
			//String[] corPoll = corLine.split(", ");
			//String[] stuPoll = null;
			/*if(stuLine.indexOf(", ") == -1)
			{
				System.out.print("\t\tPlease ");
				printErr("ensure you are not missing a space");
				System.out.println(" after each comma.");
				stuPoll = stuLine.split(",");
			}
			else
				stuPoll = stuLine.split(", ");*/
			
			/*if(stuPoll.length < corPoll.length)
			{
				System.out.print("\t\tYour " + prefixToCheck + " line has ");
				printErr("less");
				System.out.println(" commas than it should.\"");
			}
			else if(stuPoll.length > corPoll.length)
				printlnErr("\t\tYour " + prefixToCheck + " line has more commas than it should.");*/
			
			printlnErr("Please make sure your output prints what the correct output should be.");
		}
		System.out.println("\n\n");
	}
	
	public static boolean checkOrder(String stuOut)
	{
		int preInd = stuOut.indexOf("Pre:");
		if(preInd == -1)
			printErr("\tPerhaps \"Pre:\" does not exist in the output...");
		else
		{
			System.out.println("\t\"Pre:\" was found first!");
			String afterPre = stuOut.substring(preInd);
			int inInd = afterPre.indexOf("In:");
			if(inInd == -1)
				printlnErr("\tThe next nonempty line in your output after \"Pre:\" does not seem to have \"In:\".");
			else
			{
				System.out.println("\t\"In:\" was found second!");
				String afterIn = afterPre.substring(inInd);
				int postInd = afterIn.indexOf("Post:");
				if(postInd == -1)
					printlnErr("\tThe next nonempty line in your output after \"In:\" does not seem to have \"Post\".");
				else
				{
					System.out.println("\t\"Post:\" was found third!");
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getNextNonEmpty(String[] lines, int lineNum)
	{
		if(lineNum+1 >= lines.length)
			System.out.println("\tEnd of your output was reached\n");
		for(int number = lineNum + 1; number < lines.length; number++)
		{
			if(!lines[number].equals(""))
			{
				if(number > lineNum + 1)
				{
					printlnErr("\tEmpty line(s) seem to be present between these two prefixes");
					System.out.println("\n");
				}
				return number; // return the first line number that is not empty
			}
		}
		return -1; // default; end was reached before an empty line was found;
	}
	
	public static String removeRN(String toRemoveRN)
	{
		String strToReturn = toRemoveRN;
		while(strToReturn.length() > 0 && (strToReturn.charAt(strToReturn.length()-1) == '\r' || strToReturn.charAt(strToReturn.length()-1) == '\n'))
			strToReturn = strToReturn.substring(0, strToReturn.length()-1);
		return strToReturn;
	}
	
	public static boolean checkForPrefixes(String studentAns)
	{
		boolean Pre = checkForPrefix(studentAns, "Pre");
		boolean In = checkForPrefix(studentAns, "In"); // need to have them separate due to short-circuiting
		boolean Post = checkForPrefix(studentAns, "Post");
		return Pre && In && Post;
	}
	
	public static boolean checkForPrefix(String studentOut, String prefix)
	{
		if (studentOut.indexOf(prefix + ":\t") != -1)
		{
			System.out.println("\t\"" + prefix + ":\\t\" was found!");
			return true;
		}
		//else if (studentOut.indexOf(prefix + ":\t") != -1)
		//	System.out.println("Potentially missing \"(\" after \"" + prefix + ":\\t\"");
		else if (studentOut.indexOf(prefix + ":") != -1)
			System.out.println("Potentially missing \"\\t\" after \"" + prefix + ":\"");
		else if (studentOut.indexOf(prefix) != -1)
			System.out.println("Potentially missing \":\\t\" after \"" + prefix + "\"");
		else
		{
			System.out.println("Potentially missing \"" + prefix + ":\\t\" completely\n\tPlease make appropriate changes and try again.\n");
			return false;
		}
		
		System.out.println("\tThe output should have something like this:\t\"" + prefix + ":\t...\"\n\t");
		if(studentOut.indexOf(prefix) == -1)
			System.out.println("but we did not find \"" + prefix + "\" at all.");
		else
		{
			System.out.print("but instead we found something like this: ");
			String stuOutLine = removeRN(studentOut.substring(studentOut.indexOf(prefix)));
			if(stuOutLine.length() < prefix.length()+2)
				System.out.println(stuOutLine);
			else
				System.out.println(stuOutLine.substring(0, prefix.length()+2) + " ...");
		}

		System.out.println("\tPlease make appropriate changes and try again.\n");
		return false;
	}
	
	public static void printErr(String message)
	{
		/*
		PrintStream out = System.out;
		PrintStream err = System.err;
		System.out.flush();
		System.setOut(err);
		System.out.print(message);
		System.out.flush();
		System.setOut(out);*/
		int numtabs = 0;
		while(message.charAt(0) == 't')
		{
			numtabs++;
			message = message.substring(1);
		}
		String tabs = "";
		for(int k = 0; k < numtabs; k++)
			tabs = tabs + "\t";
		//System.out.print(tabs +"!!____" + message + "____!!");
		System.out.print(tabs +" ▶▶▶▶" + message + "◀◀◀◀   ");
	}
	
	public static void printlnErr(String message)
	{
		/*
		PrintStream out = System.out;
		PrintStream err = System.err;
		System.out.flush();
		System.setOut(err);
		System.out.println(message);
		System.out.flush();
		System.setOut(out);
		*/
		String tabs = "";
		int numtabs = 0;
		while(message.charAt(0) == '\t')
		{
			numtabs++;
			message = message.substring(1);
		}
		for(int k = 0; k < numtabs; k++)
			tabs = tabs + "\t";
		//System.out.println(tabs +"!!____" + message + "____!!");※
		System.out.println(tabs +"▶▶▶▶▶▶▶" + message + "◀◀◀◀◀◀◀");
	}
	

	@Deprecated
	public static boolean checkOrder(String[] lines)
	{
		int lineNum = 0;
		
		if(lines[lineNum].indexOf("Pre:") != -1)
		{
			System.out.println("\t\"Pre:\" was found first!");
			
			lineNum = getNextNonEmpty(lines, lineNum);
			if(lineNum == -1)
				System.out.println("\tAfter \"Pre:\", the output does not seem to have any more lines.");
			else if(lines[lineNum].indexOf("In:") != -1)
			{
				System.out.println("\t\"In:\" was found second!");
				
				lineNum = getNextNonEmpty(lines, lineNum);
				if(lineNum == -1)
					System.out.println("\tAfter \"In:\", the output does not seem to have any more lines.");
				else if(lines[lineNum].indexOf("Post:") != -1)
				{
					System.out.println("\t\"Post:\" was found third!");
					System.out.println("");
					return true;
				}
				else
					printlnErr("\tThe next nonempty line in your output after \"In:\" does not seem to have \"Post\".");
			}
			else
				printlnErr("\tThe next nonempty line in your output after \"Pre:\" does not seem to have \"In:\".");
		}
		else
			printlnErr("\tPerhaps \"Pre:\" does not exist in the output...");
		System.out.println("");
		return false;
	}
	
	public static String getOutput(String[] mainArgs)
	{

		ByteArrayOutputStream storage = new ByteArrayOutputStream();
		PrintStream studentOut = new PrintStream(storage);
		PrintStream origOut = System.out;
		System.setOut(studentOut);
		
		Main.main(mainArgs);
		
		System.out.flush();
		System.setOut(origOut);
		
		return removeRN(storage.toString());
	}
	
	public static String[] getStudentLines(String studentWork)
	{
		String[] stuArr = studentWork.split("\n");
		for(int i = 0; i < stuArr.length; i++)
			stuArr[i] = removeRN(stuArr[i]);
		return stuArr;
	}
}
