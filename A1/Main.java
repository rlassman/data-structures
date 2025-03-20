/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 1.
 *
 * This program continuously prompts the user for a zipcode until they enter 00000
 * to quit. It reads the uszipcodes.csv file and creates an array of places based 
 * on the data, then finds and prints the town and state that correspond
 * to the entered zipcode. It tells the user that there is no such zipcode if it is
 * not found in the array.
 *
 */

import java.io.*;
import java.util.*;

public class Main {
    //See desc above
    public static void main(String[] args) throws FileNotFoundException {

	//Prompts user for the first zipcode
	Scanner input = new Scanner(System.in);
	System.out.print("zipcode: ");
	String userZip = input.next(); //zipcode entered by user
	Place[] places = LookupZip.readZipCodes("uszipcodes.csv");

	//Finds and prints corresponding town/state
	while (!userZip.equals("00000")) { //checks if quit code entered
	    Place place = LookupZip.lookupZip(places, userZip);
	    if (place == null) {
		System.out.println("No such zipcode\n");
	    } else {
		System.out.println(place.getTown()+", "+place.getState()+"\n");
	    }
	    //prompts user for the next zipcode
	    System.out.print("zipcode: ");
	    userZip = input.next();
	}
	
	System.out.println("Good Bye!"); //terminates program once quit code is entered
    }

}

class Place {

    private String zipcode; //5-digit zipcode
    private String town; //name of town
    private String state; //two letter state name abbreviation

     /** Constructs a Place object containing a zipcode, town, and state
     * @param zip 5-digit zipcode
     * @param town Name of town
     * @param state Two letter state name abbreviation
     */
    public Place(String zip, String town, String state) {
	zipcode = zip;
	this.town = town;
	this.state = state;
    }

     /** Returns the value of zipcode
     * @return The value of the zipcode instance variable
     */
    public String getZip() {
	return zipcode;
    }

     /** Returns the value of town
     * @return The value of the town instance variable
     */
    public String getTown() {
	return town;
    }

     /** Returns the value of state
     * @return The value of the state instance variable
     */
    public String getState() {
	return state;
    }

     /** Formats how a Place is printed
     * @return A string containing a Place's zipcode, town, and state
     */
    public String toString() {
	return "zipcode: "+zipcode+"\n"+town+", "+state;
    }

}

class LookupZip {

    /** Parses one line of input by creating a Place that
     * denotes the information in the given line
     * @param lineNumber The line number of this line
     * @param line One line from the zipcodes file
     * @return A Place that contains the relevant information
     * (zip code, town, state) from that line
     */
    public static Place parseLine(int lineNumber, String line) {
	
	String[] tokens = line.split(","); //stores the tokens from given line
	String zip = tokens[0]; //zipcode from given line
	String town = tokens[1]; //town from given line
	String state = tokens[2]; //state from given line
	Place place = new Place(zip, town, state);
	return place;
    }

    /** Reads a zipcodes file, parsing every line
     * @param filename The name of the zipcodes file
     * @return The array of Places representing all the
     * data in the file.
     */
    public static Place[] readZipCodes(String filename) throws FileNotFoundException {
	
	Scanner input = new Scanner(new File(filename));
	int lineNum = 0; //current line being read from the file

	//Extracts the total number of places from the file's first line
	String firstLine = input.nextLine();
	String[] tokens = firstLine.split(",");
	int totalPlaces = Integer.parseInt(tokens[0]);
	Place[] places = new Place[totalPlaces]; //stores all the Places in the file

	//Reads each line in the file and creates a Place,
	//then stores it in the places array  
	while (input.hasNextLine()) {
	    String line = input.nextLine();
	    Place place = parseLine(lineNum, line);
	    places[lineNum] = place;
	    lineNum++;
	}
	input.close();
	
	return places;
    }

    /** Find a Place with a given zip code
     * @param places The array of places being searched
     * @param zip The zip code (as a String) to look up
     * @return A place that matches the given zip code,
     * or null if no such place exists.
     */
    public static Place lookupZip(Place [] places, String zip) {

	//Compares each zipcode in places array to the given zipcode
	for (int i=0; i<places.length; i++) {
	    String placeZip = places[i].getZip(); //zipcode of ith element in places
	    if (placeZip.equals(zip)) {
		return places[i];
	    }
	}
	return null;
    }
    
}
