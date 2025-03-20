/* Name: Rebecca Lassman
 * File: LookupPollData.java
 * Desc:
 *
 * This class reads data from a democratic primaries data file
 * and stores it in a linked binary tree.
 *
 */

import java.io.*;
import java.util.*;

public class LookupPollData {
    //Indexes for data file lines
    public static final int LAST = 0, FULL = 1, PERCENT = 2;

    /** Reads a democratic primaries data file, parsing every line, 
     * and adds the data to a linked binary tree
     * @param fileName The name of the data file
     * @param lbt The linked binary tree that is added to
     * @param sameDate Determines if the previous file read was 
     * from the same date as this one
     */
    public static void readPollData(String fileName, LinkedBinaryTree<Candidate> lbt, boolean sameDate) throws FileNotFoundException {
	Scanner input = new Scanner(new File(fileName));
	String firstLine = input.nextLine(); //discards first line containing file format

	//reads each line of the file and updates the tree
	while (input.hasNextLine()) {
	    String line = input.nextLine(); //line being read
	    Candidate newest = parseLine(line); //candidate created from line's data
	    if (lbt.contains(newest)) {
		Candidate duplicate = lbt.findElement(newest); //the candidate object already in the tree
		if (sameDate) {
		    double average = (duplicate.getPercent() + newest.getPercent()) / 2;
		    duplicate.setPercent(average);
		} else {
		    duplicate.setPercent(newest.getPercent());
		}
	    } else {
		lbt.insert(newest);
	    }
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
}
