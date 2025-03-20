/* Name: Rebecca Lassman
 * File: LookupZip.java
 * Desc:
 *
 * This program contains static methods that read two zipcodes files and combine the data
 * from both to create an AVLTree of Places, LocatedPlaces, and PopulatedPlaces.
 * It also searches for a place in the AVLTree when given a zipcode.
 *
 */

import java.io.*;
import java.util.*;

public class LookupZip {
    //Indexes for data file lines
    public static final int ZIP = 0, TOWN = 1, STATE = 2, POP = 3, LAT = 5, LON = 6;

    /** Parses one line of input by creating a Place or
     * PopulatedPlace that denotes the information in the given line
     * @param lineNumber The line number of this line
     * @param line One line from the zipcodes file
     * @return A place that contains the given data
     */
    public static Place parseLine(int lineNumber, String line) {
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
	    Place place = new Place(zip, town, state);
	    return place;
	}
    }

    /** Finds a place with the same zipcode in the given AVLTree 
     *  and replaces it with a LocatedPlace or updated PopulatedPlace
     * @param places An AVLTree of places with latitude/longitude data missing
     * @param zip The zip code being searched for
     * @param lat The latitude that corresponds to the zipcode
     * @param lon The longitude that corresponds to the zipcode 
     */
    public static void combineData(AVLTree<Place> places, String zip, double lat, double lon) {
        Place temp = new Place(zip, "", ""); //used to search the tree
	Place found = places.search(temp); //place with missing lat/lon data
	Place replacement; //updated place
	if (found != null) {
	    if (found instanceof PopulatedPlace) {
		PopulatedPlace pFound = (PopulatedPlace) found; //used to get population from found
		replacement = new PopulatedPlace(zip, found.getTown(), found.getState(), lat, lon, pFound.getPopulation());
	    } else {
		replacement = new LocatedPlace(zip, found.getTown(), found.getState(), lat, lon);
	    }
	    places.updateKey(replacement);
       	}
    }

    /** Reads two zipcodes files, parsing every line, and combines the data from both files
     * @param file1Name The name of the zipcodes file containing zipcode, town, state, and population data
     * @param file2Name The name of the zipcodes file containing zipcode, latitude, and longitude data
     * @return An AVLTree of Places representing all the data in the files.
     */
    public static AVLTree<Place> readZipCodes(String file1Name, String file2Name) throws FileNotFoundException {
	Scanner input = new Scanner(new File(file1Name));
	int lineNum = 0; //current line being read from the file
	String line; //stores a line from the zipcode file
	AVLTree<Place> places = new AVLTree<Place>(); //stores all the data from the files

	//reads the first file and adds Places/PopulatedPlaces
	String firstLine = input.nextLine();
	while (input.hasNextLine()) {
	    line = input.nextLine();
	    places.insert(parseLine(lineNum, line));
	    lineNum++;
	}
	input.close();

	//reads the second file and adds availible latitude/longitude data
        input = new Scanner(new File(file2Name));
	firstLine = input.nextLine();
	while (input.hasNextLine()) {
	    line = input.nextLine();
	    //parses data from line
	    String[] tokens = line.split(",", 0); //stores tokens from given line
	    String zip = tokens[ZIP].replace("\"",""); //zip code from given line
	    String latString = tokens[LAT]; //latitude from given line
	    String lonString = tokens[LON]; //longitude from given line

	    //checks if latitude is missing from line
	    if (!latString.equals("")) {
		//stores latitude and longitude as doubles
		double lat = Double.parseDouble(latString);
		double lon = Double.parseDouble(lonString);
		combineData(places, zip, lat, lon);
	    }
	}
	input.close();

	return places;
    }

    /** Find a Place with a given zip code
     * @param places The AVLTree of places being searched
     * @param zip The zip code (as a String) to look up
     * @return A place that matches the given zip code,
     * or null if no such place exists.
     */
    public static Place lookupZip(AVLTree<Place> places, String zip) {
	Place temp = new Place(zip, "", ""); //used to search the tree
	Place found = places.search(temp);
        return found;
     } 
}  
