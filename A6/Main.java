/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 6.
 *
 * This program reads democratic primaries data files from the command
 * line and creates a candidate object. It then stores each candidate a 
 * linked binary tree and prints out the tree with updated data after each
 * file is read. The files are assumed to be given in increasing date order.
 *
 */

import java.io.*;

public class Main {
    //used to parse the year from a file's name
    public static final int START = 10, END = 6;
    
    public static void main(String[] args) {
	LinkedBinaryTree<Candidate> data = new LinkedBinaryTree<>(); //stores the candidates
	String prevDate = ""; //date from the file previously read
	boolean sameDate; //if the previous and current file are from the same date
	 
	for(int i=0; i<args.length; i++) {
	    //determines if the file has the same date as the file previously read
	    //so that the two polling percents will be averaged
	    String date = args[i].substring(args[i].length()-START, args[i].length()-END);
	    if (prevDate.equals(date))
		sameDate = true;
	    else
		sameDate = false;
	    
	    //updates the tree
	    try {
		LookupPollData.readPollData(args[i], data, sameDate);
	    } catch (FileNotFoundException e) {
		System.out.println("File not found.");
		System.exit(0);
	    }
	    System.out.println(data);
	    prevDate = date;
	}	
    }  
}
