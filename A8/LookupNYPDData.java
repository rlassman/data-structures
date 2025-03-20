/* Name: Rebecca Lassman
 * File: LookupNYPDData.java
 * Desc:
 *
 * This class stores data from a SQF data file as suspect objects in
 * an arraylist. It has five different methods to deduplicate the data.
 *
 */

import java.io.*;
import java.util.*;

public class LookupNYPDData {
    //indexes for attributes used to check for equality
    public static final int SEX = 80, RACE = 81, DOB = 82, FT = 84, IN = 85, WEIGHT = 86, HAIR = 87, EYE = 88, BUILD = 89;
    public static final int N = 1000003; //hash table capacity
    
    private ArrayList<Suspect> data = new ArrayList<>(); //stores data from each row of file
    private String file; //data file being read
    private int size = 0; //size of data

    /** Creates a LookupNYPD object and adds all data from the file to the data array
     * @param file Data file being read
     */
    public LookupNYPDData(String file) throws FileNotFoundException {
	this.file = file;
	Scanner input = new Scanner(new File(file));
	String firstLine = input.nextLine(); //discards first line containing file format
	while (input.hasNextLine()) {
	    String line = input.nextLine(); //line being read
	    Suspect newest = parseLine(line); //suspect created from line's data
	    data.add(newest);
	}
	size = data.size();
    }

    /** Parses one line of input by creating a suspect that
     * denotes the information in the given line
     * @param line One row of the file
     * @return A suspect that contains the relevant information from that line
     */
    private static Suspect parseLine(String line) {
	String[] tokens = line.split(","); //stores the tokens from given line
        return new Suspect(tokens[SEX], tokens[RACE], tokens[DOB], tokens[FT], tokens[IN], tokens[WEIGHT], tokens[HAIR], tokens[EYE], tokens[BUILD]);
    }

    /** Returns the number of rows processed
     * @return The size of the array containing all suspects created from the data
     */
    public int size() {
	return size;
    }

    /** Removes duplicate suspects by comparing every element 
     * to the rest of the elements as it is inserted.
     * @return An array list of unique suspects
     */
    public ArrayList<Suspect> allPairsDeduplication() {
	ArrayList<Suspect> noDupes = new ArrayList<>(); //arraylist of unique suspects
	for (int i=0; i<data.size(); i++) {
	    noDupes.add(data.get(i));
	    int newest = noDupes.size()-1; //index of suspect just added
	    Suspect current = noDupes.get(newest);
	    //compares newest suspect to those already in the arraylist
	    for (int j=0; j<noDupes.size(); j++) {
		if (j != newest && current.compareTo(noDupes.get(j)) == 0) {
		    noDupes.remove(newest);
		    break;
		}
	    }
	}
	return noDupes;
    }

    /** Removes duplicate suspects by inserting them into a hash table
     * that implements linear hashing
     * @return An array list of unique suspects
     */
    public ArrayList<Suspect> hashLinearDeduplication() { 
	ProbeHashMap<String, Suspect> hashTable = new ProbeHashMap<>(N); //hash table of unique suspects
	for (int i=0; i<data.size(); i++) {
	    Suspect newest = data.get(i);
	    hashTable.put(newest.toString(), newest);
	}
	System.out.println("average number of probes during insertions: "+hashTable.getTotalProbes()/hashTable.size());
	System.out.println("max number of probes during insertions: "+hashTable.getMaxProbes());
	System.out.println("load_factor after insertions: " + (double) hashTable.size()/N);

	//converts hash table to an arraylist
	ArrayList<Suspect> noDupes = new ArrayList<>();
	Iterable<Suspect> iter = hashTable.values();
	iter.forEach(noDupes::add);
	return noDupes;
    }

