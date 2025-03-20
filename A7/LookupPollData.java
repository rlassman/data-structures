/* Name: Rebecca Lassman
 * File: LookupPollData.java
 * Desc:
 *
 * This class reads data from a democratic primaries data file
 * and stores it in an array heap.
 *
 */

import java.io.*;
import java.util.*;

public class LookupPollData {
    //Indexes for data file lines
    public static final int LAST = 0, FULL = 1, PERCENT = 2;

    /** Reads a democratic primaries data file, parsing every line, 
     * and adds the data to an array heap
     * @param fileName The name of the data file
     * @param lbt The array heap that is added to
     */
    public static void readPollData(String fileName, ArrayHeap<Candidate> aHeap) throws FileNotFoundException {
	Scanner input = new Scanner(new File(fileName));
	String firstLine = input.nextLine(); //discards first line containing file format

	//reads each line of the file and updates the heap
	while (input.hasNextLine()) {
	    String line = input.nextLine(); //line being read
	    Candidate newest = parseLine(line); //candidate created from line's data
	    aHeap.insert(newest);
	}
    }

    /** Parses one line of input by creating a candidate that
     * denotes the information in the given line
     * @param line One line from the file
     * @return A candidate that contains the relevant information
     * (last name, full name, percent) from that line
     */
    public static Candidate parseLine(String line) {
	String[] tokens = line.split(","); //stores the tokens from given line
	double percent = Double.parseDouble(tokens[PERCENT]);
	return new Candidate(tokens[LAST], tokens[FULL], percent);
    }

    /** Finds the index of the first file from an array of command
     * line arguments.
     * @param args[] An array of command-line arguments
     * @return The index of the first file argument or -1 if there is none
     */
    public static int firstFileIndex(String[] args) {
	for(int i=0; i<args.length; i++) {
	    if (args[i].indexOf("dempres") >= 0)
		return i;
	}
	return -1;
    }
}
