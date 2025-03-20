/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 3.
 *
 * This program reads files with baby name data for the provided 
 * names and prints each name's alphabetical rank, the name's rank,
 * number of babies, and percent of babies for each year, and the
 * total rank, number of babies, and percentage for all provided
 * years.
 *
 */

import java.io.*;
import java.util.*;
import java.lang.Math;

public class Main {

    public static final int yearStartIndex=8, yearEndIndex=4; //indexes for parsing year from file
    public static final int flagDistance=2; //distance between flag indexes
    public static final int fileDistance=1; //distance between last name's and first file's index

    //see above description
    public static void main(String[] args) {
	//used to determine how many flags/names there are
	ArrayList<Integer> flagIndexes = new ArrayList<>();
	ArrayList<Integer> nameIndexes = new ArrayList<>();
	int counter = 0; //counts iterations of while loop
	//adds flag/name indexes, everything else is assumed to be a file
	while(args[counter].indexOf("-")!=-1) {
	    flagIndexes.add(counter);
	    if(counter+flagDistance > args.length-1) { //checks if arguments are missing
		System.out.println("Missing one or more argument(s)");
		System.exit(0);
	    }
	    nameIndexes.add(counter+1);
	    counter+=flagDistance;
	}
	//insures there is at least one flag
	if(flagIndexes.size()==0) {
	    System.out.println("Missing one or more argument(s)");
	    System.exit(0);
	}
	
	int firstFileIndex = nameIndexes.get(nameIndexes.size()-1) + fileDistance; //index of first file
	String firstFile=""; //used to parse the first year
	//makes sure there is at least one potential file
	try {
	    firstFile = args[firstFileIndex];
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.out.println("Illegal argument. Missing one or more arguments.");
	    System.exit(0);
        }
	
	//makes a new DLL for each name and stores/prints the necessary data
	for(int i=0; i<flagIndexes.size(); i++) { 
	    NameDLL names = new NameDLL(); //DLL that stores all the data from the files 
	    Name name = null; //updated for each name
	    //indexes of the ith flag and name
	    int flagIndex = flagIndexes.get(i), nameIndex = nameIndexes.get(i);
	    int total = 0; //total number of babies from all years
	    double percentage; //percentage of babies with the name 
	    ArrayList<Integer> yearTotals = new ArrayList<>(); //stores total number of babies for each year

	    //adds data from all files to the DLL for one flag
	    for(int fileIndex=firstFileIndex; fileIndex<args.length; fileIndex++) {
	       	//makes sure flag and file name are valid
		try {
		    names.readNames(args[fileIndex], args[flagIndex]); //adds one file's data to the DLL
		} catch (IllegalArgument e) {
		    System.out.println(e.getMessage());
		    System.exit(0);
		} catch (FileNotFoundException e) {
		    System.out.println("Illegal argument. File '"+args[fileIndex]+"' not found.");
		    System.exit(0);
	        } 
		yearTotals.add(names.getYearlyTotal());
	    }

	    //makes sure name is a valid name
	    try {
		name = names.findName(args[nameIndex]);
	    } catch (IllegalArgument e) {
		System.out.println(e.getMessage());
		System.exit(0);
	    }

	    //prints name's alphabetical rank after all files have been added
	    System.out.println(name.getRank()+"\n");

	    //parses the first file's year
	    int yearStart = firstFile.length()-yearStartIndex;
	    int yearEnd = firstFile.length()-yearEndIndex;
	    String firstFileSub = firstFile.substring(yearStart, yearEnd);
	    int firstYear = Integer.parseInt(firstFileSub); //year of the first file read

	    //prints the data for each year for one name
	    for(int j=0; j<name.getYears().size(); j++) {
		Year year = name.getYears().get(j); //jth year from name's years
		//calculates appropriate index for yearlyTotals
		int yearIndex = Math.abs(year.getYear()-firstYear); //assumes files each increase/decrease by one year
		if(yearIndex>=yearTotals.size()) //checks if files are more than one year apart
		    yearIndex = j;
		percentage = (double) year.getNumOfBabies()/yearTotals.get(yearIndex);
		System.out.println(year.getYear());
		System.out.print(name.getName()+": "+year.toString()+", ");
		System.out.format("%.6f",percentage);
		System.out.println("\n");
	    }
	    
	    //calculates total number of babies for all years
	    for(int j=0; j<yearTotals.size(); j++) {
		total+=yearTotals.get(j);
	    }
	    //prints the total data for one name
	    percentage = (double) name.nameTotal()/total;
	    System.out.println("Total");
	    System.out.print(name.getName()+": "+names.totalRank(name)+", "+name.nameTotal()+", ");
	    System.out.format("%.6f",percentage);
	    System.out.println("\n");
	}
    }	
}
