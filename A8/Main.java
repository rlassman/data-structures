/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 8.
 *
 * This program reads a SQF data file from the command
 * line and stores some data from each row as a suspect object
 * in an arraylist. It then uses a hash table with double 
 * hashing to deduplicate the data.
 *
 */

import java.io.*;
import java.util.*;

public class Main {
    public static final int FILE = 0; //index of the command-line argument containing an SQF file

    public static void main(String[] args) throws FileNotFoundException {
	LookupNYPDData data = new LookupNYPDData(args[FILE]); //processes the data from the file
	ArrayList<Suspect> doubleHash = data.hashDoubleDeduplication(); //stores the deduplicated data
	
	System.out.println("Records given: " + data.size());
	System.out.println("Attributes checked: race, sex, date of birth, height, weight, hair color, eye color, build");
	int dupes = data.size()-doubleHash.size(); //number of duplicates found & removed
	System.out.println("Dupliactes found: " + dupes);
    }
}
