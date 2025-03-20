/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 7.
 *
 * This program reads democratic primaries data files from the command
 * line and creates a candidate object. It then stores each candidate in 
 * an array heap and prints out the heap with updated data after each
 * file is read. It also takes optional arguments to print out the top
 * n candidates and remove candidates from consideration.
 *
 */

import java.io.*;
import java.util.*;

public class Main {
    public static final String nFlag = "-n", rFlag = "-r"; //argument flags
    public static final int illegalIndex = -1; //non valid index
    
    public static void main(String[] args) {
	ArrayHeap<Candidate> data = new ArrayHeap<Candidate>(); //heap that stores the most recent polling data
	int firstFile = LookupPollData.firstFileIndex(args); //index of the first file argument
	
	//prints updated heap after each file is read
	for (int i=firstFile; i<args.length; i++) {
	    try {
		LookupPollData.readPollData(args[i], data);
	    } catch (FileNotFoundException e) {
		System.out.println("File not found.");
		System.exit(0);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.out.println("No valid files were given.");
		System.exit(0);
	    }
	    System.out.println(data);
	}
        
	if (firstFile > 0) {
	    int n = illegalIndex; //index of -n flag
	    int r = illegalIndex; //index of -r flag
	    int rStop; //end of name arguments
	    int topN = illegalIndex; //-n's argument (top number of candidates)
	    ArrayList<Candidate> topCandidates = new ArrayList<>(); //stores top n candidates
	    //finds index of -n and -r if they exist
	    for (int i=0; i<firstFile; i++) {
		if (args[i].equals(nFlag))
		    n = i;
		else if (args[i].equals(rFlag))
		    r = i;
	    }
	    if (n == illegalIndex) {
		System.out.println("Illegal argument, -n not found.");
		System.exit(0);
	    }
	    if (r != illegalIndex) {
		 //determines indexes of -r's name arguments
		if (n < r)
		    rStop = firstFile;
		else
		    rStop = n;
		//removes -r's arguments
		for (int i=r+1; i<rStop; i++) {
		    Candidate target = new Candidate(args[i],"",0);
		    data.remove(target);
		}
	    }
	    //checks if -n's argument is valid
	    try {
	        topN = Integer.parseInt(args[n+1]);
	    } catch (NumberFormatException e) {
		System.out.println("Illegal argument for n.");
		System.exit(0);
	    }
	    try {
		topCandidates = data.peekTopN(topN);
	    } catch (IndexOutOfBoundsException e) {
		System.out.println("Illegal argument, n is out of bounds.");
		System.exit(0);
	    }
	   
	    System.out.println("Top "+topN+" Candidates:");
	    for (int i=0; i<topCandidates.size(); i++) {
		System.out.print(topCandidates.get(i));
		//prints tied candidates on the same line
		while (i<topCandidates.size()-1 && topCandidates.get(i).compareTo(topCandidates.get(i+1)) == 2) {
		    System.out.print(" "+topCandidates.get(i+1));
		    i++;
		}
		System.out.print("\n");
	    }
	}
    }
}