    /** Removes duplicate suspects by inserting them into a hash table
     * that implements double hashing
     * @return An array list of unique suspects
     */
    public ArrayList<Suspect> hashDoubleDeduplication() {
	DoubleHashMap<String, Suspect> hashTable = new DoubleHashMap<>(N); //hash table of unique suspects
	for (int i=0; i<data.size(); i++) {
	    Suspect newest = data.get(i);
	    hashTable.put(newest.toString(), newest);
	}
	System.out.println("average number of probes during insertions: "+hashTable.getTotalProbes()/hashTable.size());
	System.out.println("max number of probes during insertions: "+hashTable.getMaxProbes());
	System.out.println("load_factor after insertions: " + (double) hashTable.size()/N);

	//converts hash table to an arraylist
	ArrayList<Suspect> noDupes = new ArrayList<>();
	Iterable<Suspect> iter = hashTable.values();
	iter.forEach(noDupes::add);
	return noDupes;
    }

    /** Removes duplicate suspects by sorting the data via java's
     * sort then removing duplicates
     * @return An array list of unique suspects
     */
    public ArrayList<Suspect> builtinSortDeduplication() { 
	ArrayList<Suspect> sorted = new ArrayList<>(); //copy of data, sorted
	ArrayList<Suspect> noDupes = new ArrayList<>(); //sorted with duplicates removed
	
	for (int i=0; i<data.size(); i++) {
	    sorted.add(data.get(i));
	}
	sorted.sort(null);
	//removes a suspect if the suspect previously inserted is equal to it
	for (int i=0; i<sorted.size(); i++) {
	    noDupes.add(sorted.get(i));
	    int newest = noDupes.size() - 1; //index of element just added
	    if (newest > 0 && noDupes.get(newest).compareTo(noDupes.get(newest-1)) == 0) {
		noDupes.remove(newest);
	    }
	}
	return noDupes;
    }

    /** Removes duplicate suspects by sorting the data via quick sort
     * then removing duplicates
     * @return An array list of unique suspects
     */
    public ArrayList<Suspect> quickSortDeduplication() {
	ArrayList<Suspect> sorted = new ArrayList<>(); //copy of data, sorted
	ArrayList<Suspect> noDupes = new ArrayList<>(); //sorted with duplicates removed
	
	for (int i=0; i<data.size(); i++) {
	    sorted.add(data.get(i));
	}
	inPlaceQuickSort(sorted, 0, sorted.size()-1);

	//removes a suspect if the suspect previously inserted is equal to it
	for (int i=0; i<sorted.size(); i++) {
	    noDupes.add(sorted.get(i));
	    int newest = noDupes.size() - 1; //index of element just added
	    if (newest > 0 && noDupes.get(newest).compareTo(noDupes.get(newest-1)) == 0) {
		noDupes.remove(newest);
	    }
	}
	return noDupes;
    }

     /** Sorts an arraylist using an in-place quick sort
     * @param S The arraylist being sorted
     * @param s The starting index of the elements being sorted
     * @param e The ending index of the elements being sorted
     */
    private static void inPlaceQuickSort(ArrayList<Suspect> S, int s, int e) {
	if (s >= e)
	    return;
	int l = s; //left index
	int r = e-1; //right index
	Suspect pivot = S.get(e); //element being compared to
	while (l <= r) { //l has not passed r
	    while (l <= r && S.get(l).compareTo(pivot) <= 0)
		l++;
	    while (l <= r && S.get(r).compareTo(pivot) > 0)
		r--;
	    if (l <= r) {
		swap(S, l, r);
		l++;
		r--;
	    }
	}
	swap(S, l, e); //swaps the pivot into the middle
	//recursive call on elements smaller & larger than pivot 
	inPlaceQuickSort(S, s, l-1);
	inPlaceQuickSort(S, l+1, e);
    }

     /** Swaps two elements in an arraylist of suspects
     * @param S The arraylist containing the elements being swapped
     * @param i The first element being swapped
     * @param j The second element being swapped
     */
    private static void swap(ArrayList<Suspect> S, int i, int j) {
        Suspect temp = S.get(i);
	S.set(i, S.get(j));
	S.set(j, temp);
    }
}
