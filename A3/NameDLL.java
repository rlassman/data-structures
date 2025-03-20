/* Name: Rebecca Lassman
 * File: NameDLL.java
 * Desc:
 *
 * This class constructs and manipulates a Doubly-Linked List of Names
 *
 */

import java.io.*;
import java.util.*;

public class NameDLL {

     private static class Node {
	 private Name data; //the Name stored in this node
	 private Node next; //a reference to the subsequent  node in the list
	 private Node prev; //a reference to the preceding node in the list

	 /** Creates a node with the given element and next node.
	  * @param name  the Name to be stored
	  * @param p  reference to a node that should precede the new node
	  * @param n  reference to a node that should follow the new node
	  */
	 public Node(Name name, Node p, Node n) {
	     data = name;
	     prev = p;
	     next = n;
	 }

	 /** Returns the value of data
	  * @return The value of the data instance variable
	  */
	 public Name getData() {
	     return data;
	 }

	 /** Returns the value of next
	  * @return The following node
	  */
	 public Node getNext() {
	     return next;
	 }

	 /** Returns the value of year
	  * @return The previous node
	  */
	 public Node getPrev() {
	     return prev;
	 }

	 /** Sets the node's next reference to point to Node n
	  * @param n The node that should follow this one
	  */
	 public void setNext(Node n) {
	     next = n;
	 }

	 /** Sets the node's previous reference to point to Node n
	  * @param p The node that should precede this one
	  */
	 public void setPrev(Node p) {
	     prev = p;
	 }
    } //end of Node class
    
    private Node header = null; //sentinel node at the beginning of the list
    private Node trailer = null; //sentinel node at the end of the list
    private int size = 0; //number of names in the list (excluding sentinels)
    private int yearlyTotal = 0; //total number of babies in one year
    //Indexes of data in a line from a file
    public static final int rankIndex=0, maleName=1, maleNum=2, femaleName=3, femaleNum=4;
    //Distances from the end of a file name for parsing the file's year
    public static final int yearStartIndex=8, yearEndIndex=4;

    /** Constructs a new empty list. */
    public NameDLL() {
	 header = new Node(null, null, null);  
	 trailer = new Node(null, header, null);
	 header.setNext(trailer);
    }

    /** Returns the value of yearlyTotal
      * @return The value of the yearlyTotal instance variable
      */
    public int getYearlyTotal() {
	return yearlyTotal;
    }

    /** Returns the number of elements in the linked list.
     * @return The number of elements in the linked list
     */
    public int size() {
	return size;
    }

    /** Tests whether the linked list is empty.
     * @return True if the linked list is empty, false otherwise
     */
    public boolean isEmpty() {
	return size == 0;
    }

    /** Returns the first Name in the list.
     * @return Element at the front of the list (or null if empty)
     */
    public Name first() {
	if(isEmpty()) {
	    return null;
	} else {
	    return header.getNext().getData();
	}
    }

    /** Returns the last Name in the list.
     * @return Element at the end of the list (or null if empty)
     */
    public Name last() {
	if (isEmpty()) {
	    return null;
	} else {
	    return trailer.getPrev().getData();
	}
    }

    /** Adds a name to the end of the list.
     * @param name The new name to add
     */
    public void addLast(Name name) {
	Node newest = new Node(name, trailer.getPrev(), trailer);
	trailer.getPrev().setNext(newest);
	trailer.setPrev(newest);
	size++;
    }

    /** Adds a name to the front of the list.
      * @param name The new name to add
      */
    public void addFirst(Name name) {
       	Node newest = new Node(name, header, header.getNext());
	header.getNext().setPrev(newest);
	header.setNext(newest);
	size++;
    }

    /** Removes and returns the name at node
     * @param node The node to be removed
     * @return The removed name (or null if empty)
     */
    public Name remove(Node node) {
	 Node predecessor = node.getPrev();
	 Node successor = node.getNext();
	 predecessor.setNext(successor);
	 successor.setPrev(predecessor);
	 size--;
	 return node.getData();
    }

    /** Formats how the DLL is printed.
     * @return A String containing all the names in the DLL
     */
    public String toString() {
	String s = new String();
	for(Node n=header.getNext(); n!=trailer; n=n.getNext()) {
	    s = s + n.getData();
	    if (n!=trailer.getPrev()) {
		s = s+", ";
	    }
	}
	return s;
    }

    /** Adds a name in front of the specified node.
      * @param name The new name to add
      * @param n The node that name will precede
      */
    public void insertBefore(Name name, Node n) {
	Node newest = new Node(name, n.getPrev(), n);
	n.getPrev().setNext(newest);
        n.setPrev(newest);
	size++;
    }

    /** Adds a name to the correct alphabetical position in the DLL
      * @param name The new name to add
      */
    public void insertSorted(Name name) {
	Node n = header.getNext();
	//String comparison between name and each name in the DLL 
	while(n!=trailer&&name.getName().compareTo(n.getData().getName())>0) {
	    n=n.getNext();
        }
        insertBefore(name, n);
    }

