/* Name: Rebecca Lassman
 * File: LookupZip.java
 * Desc:
 *
 * This program contains static methods that read two zipcodes files and combine the data
 * from both to create an ArrayList of Places, LocatedPlaces, and PopulatedPlaces.
 * It also finds a place in the ArrayList when given a zipcode.
 *
 */

import java.io.*;
import java.util.*;

public class LookupZip {
    //Indexes for data file lines
    public static final int ZIP = 0, TOWN = 1, STATE = 2, POP = 3, LAT = 5, LON = 6;

    /** Parses one line of input by creating a LocatedPlace or
     * PopulatedPlace that denotes the information in the given line
     * @param lineNumber The line number of this line
     * @param line One line from the zipcodes file
     * @return A place that contains the relevant information
     * (zipcode, town, state, and population if availible) from that line
     */
    public static LocatedPlace parseLine(int lineNumber, String line) {
	String[] tokens = line.split(",", 0); //stores the tokens from given line
       	String zip = tokens[ZIP]; //zipcode from given line
	String town = tokens[TOWN]; //town from given line
	String state = tokens[STATE]; //state from given line

	//checks if population is listed
	if (tokens.length > POP) {
	    //creates a PopulatedPlace with data from line
	    String popString = tokens[POP]; //population from given line
	    int pop = Integer.parseInt(popString);
	    PopulatedPlace popPlace  = new PopulatedPlace(zip, town, state, 0, 0, pop); //0 is a placeholder for latitude/longitude
	    return popPlace;
	} else {
	    //creates a LocatedPlace with data from line
	    LocatedPlace place = new LocatedPlace(zip, town, state, 0, 0); //0 is a placeholder for latitude/longitude
	    return place;
	}
    }

     /** Adds latitude/longitude data from the given line to a place with
     * the same zipcode in the given ArrayList 
     * @param line One line from the zipcodes file
     * @param places An ArrayList of LocatedPlaces with latitude/longitude data missing
     */
    public static void combineData(String line, ArrayList<LocatedPlace> places) {
	//parses data from line
	String[] tokens = line.split(",", 0); //stores tokens from given line
	String zip = tokens[ZIP].replace("\"",""); //zipcode from given line
	String latString = tokens[LAT]; //latitude from given line
	String lonString = tokens[LON]; //longitude from given line

	//checks if latitude is missing from line
	if (!latString.equals("")) {
	    //stores latitude and longitude as doubles
	    double lat = Double.parseDouble(latString);
	    double lon = Double.parseDouble(lonString);

	    //adds latitude and longitude data to a LocatedPlace with the same zipcode
	    for(int i=0; i<places.size(); i++) {
	        if(zip.equals(places.get(i).getZip())) {
		    places.get(i).setLatitude(lat);
		    places.get(i).setLongitude(lon);
		}
	    }

	}
    }

    /** Reads two zipcodes files, parsing every line, and combines the data from both files
     * @param file1Name The name of the zipcodes file containing zipcode, town, state, and population data
     * @param file2Name The name of the zipcodes file containing zipcode, latitude, and longitude data
     * @return The ArrayList of Places representing all the data in the files.
     */
    public static ArrayList<Place> readZipCodes(String file1Name, String file2Name) throws FileNotFoundException {
	Scanner input = new Scanner(new File(file1Name));
	int lineNum = 0; //current line being read from the file
	String line; //stores a line from the zipcode file
	ArrayList<LocatedPlace> places = new ArrayList<>(); //temporarily stores all LocatedPlaces
	ArrayList<Place> updatedPlaces = new ArrayList<>(); //stores all Places once data is updated

	//reads the first file and adds LocatedPlaces/PopulatedPlaces
	//(with 0 as a placeholder for latitude/longitude) to the ArrayList
	String firstLine = input.nextLine();
	while (input.hasNextLine()) {
	    line = input.nextLine();
	    places.add(parseLine(lineNum, line));
	    lineNum++;
	}
	input.close();

	//reads the second file and adds availible latitude/longitude data to the existing LocatedPlaces
	Scanner input2 = new Scanner(new File(file2Name));
	firstLine = input2.nextLine();
	while (input2.hasNextLine()) {
	    line = input2.nextLine();
	    combineData(line, places);
	}
	input2.close();

	//changes LocatedPlaces without latitude/longitude data to Places
	for(int i=0; i<places.size(); i++) {
	    updatedPlaces.add(places.get(i));
	    if (places.get(i).getLatitude()==0) { //checks if latitude has the 0 placeholder
		Place place = new Place(places.get(i).getZip(), places.get(i).getTown(), places.get(i).getState());
		updatedPlaces.set(i, place);
	    }
	}
	
	return updatedPlaces;
    }


    /** Find a Place with a given zip code
     * @param places The ArrayList of places being searched
     * @param zip The zip code (as a String) to look up
     * @return A place that matches the given zip code,
     * or null if no such place exists.
     */
     public static Place lookupZip(ArrayList<Place> places, String zip) {
	//compares each zipcode in the ArrayList to the given zipcode
	 for (int i=0; i<places.size(); i++) {
	    String placeZip = places.get(i).getZip(); //zipcode of ith element in places
	    if (placeZip.equals(zip)) {
		return places.get(i);
	    }
	}
	return null;
     }
    
}



  