    /** Parses one line of input by creating a Name that denotes the information in the given line
     * @param line One line from the baby names file
     * @param isMale Controls whether the male or female data is parsed
     * @return A Name that contains the name from the line
     */
    public Name parseLineName(String line, boolean isMale) {
	String[] tokens = line.split(","); //stores the tokens from given line
	Name name; //new name from line
	if(isMale)
	    name = new Name(tokens[maleName]);
	else
	    name = new Name(tokens[femaleName]);
	return name;
    }

    /** Parses one line of input by creating a Year that denotes the information in the given line.
     * @param line One line from the baby names file
     * @param fileYear The year parsed from the file name
     * @param isMale Controls whether the male or female data is parsed
     * @return A Year that contains the file year and the rank and number of babies from the line
     */
    public Year parseLineYear(String line, int fileYear, boolean isMale) {
	String[] tokens = line.split(","); //stores the tokens from given line
	int rank = Integer.parseInt(tokens[rankIndex]); //rank from line
	int numOfBabies; //number of babies from line
	if(isMale) {
	    numOfBabies = Integer.parseInt(tokens[maleNum]);
	} else {
	    numOfBabies = Integer.parseInt(tokens[femaleNum]);
	}
	return new Year(fileYear, rank, numOfBabies);
	
    }

    /** Reads a baby names file, parsing every line, and adds the data to the DLL.
     * @param fileName The name of the baby names file containing rank, name, and number of babies data
     * @param flag Either -f or -m to determine if male or female data should be parsed
     */
    public void readNames(String fileName, String flag) throws FileNotFoundException { 
	Scanner input = new Scanner(new File(fileName));
	yearlyTotal = 0; //total number of babies in the file
	
	//controls if male or female data is parsed
	boolean isMale;
	if(flag.equals("-m"))
	    isMale = true;
	else if(flag.equals("-f"))
	    isMale = false;
	else
	    throw new IllegalArgument("Illegal flag. Must be '-m' or '-f'");
	
	//parses the file year from the file name
	int yearStart = fileName.length()-yearStartIndex;
	int yearEnd = fileName.length()-yearEndIndex;
	String fileYear = fileName.substring(yearStart, yearEnd);
	int year = Integer.parseInt(fileYear); //file's numerical year
	
	String line; //stores a line from the file
	Name newName; //name to store data from the file
	Year newYear; //year to store data from the file

	//reads the file
	while (input.hasNextLine()) {
	    line = input.nextLine();
	    newName = parseLineName(line, isMale);
	    newYear = parseLineYear(line, year, isMale);
	    yearlyTotal+=newYear.getNumOfBabies();
	    newName.getYears().add(newYear); //adds newYear to the name's ArrayList of years
	    //checks if the name is already in the list and if it is not,
	    //adds newName in its alphabetical place
	    boolean duplicateName = this.checkDuplicateName(newName, newYear);
	    if (!duplicateName)
		this.insertSorted(newName);
	}
	input.close();
	this.updateRank();
    }


     /** Checks if a name is already in the DLL and if so, adds a Year to that name.
     * @param name The name being checked
     * @param year The year to add to name's ArrayList of years
     * @return True if the name is already in the list, false otherwise
     */
    public boolean checkDuplicateName(Name name, Year year) {
	for(Node n=header.getNext(); n!=trailer; n=n.getNext()) {
	    if(n.getData().getName().equals(name.getName())) {
		n.getData().getYears().add(year);
		return true;
	    }
        }
       	return false;
    }

     /** Sets each name's rank to its position in the DLL. */
    public void updateRank() {
	int rank = 1;
	for(Node n=header.getNext(); n!=trailer; n=n.getNext()) {
	    n.getData().setRank(rank);
	    rank++;
	}
    }

    /** Finds a name that matches the given name.
     * @param name The name being searched for
     * @return A Name object that has the same name as the given name
     */
    public Name findName(String name) {
	for(Node n=header.getNext(); n!=trailer; n=n.getNext()) {
	    if(n.getData().getName().equals(name)) {
		return n.getData();
	    }
	}
	//throws an IllegalArgument excpetion if the name is not in the DLL
	throw new IllegalArgument("Illegal argument. Name '"+name+"' not found."); 
    }

     /** Calculates a name's total rank in the DLL based on number of babies.
     * @param name The name whose rank is being calculated
     * @return The name's overall rank
     */
    public int totalRank(Name name) {
	int rank = 1;
	//compares name's total number of babies for all years to each name's total number of babies
	for(Node n=header.getNext(); n!=trailer; n=n.getNext()) {
	    if(name.nameTotal()<n.getData().nameTotal())
		rank++;
	}
	return rank;
    }
    
}
